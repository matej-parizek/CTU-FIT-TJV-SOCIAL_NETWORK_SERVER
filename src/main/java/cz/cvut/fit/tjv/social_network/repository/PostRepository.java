package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, PostKey> {
    List<Post> findAllByKeyAuthor(User key_author);
    @Query("SELECT p from Post p where p.key.author in " +
            "(select u.followed from User_account u where u.username=:username)")
    List<Post> findAllFollowedPosts(@Param("username")String username);

    @Query("select p from Post p where :username in (select l.username from p.likes l)")
    Collection<Post> findAllPostLikedByUsername(@Param("username")String username);

}
