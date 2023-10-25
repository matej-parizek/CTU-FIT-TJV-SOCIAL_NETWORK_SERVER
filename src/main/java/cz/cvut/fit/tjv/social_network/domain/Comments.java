package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter @Getter @NoArgsConstructor
public class Comments implements DomainEntities<CommentsKey>{
    @EmbeddedId
    private CommentsKey key;
    @Column(nullable = false)
    private String text;

    @Override
    public CommentsKey getId() {
        return null;
    }
}
