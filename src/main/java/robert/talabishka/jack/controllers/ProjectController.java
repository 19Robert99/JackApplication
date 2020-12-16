package robert.talabishka.jack.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robert.talabishka.jack.model.Project;
import robert.talabishka.jack.services.ProjectService;

import java.util.List;

@RestController
@RequestMapping(value = "/projectManagement/")
public class ProjectController extends AbstractController{

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/projects/user/{id}", method = RequestMethod.GET)
    public List<Project> getProjectsByUserId(@PathVariable Long id){
        List<Project> list = projectService.getProjectsByUserId(id);
        list.sort((d1, d2) -> (int) (d1.getId() - d2.getId()));
        return list;
    }
}
