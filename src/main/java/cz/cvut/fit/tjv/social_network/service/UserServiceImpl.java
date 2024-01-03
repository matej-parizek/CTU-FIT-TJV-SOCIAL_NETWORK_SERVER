package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoesntFollowException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.stream.Collectors;

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
        var userOpt = readById(entity.getId());
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        var user = userOpt.get();
        user.setInfo(entity.getInfo());
        user.setRealName(entity.getRealName());
        return userRepository.save(user);
    }
    @Override
    protected JpaRepository<User, String> getRepository() {
        return userRepository;
    }
    @Override
    public Collection<User> findFriends(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        var user = userOpt.get();
        return user.getFollowed().stream().distinct().filter(user.getFollowers()::contains).collect(Collectors.toSet());
    }
    @Override
    public void follow(String follower, String followed) {
        var followerOpt = userRepository.findById(follower);
        var followedOpt = userRepository.findById(followed);
        if(followedOpt.isEmpty() || followerOpt.isEmpty())
            throw new UserDoestExistException();
        var followerUser = followerOpt.get();
        var followedUser = followedOpt.get();

        if(followedUser.getFollowers().contains(followerUser) || followerUser.getFollowed().contains(followedUser)){
            userRepository.save(followedUser);
            userRepository.save(followerUser);
            return;
        }

        if(followedUser.equals(followerUser))
            throw new UsersAreSameException();
        followerUser.getFollowed().add(followedUser);
        followedUser.getFollowers().add(followerUser);
        userRepository.save(followedUser);
        userRepository.save(followerUser);
    }
    @Override
    public void unfollow(String follower, String followed) {
        var followerOpt = userRepository.findById(follower);
        var followedOpt = userRepository.findById(followed);
        if(followedOpt.isEmpty() || followerOpt.isEmpty())
            throw new UserDoestExistException();
        var followerUser = followerOpt.get();
        var followedUser = followedOpt.get();
        if(followedUser.equals(followerUser))
            throw new UsersAreSameException();
        if(!followerUser.getFollowed().remove(followedUser))
            throw new UserDoesntFollowException();
        if(!followedUser.getFollowers().remove(followerUser))
            throw new UserDoesntFollowException();
        userRepository.save(followedUser);
        userRepository.save(followerUser);
    }
    @Override
    public Collection<User> getFollowers(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        return userOpt.get().getFollowers();
    }
    @Override
    public Collection<User> getFollowed(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        return userOpt.get().getFollowed();
    }
    @Override
    public long sumAllPostLikes(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        if(postRepository.findAllByKeyAuthor(userOpt.get()).isEmpty())
            return 0;
        var sum = userRepository.sumAllPostLikes(username);
        if(sum==null)
            return 0;
        return sum;
    }
    @Override
    public long sumLikesLikeCoWorker(String username) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        if(postRepository.findAllByKeyAuthor(userOpt.get()).isEmpty())
            return 0;
         var sum = userRepository.sumAllLikesLikeCoCreator(username);
         if(sum==null)
             return 0;
        return sum;
    }

    @Override
    public Collection<User> getAll() {
        return userRepository.findAll();
    }
}
