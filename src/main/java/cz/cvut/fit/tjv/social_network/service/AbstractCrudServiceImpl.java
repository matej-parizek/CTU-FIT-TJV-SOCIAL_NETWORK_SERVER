package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.DomainEntities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractCrudServiceImpl<E extends DomainEntities<K>,K> implements CrudService< E, K>{


    /**
     * Create new entity in repository
     *
     * @param entity Allowed object is {@link E}
     * @throws NullPointerException if id entity is {@literal null}
     */
    @Override
    public E create(E entity){
        K id = Objects.requireNonNull(entity.getId(), "Id (Key) for create entity cannot be null");
        if(getRepository().existsById(id))
            throw new RuntimeException();
        return getRepository().save(entity);
    }

    /**
     * Delete entity by ID
     *
     * @param entity Allowed object is {@link E}
     */
    @Override
    public void deleteById(E entity){
        K id = Objects.requireNonNull(entity.getId(), "Id (Key) for delete entity cannot be null");
        if(!getRepository().existsById(id))
            throw new RuntimeException();
        getRepository().deleteById(id);
    }

    /**
     * Find Entity {@link E}, by id (key) {@link K}
     * @param id object of {@link K}
     * @return {@link Optional}
     */
    @Override
    public Optional<E> readById(K id){
        return getRepository().findById(id);
    }
    @Override
    public abstract void update(E entity);

    protected abstract JpaRepository<E, K> getRepository();
}
