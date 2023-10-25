package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.net.URI;

@Embeddable
@NoArgsConstructor @Getter
public class PostKey implements Serializable {
    @ManyToOne(targetEntity = User.class, optional = false)
    private User author;
    @Column(nullable = false)
    private URI uri;

    public PostKey(User author, URI uri) {
        this.author = author;
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostKey postKey = (PostKey) o;

        if (!author.equals(postKey.author)) return false;
        return uri.equals(postKey.uri);
    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + uri.hashCode();
        return result;
    }
}
