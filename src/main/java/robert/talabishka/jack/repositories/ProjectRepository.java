package robert.talabishka.jack.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import robert.talabishka.jack.model.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends AbstractRepository<Project> {

    //    @Query("select proj, au.role.name as currentUserProjectRole from Project proj join AssignedUser au on proj.id = au.project.id where au.user.id = :id")
//    @Query(nativeQuery = true, value = "select proj.*, pr.name as \"currentUserProjectRole\" from public.projects proj \n" +
//            " join public.project_users au on proj.id = au.project_id\n" +
//            " join public.project_roles pr on pr.id=au.project_id\n" +
//            " where au.user_id = 2;\n" +
//            "\n")
    @Query("select proj from Project proj where proj.id in (select au.project from AssignedUser au where au.user.id = :id)")
    List<Project> getProjectsByUserId(Long id);
}
