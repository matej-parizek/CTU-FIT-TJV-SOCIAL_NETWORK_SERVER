package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
@Repository
public interface UserRepository extends CrudRepository<UserAccount,String> {
    @Query("select followed.username from user_account.followed followed where username(followed.username)" +
            "union select followers.username from user_account followers where username(followers.username)")
    Collection<UserAccount> findFriends(String username);
}
