package robert.talabishka.jack.services;

import robert.talabishka.jack.model.BaseEntity;

import java.util.List;

public interface AbstractService<S extends BaseEntity> {
    S save(S s);

    S findById(Long aLong);

    boolean existsById(Long aLong);

    List<S> findAll();

    void deleteById(Long aLong);

    void delete(S entity);

    void deleteAll();
}
