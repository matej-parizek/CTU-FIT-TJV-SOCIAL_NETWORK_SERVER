package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.User;

import java.util.Collection;

public interface UserService extends CrudService<User,String> {

   long sumAllPostLikes(String username);
   long sumLikesLikeCoWorker(String username);
   void follow(String follower, String followed);
   void unfollow(String follower, String follow);
   Collection<User> findFriends(String username);
   Collection<User> getFollowers(String username);
   Collection<User> getFollowed(String username);
   Collection<User> getAll();
}
