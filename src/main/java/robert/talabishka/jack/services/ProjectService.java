package robert.talabishka.jack.services;

import robert.talabishka.jack.model.Project;

import java.util.List;

public interface ProjectService extends AbstractService<Project> {
    List<Project> getProjectsByUserId(Long id);

    List<Project> addCurrentProjectRole(Long id, List<Project> projects);
}
