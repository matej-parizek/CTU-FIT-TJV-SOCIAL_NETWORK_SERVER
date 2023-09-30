package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.net.URI;

@EnableJpaRepositories
public interface PostJpaRepository extends JpaRepository<Post, URI> {
}
