package robert.talabishka.jack.controllers;

import org.springframework.web.bind.annotation.*;
import robert.talabishka.jack.model.Build;
import robert.talabishka.jack.model.Commit;
import robert.talabishka.jack.model.GHRequest;
import robert.talabishka.jack.services.GitService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(value = "/commitManagement/")
public class CommitController {

    private final GitService gitService;

    public CommitController(GitService gitService) {
        this.gitService = gitService;
    }

    @RequestMapping(value = "commits/all", method = RequestMethod.GET)
    public List<Commit> getCommits(@RequestBody GHRequest request) {
        return (List<Commit>) gitService.getAllCommits(request);
    }

    @RequestMapping(value = "builds/all/{key}", method = RequestMethod.GET)
    public List<Build> getBuilds(@PathVariable String key) {
        return gitService.getBuildsByTicketKey(key);
    }

}
