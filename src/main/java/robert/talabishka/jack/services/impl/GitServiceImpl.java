package robert.talabishka.jack.services.impl;


import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitStatus;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Component;
import robert.talabishka.jack.model.*;
import robert.talabishka.jack.services.GitService;
import robert.talabishka.jack.services.TicketService;
import robert.talabishka.jack.services.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GitServiceImpl implements GitService {

    private final TicketService ticketService;
    private final UserService userService;
    private ConcurrentHashMap<String, GHRepository> projectToRepository = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, List<Build>> keyToBuilds = new ConcurrentHashMap<>();

    public GitServiceImpl(TicketService ticketService, UserService userService) {
        this.ticketService = ticketService;
        this.userService = userService;
    }

    private GHRepository initRepository(GHRequest request) {
        try {
            GHRepository ghRepository = projectToRepository.get(request.getRepositoryName());
            if (ghRepository != null) {
                return ghRepository;
            } else {
                GHRepository repository = GitHub.connectUsingOAuth(request.getToken()).getRepository("19Robert99/".concat(request.getRepositoryName()));
                projectToRepository.put(request.getRepositoryName(), repository);
                return repository;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Commit> getAllCommits(GHRequest request) {
        if (StringUtils.isBlank(request.getRepositoryName()) && StringUtils.isBlank(request.getToken())) {
            return Collections.singletonList(new Commit());
        }
        GHRepository repository = initRepository(request);

        try {
            return mapCommits(repository.listCommits().toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.singletonList(new Commit());
    }

    private List<Commit> mapCommits(List<GHCommit> ghCommits) {
        return ghCommits.stream().map(this::buildCommit).filter(commit -> commit.getTicket() != null).collect(Collectors.toList());
    }

    private Commit buildCommit(GHCommit ghCommit) {
        try {
            String ticketKey = getTicketKey(ghCommit.getCommitShortInfo().getMessage());
            addBuildInfo(ghCommit.getLastStatus(), ticketKey);
            return Commit.builder()
                    .user(getUserByEmail(ghCommit.getAuthor().getEmail()))
                    .ticket(getTicketByKey(ticketKey))
                    .comment(ghCommit.getCommitShortInfo().getMessage())
                    .date(ghCommit.getCommitShortInfo().getCommitDate())
                    .url(ghCommit.getHtmlUrl().toURI().toString())
                    .build();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new Commit();
    }

    @Override
    public List<Build> getBuildsByTicketKey(String ticketKey){
        List<Build> builds = keyToBuilds.get(ticketKey);
        if (builds != null && !builds.isEmpty()){
            return builds;
        } else {
            return new ArrayList<>();
        }
    }

    private void addBuildInfo(GHCommitStatus status, String ticketKey) {
        if (status != null) {
            List<Build> builds = keyToBuilds.get(ticketKey);

            Build build = Build.builder()
                    .status(status.getState().name())
                    .url(status.getTargetUrl())
                    .build();

            if (builds != null && !builds.isEmpty()) {
                builds.add(build);
            } else {
                keyToBuilds.put(ticketKey, Collections.singletonList(build));
            }
        }
    }

    private String getTicketKey(String commitComment) {
        Pattern pattern = Pattern.compile("\\[([^\\]]+)");
        Matcher matcher = pattern.matcher(commitComment);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Ticket getTicketByKey(String key) {
        Ticket ticket = new Ticket();
        if (StringUtils.isNoneBlank(key)) {
            ticket.setId(ticketService.getIdByKey(key));
            return ticket;
        }
        return null;
    }

    private User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

}
