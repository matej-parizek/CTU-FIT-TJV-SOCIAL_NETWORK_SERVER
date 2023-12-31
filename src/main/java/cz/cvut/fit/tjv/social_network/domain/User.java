package cz.cvut.fit.tjv.social_network.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity(name = "User_account")
@Getter
@Setter
@NoArgsConstructor
public class User implements DomainEntities<String> {
    @Id
    private String username;
    private String realName;
    private String info;

    @JsonIgnoreProperties({"followed", "followers"})
    @ManyToMany(fetch = FetchType.EAGER)
    private final Collection<User> followed = new HashSet<>();

    @JsonIgnoreProperties({"followed", "followers"})
    @ManyToMany(mappedBy = "followed", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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
        return toStringWithDepth(0);
    }

    private String toStringWithDepth(int depth) {
        if (depth > 1) {
            return "Max depth reached";
        }

        StringBuilder result = new StringBuilder();
        result.append("User{username='").append(username).append('\'');
        result.append(", realName='").append(realName).append('\'');
        result.append(", info='").append(info).append('\'');

        result.append(", followed=[");
        boolean firstFollowed = true;
        for (User followedUser : followed) {
            if (!firstFollowed) {
                result.append(", ");
            }
            result.append(followedUser.toStringWithDepth(depth + 1));
            firstFollowed = false;
        }
        result.append("]");

        result.append(", followers=[");
        boolean firstFollowers = true;
        for (User follower : followers) {
            if (!firstFollowers) {
                result.append(", ");
            }
            result.append(follower.toStringWithDepth(depth + 1));
            firstFollowers = false;
        }
        result.append("]}");

        return result.toString();
    }
}
