package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter @Getter @NoArgsConstructor
public class CommentsKey implements Serializable {
    @ManyToOne(optional = false)
    private Post forPost;
    @ManyToOne(optional = false)
    private UserAccount authorCom;
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public CommentsKey(Long id, Post forPost, UserAccount authorCom) {
        this.id = Objects.requireNonNull(id);
        this.forPost = Objects.requireNonNull(forPost);
        this.authorCom = Objects.requireNonNull(authorCom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentsKey that = (CommentsKey) o;

        if (!id.equals(that.id)) return false;
        if (!forPost.equals(that.forPost)) return false;
        return authorCom.equals(that.authorCom);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + forPost.hashCode();
        result = 31 * result + authorCom.hashCode();
        return result;
    }
}
