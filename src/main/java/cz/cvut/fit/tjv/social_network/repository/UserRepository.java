package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("SELECT sum(size(p.likes) ) FROM Post p where :username=p.key.author.username")
    long sumAllPostLikes(@Param("username") String username);
    @Query("select sum(size(p.likes) ) from Post p where p.text= ('%Author: ' + p.key.author.username + CHAR(10) +'Co-Author: '+ :username) ")
    long sumAllLikesLikeCoCreator(@Param("username") String username);
    @Query("select u from User_account u where :username=u.username and u.followed in " +
            "(select us.followers from User_account us where us.username=:username)")
    Collection<User> findFriends(@Param("username") String username);
}
