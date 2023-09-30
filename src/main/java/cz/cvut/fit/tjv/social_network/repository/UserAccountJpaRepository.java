package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;
@EnableJpaRepositories
public interface UserAccountJpaRepository extends JpaRepository<UserAccount,String> {
    /**
     * Find all friends of user. Friends are users, which follows each other.
     *
     * @param username is ID of user, whom looking for friends. Allowed object {@link String}
     * @return {@link Collection}
     */
    Collection<UserAccount> findFriends(String username);
}
