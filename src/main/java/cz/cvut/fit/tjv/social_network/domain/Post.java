package cz.cvut.fit.tjv.social_network.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.PatternSyntaxException;

@Entity
@Setter @Getter @NoArgsConstructor
public class Post implements DomainEntities<PostKey>{

    @EmbeddedId
    private PostKey key;
    @Column(nullable = false)
    private LocalDateTime added;
    private String text;
    @Column(nullable = false)
    @Lob
    private byte[] image;
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"followed","followers","info"})
    private final Collection<User>likes = new HashSet<>();

    public Post(PostKey key) {
        this.key = key;
    }

    public Post( Long id, User author) {
       this(new PostKey(author, id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return key.equals(post.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public PostKey getId() {
        return key;
    }

    @Override
    public String toString() {
        return "Post{" +
                "key=" + key +
                ", added=" + added +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                '}';
    }
}
