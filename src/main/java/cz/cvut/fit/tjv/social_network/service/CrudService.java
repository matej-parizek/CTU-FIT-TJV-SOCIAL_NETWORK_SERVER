package cz.cvut.fit.tjv.social_network.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudService<E, K> {
    E create(E entity);
    void deleteById(K entity);
    Optional<E> readById(K id);
    E update(E entity);

}
