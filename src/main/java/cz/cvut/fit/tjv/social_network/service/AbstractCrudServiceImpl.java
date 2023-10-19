package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCrudServiceImpl<E extends DomainEntity<K>,K> implements CrudService< E, K>{
    public final JpaRepository<E,K> repository;

    protected AbstractCrudServiceImpl(JpaRepository<E, K> repository) {
        this.repository = repository;
    }

    /**
     * Create new entity in repository
     *
     * @param entity Allowed object is {@link E}
     * @throws NullPointerException if id entity is {@literal null}
     */
    @Override
    public E create(E entity){
        K id = Objects.requireNonNull(entity.getKEY(), "Id (Key) for create entity cannot be null");
        if(repository.existsById(id))
            throw new RuntimeException();
        return repository.save(entity);
    }

    /**
     * Delete entity by ID
     *
     * @param entity Allowed object is {@link E}
     */
    @Override
    public void deleteById(E entity){
        K id = Objects.requireNonNull(entity.getKEY(), "Id (Key) for delete entity cannot be null");
        if(!repository.existsById(id))
            throw new RuntimeException();
        repository.deleteById(id);
    }

    /**
     * Find Entity {@link E}, by id (key) {@link K}
     * @param id object of {@link K}
     * @return {@link Optional}
     */
    @Override
    public Optional<E> readById(K id){
        return repository.findById(id);
    }
    @Override
    public abstract void update(E entity);

    @Override
    public JpaRepository<E, K> getRepository() {
        return repository;
    }
}
