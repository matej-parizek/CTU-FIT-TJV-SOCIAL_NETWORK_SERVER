package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.net.URI;

@Repository
public interface PostJpaRepository extends JpaRepository<Post, PostKey> {
}
