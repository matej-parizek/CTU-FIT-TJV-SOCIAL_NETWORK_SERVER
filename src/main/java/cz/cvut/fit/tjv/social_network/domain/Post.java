package cz.cvut.fit.tjv.social_network.domain;

import ch.qos.logback.core.util.LocationUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.parsing.Location;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter @Setter
public class Post implements DomainEntity<URI>{
    @Id
    private URI id;
    @ManyToOne  // TODO: 28.09.2023 Zkontrolovat zda je to OK
    @JoinColumn(nullable = false)
    private UserAccount author;
    @Column(nullable = false)
    private LocalDateTime added;
    @ManyToMany // TODO: 28.09.2023 Zkontrolovat zda je to OK
    private final Set<UserAccount> likes = new HashSet<>();
    private String text;

    public Post() {
    }

    public Post(URI id, UserAccount author) {
        this.id = Objects.requireNonNull(id);
        this.author = Objects.requireNonNull(author);
    }

    @Override
    public URI getKEY() {
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

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", author=" + author +
                ", added=" + added +
                ", likes=" + likes +
                ", text='" + text + '\'' +
                '}';
    }
}
