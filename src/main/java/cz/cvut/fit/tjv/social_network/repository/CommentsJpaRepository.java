package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Comments;
import cz.cvut.fit.tjv.social_network.domain.CommentsKey;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface CommentsJpaRepository extends JpaRepository<Comments, CommentsKey> {

    //@EntityGraph ????
}
