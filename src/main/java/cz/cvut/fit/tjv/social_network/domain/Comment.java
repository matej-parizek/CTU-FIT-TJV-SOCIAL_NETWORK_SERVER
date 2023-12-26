package cz.cvut.fit.tjv.social_network.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @NoArgsConstructor
public class Comment implements DomainEntities<Long>{
    @Id
    private long idComment;
    @JsonIgnoreProperties({"followed","followers","info"})
    @ManyToOne(optional = false)
    private User author;
    @JsonIgnoreProperties({"image","added","text"})
    @ManyToOne(optional = false)
    private Post toPost;
    @Column(nullable = false)
    private String text;

    public Comment(User author, Post toPost) {
        this.author = author;
        this.toPost = toPost;
    }

    public Comment(Long id, User author, Post toPost) {
        this.idComment = id;
        this.author = author;
        this.toPost = toPost;
    }

    public Comment(Long id, User author, Post toPost, String text) {
        this.idComment = id;
        this.author = author;
        this.toPost = toPost;
        this.text = text;
    }

    @Override
    public Long getId() {
        return idComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        return comment.idComment == this.idComment;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(idComment).hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "idComment=" + idComment +
                ", author=" + author +
                ", toPost=" + toPost +
                ", text='" + text + '\'' +
                '}';
    }
}
