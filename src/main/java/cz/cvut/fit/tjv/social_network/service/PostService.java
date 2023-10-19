package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.UserAccount;

import java.net.URI;

public interface PostService extends CrudService<Post, PostKey> {
    public void likePost(PostKey key, String username);
    void addCoCreator(PostKey key, String username);

}
