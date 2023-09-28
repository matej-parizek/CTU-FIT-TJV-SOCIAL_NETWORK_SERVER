package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter @Getter
public class CommentsKey implements Serializable {
    private Long id;
    private Post forPost;
    private UserAccount authorCom;

    public CommentsKey(Long id, Post forPost, UserAccount authorCom) {
        this.id = id;
        this.forPost = forPost;
        this.authorCom = authorCom;
    }

    public CommentsKey() {

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
