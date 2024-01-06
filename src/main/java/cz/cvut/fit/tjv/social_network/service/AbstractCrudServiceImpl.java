package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.DomainEntities;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

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
            throw new EntityAlreadyExistException();
        return getRepository().save(entity);
    }

    /**
     * Delete entity by ID
     *
     * @param id Allowed object is {@link K}
     */
    @Override
    @Transactional
    public void deleteById( K id){
        if(!getRepository().existsById(id))
            throw new EntityDoesNotExistException();
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
    public abstract E update(E entity);

    protected abstract JpaRepository<E, K> getRepository();
}
