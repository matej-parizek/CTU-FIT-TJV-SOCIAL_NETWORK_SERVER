package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;

import java.util.Collection;
import java.util.Optional;

public interface PostService extends CrudService<Post, PostKey>{
    void coCreator(String author, long uri, String coAuthor);
    public void like(String who, long uri, String author);
    Collection<Post> readAllPostByAuthor(String author);
    Optional<Post> readById(String username, long id);
}
