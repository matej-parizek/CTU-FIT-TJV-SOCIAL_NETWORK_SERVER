package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.regex.PatternSyntaxException;

@Entity
@Setter @Getter @NoArgsConstructor
public class Post implements DomainEntities<PostKey>{

//    @Id
//    private Long id;
//    private URI uri;
//    @ManyToOne(optional = false)
//    private User author;


    @EmbeddedId
    private PostKey key;
    private String text;
    @ManyToMany(mappedBy = "liked", fetch = FetchType.EAGER)
    private final Collection<User>likes = new HashSet<>();

    public Post(PostKey key) {
        this.key = key;
    }

    public Post( URI uri, User author) {
       this(new PostKey(author, uri));
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
}
