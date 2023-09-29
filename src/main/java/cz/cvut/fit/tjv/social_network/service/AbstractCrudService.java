package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.DomainEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCrudService <E extends DomainEntity<K>,K>{
    public final CrudRepository<E,K> repository;

    protected AbstractCrudService(CrudRepository<E, K> repository) {
        this.repository = repository;
    }

    /**
     * Create new entity in repository
     *
     * @param entity Allowed object is {@link E}
     * @throws NullPointerException if id entity is {@literal null}
     */
    public void create(E entity){
        K id = Objects.requireNonNull(entity.getKEY(), "Id (Key) for create entity cannot be null");
        if(repository.existsById(id))
            throw new RuntimeException();
        repository.save(entity);
    }

    /**
     * Delete entity by ID
     *
     * @param entity Allowed object is {@link E}
     */
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
    public Optional<E> readById(K id){
        return repository.findById(id);
    }
    public abstract void update(E entity);
}
