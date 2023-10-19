package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Setter @Getter @NoArgsConstructor
/*
  @Table(uniqueConstraints = @UniqueConstraint(name = "comments_key", columnNames = {""}))
 */
public class Comments implements DomainEntity<CommentsKey> {
    @EmbeddedId
    private CommentsKey id;

    // Another opinion for id
    /**
     @Id
     private Long key;
     @ManyToOne(optional = false)
     private UserAccount author;
     @ManyToOne(optional = false)
     private Post toPost;
     */
    @Column(nullable = false)
    private LocalDateTime added;
    @Column(nullable = false)
    private String text;

    public Comments(CommentsKey key) {
        this.id = Objects.requireNonNull(key);
    }
    public Comments(Long id, Post forPost, UserAccount author){
        this(new CommentsKey(id,forPost,author));
    }


    @Override
    public CommentsKey getKEY() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comments comments = (Comments) o;

        return id.equals(comments.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Comments{" +
                "key=" + id+
                ", added=" + added +
                ", text='" + text + '\'' +
                '}';
    }
}
