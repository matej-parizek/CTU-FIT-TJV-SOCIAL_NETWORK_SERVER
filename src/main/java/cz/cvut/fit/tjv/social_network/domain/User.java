package cz.cvut.fit.tjv.social_network.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.Mapping;

import java.util.Collection;
import java.util.HashSet;

@Entity(name = "User_account")
@Getter @Setter @NoArgsConstructor
public class User implements DomainEntities<String>{
    @Id
    private String username;
    private String realName;
    private String info;

    @JsonIgnoreProperties({"followed", "followers"})
    @ManyToMany(fetch = FetchType.LAZY)
    private final Collection<User> followed = new HashSet<>();

    @JsonIgnoreProperties({"followed", "followers"})
    @ManyToMany(fetch = FetchType.LAZY)
    private final Collection<User> followers = new HashSet<>();

        public User(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", realName='" + realName + '\'' +
                ", info='" + info + '\'' +
                ", followed=" + followed +
                '}';
    }
}
