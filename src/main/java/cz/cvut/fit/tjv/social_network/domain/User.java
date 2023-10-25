package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity(name = "User_account")
@Getter @Setter @NoArgsConstructor
public class User implements DomainEntities<String>{
    @Id
    private String username;
    @Column(nullable = false)
    private String password;
    private String realName;
    private String info;

    @ManyToMany(fetch = FetchType.EAGER)
    private final Collection<User> followed = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private final Collection<Post> liked=new HashSet<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
}
