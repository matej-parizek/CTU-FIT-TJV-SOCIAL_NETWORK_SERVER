package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,String> {
    /**
     * Find all friends of user. Friends are users, which follows each other.
     *
     * @param username is ID of user, whom looking for friends. Allowed object {@link String}
     * @return {@link Collection}
     */
    @Query("select followed.username from user_account.followed followed where username(followed.username)" +
            "intersect select followers.username from user_account followers where username(followers.username)")
    Collection<UserAccount> findFriends(String username);
}
