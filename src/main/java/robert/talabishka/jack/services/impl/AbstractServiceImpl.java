package robert.talabishka.jack.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.talabishka.jack.model.BaseEntity;
import robert.talabishka.jack.repositories.AbstractRepository;
import robert.talabishka.jack.services.AbstractService;

import java.util.List;

@Service
@Transactional
public abstract class AbstractServiceImpl<S extends BaseEntity> implements AbstractService<S> {

    private final AbstractRepository<S> repository;

    @Autowired
    public AbstractServiceImpl(AbstractRepository<S> repository) {
        this.repository = repository;
    }

    @Override
    public S save(S o) {
        return repository.save(o);
    }

    @Override
    public S findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public List<S> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(S entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
