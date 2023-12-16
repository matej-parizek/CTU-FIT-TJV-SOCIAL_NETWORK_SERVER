package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("SELECT sum(size(p.likes) ) FROM Post p where :username=p.key.author.username")
    long sumAllLikes(@Param("username") String username);

    @Query("select author.followed from User_account author where :username=author.username" +
        " intersect " +
        "select u from User_account u where :username in (select f.username from u.followed f)")
    Collection<User> findFriends(@Param("username") String username);

}
