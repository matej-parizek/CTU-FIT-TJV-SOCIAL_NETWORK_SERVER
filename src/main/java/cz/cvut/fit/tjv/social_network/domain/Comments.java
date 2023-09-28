package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//@Entity
@Setter @Getter
public class Comments implements DomainEntity<CommentsKey> {
    @EmbeddedId
    private CommentsKey key;
    @Column(nullable = false)
    private LocalDateTime added;
    @Column(nullable = false)
    private String text;
    @Override
    public CommentsKey getID() {
        return null;
    }
}
