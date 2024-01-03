package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.LikeDoesntExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreNotFriendsException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class PostServiceImpl extends AbstractCrudServiceImpl<Post, PostKey> implements PostService{
    final private PostRepository postRepository;

    final private UserRepository userRepository;
//    public PostServiceImpl(PostRepository repository) {
//        this.repository = repository;
//    }

    public PostServiceImpl(PostRepository repository, UserRepository userRepository) {
        this.postRepository = repository;
        this.userRepository = userRepository;
    }
    @Override
    public Post create(Post entity){
        var authorOpt = userRepository.findById(entity.getKey().getAuthor().getUsername());
        if(authorOpt.isEmpty())
            throw new UserDoestExistException();
        entity.getKey().setAuthor(authorOpt.get());
        return super.create(entity);
    }
    @Override
    public Post update(Post entity) {
        return postRepository.save(entity);
    }

    @Override
    protected JpaRepository<Post, PostKey> getRepository() {
        return postRepository;
    }

    @Override
    public void coCreator(String author, long id, String coAuthor) {
        var optUserAuthor = userRepository.findById(author);
        if( optUserAuthor.isEmpty()){
            throw new UserDoestExistException();
        }
        var userAuthor = optUserAuthor.get();
        // Check friends
        List<User> friends = new ArrayList<>(userAuthor.getFollowed());
        friends.retainAll(userAuthor.getFollowers());
        var coAuthorUserOpt = userAuthor.getFollowed().stream()
                .filter(user -> user.getUsername().equals(coAuthor))
                .findFirst();
        if(friends.isEmpty() ||coAuthorUserOpt.isEmpty()) {
            throw new UsersAreNotFriendsException();
        }
        var userCoAuthor = coAuthorUserOpt.get();
        var optPost = postRepository.findById(new PostKey(userAuthor,id));
        if(optPost.isEmpty())
        {
            throw new PostDoesNotExistException();
        }
        var post = optPost.get();
        long newId = this.readAllPostByAuthor(coAuthor).size();
        var newPost = new Post(newId,userCoAuthor); newPost.setAdded(LocalDateTime.now());newPost.setImage(post.getImage());
        String text = post.getText();
        if(text==null)
            text="";
        post.setText(text+ "\nAuthor: "+author+"\nCo-Author: "+coAuthor);
        newPost.setText(text+ "\nAuthor: "+author+"\nCo-Author: "+coAuthor);
        getRepository().save(newPost);
        getRepository().save(post);
//        postRepository.save(newPost);
    }

    @Override
    public void like(String who, long id, String author) {
        var optUserWho = userRepository.findById(who);
        var optUserAuthor = userRepository.findById(author);
        if(optUserWho.isEmpty() || optUserAuthor.isEmpty())
            throw new UserDoestExistException();
        var userWho = optUserWho.get();
        var userAuthor = optUserAuthor.get();
        var optPost = postRepository.findById(new PostKey(userAuthor,id));
        if(optPost.isEmpty())
            throw new PostDoesNotExistException();
        var post = optPost.get();

        post.getLikes().add(userWho);
        postRepository.save(post);
    }
    @Override
    public void unlike(String who, long id, String author ){
        var optUserWho = userRepository.findById(who);
        var optUserAuthor = userRepository.findById(author);
        if(optUserWho.isEmpty() || optUserAuthor.isEmpty())
            throw new UserDoestExistException();
        var userWho = optUserWho.get();
        var userAuthor = optUserAuthor.get();
        var optPost = postRepository.findById(new PostKey(userAuthor,id));
        if(optPost.isEmpty())
            throw new PostDoesNotExistException();
        var post = optPost.get();
        if(!post.getLikes().remove(userWho))
            throw new LikeDoesntExistException();
        postRepository.save(post);
    }

    @Override
    public Collection<Post> readAllPostByAuthor(String author) {
        var authorOfPosts = userRepository.findById(author);
        if(authorOfPosts.isEmpty())
            throw new UsersAreNotFriendsException();
        return postRepository.findAllByKeyAuthor(authorOfPosts.get());
    }

    @Override
    public Optional<Post> readById(String username, long id) {
        var userOpt=userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();

        var post = postRepository.findById(new PostKey(userOpt.get(),id));
        if(post.isEmpty())
            throw new PostDoesNotExistException();
        return post;
    }

    @Override
    public Collection<User> likes(String username, long id) {
        var postOpt = readById(username,id);
        if(postOpt.isEmpty())
            throw new PostDoesNotExistException();
        return postOpt.get().getLikes();
    }

    @Override
    public void deleteById(String username, long id){
        var optPost = readById(username,id);
        if (optPost.isEmpty())
            throw new PostDoesNotExistException();
        postRepository.deleteById(optPost.get().getKey());
    }

    @Override
    public Collection<Post> getAllFollowedPosts(String username) {
        var posts =  postRepository.findAllFollowedPosts(username);
        if(posts==null)
            return new ArrayList<>();
        return posts;
    }
}
