package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("SELECT sum(size(p.likes) ) FROM Post p where :username=p.key.author.username")
    Long sumAllPostLikes(@Param("username") String username);
    @Query("SELECT SUM(size(p.likes))FROM Post p where p.text like concat('%Author: ',p.key.author.username,CHAR(10)" +
            ",'Co-Author: ', :username ,'%') ")
    Long sumAllLikesLikeCoCreator(@Param("username") String username);
}
