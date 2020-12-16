package robert.talabishka.jack.services.impl;


import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GitServiceImpl {

    private String repositoryName;
    private String token;

    private Map<String, BigInteger> map;

    private Collection<GHCommit> ghCommits;

    private Map<String, List<GHCommit.File>> fileMap = new HashMap<>();


    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @PostConstruct
    public GHRepository getGHRepository() throws IOException {
        if (repositoryName != null && token != null) {
            return GitHub.connectUsingOAuth(token).getRepository(repositoryName);
        }
        return null;
    }


    public Collection<GHCommit> getAllGHCommits() throws IOException {

        if (repositoryName != null && token != null) {
            return GitHub.connectUsingOAuth(token).getRepository(repositoryName).listCommits().asList();
        }
        return null;
    }

}
