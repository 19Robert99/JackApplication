package robert.talabishka.jack.services.impl;


import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Component;
import robert.talabishka.jack.model.Commit;
import robert.talabishka.jack.model.GHRequest;
import robert.talabishka.jack.model.Ticket;
import robert.talabishka.jack.model.User;
import robert.talabishka.jack.services.GitService;
import robert.talabishka.jack.services.TicketService;
import robert.talabishka.jack.services.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
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
        return ghCommits.stream().map(this::buildCommit).collect(Collectors.toList());
    }

    private Commit buildCommit(GHCommit ghCommit) {
        try {
            return Commit.builder()
                    .user(getUserByEmail(ghCommit.getAuthor().getEmail()))
                    .ticket(getTicketByKey(ghCommit.getCommitShortInfo().getMessage()))
                    .comment(ghCommit.getCommitShortInfo().getMessage())
                    .date(ghCommit.getCommitShortInfo().getCommitDate())
                    .url(ghCommit.getHtmlUrl().toURI().toString())
                    .build();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new Commit();
    }

    private String getTicketKey(String commitComment) {
        Pattern pattern = Pattern.compile("\\[([^\\]]+)");
        Matcher matcher = pattern.matcher(commitComment);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Ticket getTicketByKey(String commitMessage) {
        Ticket ticket = new Ticket();
        if (StringUtils.isNoneBlank(getTicketKey(commitMessage))) {
            ticket.setId(ticketService.getIdByKey(getTicketKey(commitMessage)));
            return ticket;
        }
        return null;
    }

    private User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

}
