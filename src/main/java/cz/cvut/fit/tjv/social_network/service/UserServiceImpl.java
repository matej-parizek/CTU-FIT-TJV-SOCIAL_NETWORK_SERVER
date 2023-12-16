package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class UserServiceImpl extends AbstractCrudServiceImpl<User,String>implements UserService{
    UserRepository userRepository;
   PostRepository postRepository;

//    protected UserServiceImpl(UserRepository repository) {
//        this.repository=repository;
//    }

    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public User update(User entity) {
        // TODO: 20.10.2023
        throw new RuntimeException();
    }

    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    @Override
    public long sumAllLikes(String username) {
        // TODO: 16.12.2023
        return 0L;
    }

    @Override
    public Collection<User> findFriends(String username) {
        return userRepository.findFriends(username);
    }

    @Override
    public void follow(String follower, String followed) {
        var followerOpt = userRepository.findById(follower);
        var followedOpt = userRepository.findById(followed);
        if(followedOpt.isEmpty() || followerOpt.isEmpty())
            throw new UserDoestExistException();
        var followerUser = followerOpt.get();
        var followedUser = followedOpt.get();
        if(followedUser.equals(followerUser))
            throw new UsersAreSameException();
        followerUser.getFollowed().add(followedUser);
        followedUser.getFollowers().add(followerUser);
        userRepository.saveAll(Arrays.asList(followedUser,followerUser));
    }

    @Override
    public Collection<User> getFollowers(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        return userOpt.get().getFollowers();
    }
    public Collection<User> getFollowed(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        return userOpt.get().getFollowed();
    }

}
