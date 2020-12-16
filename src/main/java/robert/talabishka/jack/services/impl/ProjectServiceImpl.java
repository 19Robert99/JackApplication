package robert.talabishka.jack.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.talabishka.jack.model.AssignedUser;
import robert.talabishka.jack.model.Project;
import robert.talabishka.jack.repositories.ProjectRepository;
import robert.talabishka.jack.services.ProjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectServiceImpl extends AbstractServiceImpl<Project> implements ProjectService {
    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<Project> getProjectsByUserId(Long id) {
        return addCurrentProjectRole(id, repository.getProjectsByUserId(id));
    }

    @Override
    public List<Project> addCurrentProjectRole(Long id, List<Project> projects) {
        for (Project project : projects) {
            for (AssignedUser assignedUser : project.getAssignedUsers()) {
                if (assignedUser.getUser().getId().equals(id)) {
                    project.setCurrentUserProjectRole(assignedUser.getRole().getName());
                }
            }
        }
        return projects;
    }
}
