package robert.talabishka.jack.services;

import robert.talabishka.jack.model.Build;
import robert.talabishka.jack.model.Commit;
import robert.talabishka.jack.model.GHRequest;

import java.util.Collection;
import java.util.List;

public interface GitService {
    Collection<Commit> getAllCommits(GHRequest request);
    List<Build> getBuildsByTicketKey(String ticketKey);
}
