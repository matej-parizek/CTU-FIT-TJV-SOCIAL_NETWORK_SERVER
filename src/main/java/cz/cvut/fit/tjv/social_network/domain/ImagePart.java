package cz.cvut.fit.tjv.social_network.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
//@Entity(name = "image")
@Getter @Setter
public class ImagePart extends Post{
    @Column(nullable = false)
    private int height;
    @Column(nullable = false)
    private int width;

    public ImagePart(URI id, UserAccount author) {
        super(id, author);
    }

    public ImagePart() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
