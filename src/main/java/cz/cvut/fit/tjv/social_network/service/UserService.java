package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

public interface UserService extends CrudService<User,String> {
   long sumAllLikes(String username);

   void follow(String follower, String followed);
   Collection<User> findFriends(String username);

}
