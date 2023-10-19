package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.net.URI;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter @Setter @NoArgsConstructor
// Another opinion key
/*
 * @Table(uniqueConstraints = @UniqueConstraint(name = "post_key",columnNames = {"author", "uri"}))
 */
public class Post implements DomainEntity<PostKey>{

    @EmbeddedId
    private PostKey key;

    //Another opinion key;
    /**
    @Id
    private Long id;
    @Column(nullable = false)
    private URI uri;
    @ManyToOne(optional = false)
    private UserAccount author;
     */

    @Column(nullable = false)
    private LocalDateTime added;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "username")
    private final Collection<UserAccount> likes = new HashSet<>();
    private String text;

    public Post(PostKey key){
        this.key=key;
    }
    public Post(URI id, UserAccount author) {
        this(new PostKey(id,author));
    }

    public UserAccount getAuthor(){
        return key.getAuthor();
    }
    public URI getUri(){
        return key.getUri();
    }

    @Override
    public PostKey getKEY() {
        return key;
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


}
