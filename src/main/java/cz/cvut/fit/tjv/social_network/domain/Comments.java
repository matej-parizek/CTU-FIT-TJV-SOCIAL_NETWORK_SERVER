package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class Comments implements DomainEntity<CommentsKey> {
    @EmbeddedId
    private CommentsKey key;
    @Column(nullable = false)
    private LocalDateTime added;
    @Column(nullable = false)
    private String text;
    @Override
    public CommentsKey getKEY() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        return key.equals(comments.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "Comments{" +
                "key=" + key +
                ", added=" + added +
                ", text='" + text + '\'' +
                '}';
    }
}
