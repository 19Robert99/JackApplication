package robert.talabishka.jack.services;

import robert.talabishka.jack.model.Commit;
import robert.talabishka.jack.model.GHRequest;

import java.util.Collection;

public interface GitService {
    Collection<Commit> getAllCommits(GHRequest request);
}
