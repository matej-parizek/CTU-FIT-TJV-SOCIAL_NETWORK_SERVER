package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.net.URI;
@Embeddable
@Getter @Setter @NoArgsConstructor
public class PostKey implements Serializable {
    @Column(nullable = false)
    private URI uri;
    @ManyToOne(optional = false)
    private UserAccount author;
    public PostKey(URI uri, UserAccount author){
        this.uri =uri;
        this.author=author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostKey postKey = (PostKey) o;

        if (!uri.equals(postKey.uri)) return false;
        return author.equals(postKey.author);
    }

    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + author.hashCode();
        return result;
    }
}
