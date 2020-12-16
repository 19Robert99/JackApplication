package robert.talabishka.jack.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import robert.talabishka.jack.model.BaseEntity;

@NoRepositoryBean
public interface AbstractRepository<S extends BaseEntity> extends JpaRepository<S, Long> {
}
