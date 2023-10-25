package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Component
public class PostServiceImpl extends AbstractCrudServiceImpl<Post, PostKey> implements PostService{
    PostRepository postRepository;
    UserRepository userRepository;
//    public PostServiceImpl(PostRepository repository) {
//        this.repository = repository;
//    }

    public PostServiceImpl(PostRepository repository, UserRepository userRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public void update(Post entity) {
        /// TODO: 20.10.2023
        throw new RuntimeException();
    }

    @Override
    protected JpaRepository<Post, PostKey> getRepository() {
        return postRepository;
    }

    @Override
    public void coCreator(String author, URI uri, String coAuthor) {
        var optUserAuthor = userRepository.findById(author);
        var optUserCoAuthor = userRepository.findById(coAuthor);
        if(optUserCoAuthor.isEmpty() || optUserAuthor.isEmpty())
        // TODO: 24.10.2023
        {
            throw new RuntimeException();
        }
        var userAuthor = optUserAuthor.get();
        var userCoAuthor = optUserCoAuthor.get();

        // Check friends
        if(!userRepository.findFriends(author).contains(userCoAuthor))
            // TODO: 24.10.2023
            throw new RuntimeException();
        var optPost = postRepository.findById(new PostKey(userAuthor,uri));
        if(optPost.isEmpty() || userCoAuthor.equals(userAuthor))
            // TODO: 24.10.2023
            throw new RuntimeException();
        var post = optPost.get();
        var newPost = new Post(post.getKey().getUri(),userCoAuthor);
        newPost.setText(post.getText()+ "\n Author: "+author+"\n Co-Author: "+coAuthor+"\n");
        postRepository.save(newPost);
    }

    @Override
    public void wasLiked(String who,URI uri, String author) {
        var optUserWho = userRepository.findById(who);
        var optUserAuthor = userRepository.findById(author);
        if(optUserWho.isEmpty() || optUserAuthor.isEmpty())
            // TODO: 24.10.2023
            throw new RuntimeException();
        var userWho = optUserWho.get();
        var userAuthor = optUserAuthor.get();

        var optPost = postRepository.findById(new PostKey(userAuthor,uri));
        if(optPost.isEmpty())
            // TODO: 24.10.2023
            throw new RuntimeException();
        var post = optPost.get();

        post.getLikes().add(userWho);
        userWho.getLiked().add(post);
        userRepository.save(userWho);
        postRepository.save(post);
    }

    @Override
    public Collection<Post> findAll() {
        return postRepository.findAll();
    }
}
