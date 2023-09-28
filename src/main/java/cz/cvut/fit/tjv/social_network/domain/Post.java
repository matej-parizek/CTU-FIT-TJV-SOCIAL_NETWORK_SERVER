package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "post")
@Getter @Setter
public class Post implements DomainEntity<URI>{
    @Id
    private URI id;
    @OneToOne  // TODO: 28.09.2023 Zkontrolovat zda je to OK
    @JoinColumn(nullable = false)
    private UserAccount author;
    @ManyToMany // TODO: 28.09.2023 Zkontrolovat zda je to OK
    private final Set<UserAccount> likes = new HashSet<>();

    public Post() {
    }

    public Post(URI id, UserAccount author) {
        this.id = id;
        this.author = author;
    }

    @Override
    public URI getID() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
