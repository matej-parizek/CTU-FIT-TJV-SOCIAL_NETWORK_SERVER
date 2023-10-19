package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface UserAccountJpaRepository extends JpaRepository<UserAccount,String> {
    @Query("SELECT COUNT(l) FROM Post.likes l WHERE l.username= :username")
    int countAllLikes(String username);

    @Query("SELECT followed FROM User_account.followed followed WHERE :username = followed.username " +
            "intersect " +
            "SELECT user.followed FROM User_account user WHERE user.username = :username")
    Collection<UserAccount>findFriends(String username);
}
