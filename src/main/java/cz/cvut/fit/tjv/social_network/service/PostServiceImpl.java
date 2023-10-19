package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import cz.cvut.fit.tjv.social_network.service.exceptions.AuthorIsSameAsUserException;
import cz.cvut.fit.tjv.social_network.service.exceptions.PostOrUserNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class PostServiceImpl extends AbstractCrudServiceImpl<Post, PostKey> implements PostService{
    JpaRepository<UserAccount, String> userRepository;
    public PostServiceImpl(JpaRepository<Post, PostKey> repository, JpaRepository<UserAccount,String> userRepository) {
        super(repository);
        this.userRepository=userRepository;
    }

    @Override
    public void update(Post entity) {
    }

    @Override
    public void likePost(PostKey key, String username) {
        if(username == null || key ==null)
            throw new PostOrUserNotExistException();
        Optional<Post> optPost = repository.findById(key);
        Optional<UserAccount> optUser = userRepository.findById(username);

        if(optPost.isEmpty() || optUser.isEmpty())
            throw new PostOrUserNotExistException();

        var post = optPost.get();
        var user = optUser.get();

        if(user.equals(post.getAuthor()))
            throw new AuthorIsSameAsUserException();
        post.getLikes().add(user);
        repository.save(post);
    }

    @Override
    public void addCoCreator(PostKey key, String username) {
        if(username == null || key ==null)
            throw new PostOrUserNotExistException();
        Optional<Post> optPost = repository.findById(key);
        Optional<UserAccount> optUser = userRepository.findById(username);

        if(optPost.isEmpty() || optUser.isEmpty())
            throw new PostOrUserNotExistException();
        var post = optPost.get();
        var user = optUser.get();

        if(user.equals(post.getAuthor()))
            throw new AuthorIsSameAsUserException();

        Post newPost = new Post(post.getUri(), user);
        
        repository.save(newPost);
    }

}
