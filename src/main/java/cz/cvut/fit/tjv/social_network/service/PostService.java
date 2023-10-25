package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;

import java.net.URI;
import java.util.Collection;

public interface PostService extends CrudService<Post, PostKey>{
    void coCreator(String author, URI uri, String coAuthor);
    public void wasLiked(String who,URI uri, String author);
}
