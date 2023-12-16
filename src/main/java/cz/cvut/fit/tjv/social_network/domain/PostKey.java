package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor @Getter
public class PostKey implements Serializable {
    @ManyToOne(targetEntity = User.class, optional = false)
    private User author;
    @Column(nullable = false)
    private Long id;

    public PostKey(User author, Long id) {
        this.author = author;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostKey postKey = (PostKey) o;

        if (!author.equals(postKey.author)) return false;
        return id.equals(postKey.id);
    }

    @Override
    public int hashCode() {
        int result = author.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PostKey{" +
                "author=" + author +
                ", uri=" + id +
                '}';
    }
}
