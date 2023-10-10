package cz.cvut.fit.tjv.social_network.repository;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Collection;
@EnableJpaRepositories
public interface UserAccountJpaRepository extends JpaRepository<UserAccount,String> {
    @Query("SELECT COUNT(l) FROM Post.likes l WHERE l.username= :username")
    int countAllLikes(String username);
}
