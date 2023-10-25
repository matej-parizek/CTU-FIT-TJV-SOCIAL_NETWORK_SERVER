package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Component
public class UserServiceImpl extends AbstractCrudServiceImpl<User,String>implements UserService{
    UserRepository repository;
//    PostRepository postRepository;

    protected UserServiceImpl(UserRepository repository) {
        this.repository=repository;
    }

//    public UserServiceImpl(UserRepository repository, PostRepository postRepository) {
//        this.repository = repository;
//        this.postRepository = postRepository;
//    }

    @Override
    public void update(User entity) {
        // TODO: 20.10.2023
        throw new RuntimeException();
    }

    @Override
    protected JpaRepository<User, String> getRepository() {
        return repository;
    }

    @Override
    public long sumAllLikes(String username) {
        var optUser = repository.findById(username);
        if(optUser.isEmpty())
            // TODO: 24.10.2023
            throw new RuntimeException();
        return repository.sumAllLikes(username);
    }


    @Override
    public Collection<User> findFriends(String username) {
        return repository.findFriends(username);
    }

    @Override
    public void follow(String follower, String followed) {
        var optFollower = repository.findById(follower);
        var optFollowed = repository.findById(followed);
        if(optFollowed.isEmpty() || optFollower.isEmpty())
            // TODO: 24.10.2023
            throw new RuntimeException();
        var followerUser = optFollower.get();
        var followedUser = optFollowed.get();
        if(followedUser.equals(followerUser))
            // TODO: 24.10.2023  
            throw new RuntimeException();
        followedUser.getFollowed().add(followerUser);
        repository.save(followedUser);
    }
}
