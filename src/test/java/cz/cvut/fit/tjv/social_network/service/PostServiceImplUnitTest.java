/*
 * @Copyright Matej Parizek
 */

package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceImplUnitTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPostSuccessful() {
        var author = new User("author");
        var post = new Post(new PostKey(author,1L));
        when(userRepository.findById(author.getUsername())).thenReturn(Optional.of(author));
        when(postRepository.save(any())).thenReturn(post);

        Post createdPost = postService.create(post);
        assertNotNull(createdPost);
        assertEquals(author, createdPost.getKey().getAuthor());
        verify(postRepository, times(1)).save(any());
    }

    @Test
    void createPostUserDoesNotExistExceptionThrown() {
        Post post = new Post();
        post.setKey(new PostKey(new User(), 1L));

        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> postService.create(post));
        verify(postRepository, never()).save(any());
    }

    @Test
    void readAllPostByAuthorUserDoesNotExistExceptionThrown() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> postService.readAllPostByAuthor("NonExistenUser"));
    }

    @Test
    void readByIdUserDoesNotExistExceptionThrown() {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> postService.readById("NonExistenUser", 1L));
    }

    @Test
    void readByIdPostDoesNotExistExceptionThrown() {
        User user = new User();
        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(postRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(PostDoesNotExistException.class, () -> postService.readById("ExistingUser", 1L));
    }

    @Test
    void likesPostDoesNotExistExceptionThrown() {
        User author = new User("author");
        when(postRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.findById(any())).thenReturn(Optional.of(author));
        assertThrows(PostDoesNotExistException.class, () -> postService.likes("ExistingUser", 1L));
    }

    @Test
    void deleteByIdPostDoesNotExistExceptionThrown() {
        User author = new User("author");
        when(postRepository.findById(any())).thenReturn(Optional.empty());
        when(userRepository.findById(any())).thenReturn(Optional.of(author));
        assertThrows(PostDoesNotExistException.class, () -> postService.deleteById("ExistingUser", 1L));
    }

    @Test
    void getAllFollowedPostsSuccessful() {
        when(postRepository.findAllFollowedPosts(anyString())).thenReturn(new ArrayList<>());

        Collection<Post> followedPosts = postService.getAllFollowedPosts("ExistingUser");

        assertNotNull(followedPosts);
        verify(postRepository, times(1)).findAllFollowedPosts(anyString());
    }

    @Test
    void coCreatorSuccessful() {
        String author = "author";
        long postId = 1L;
        String coAuthor = "coAuthor";

        User userAuthor = new User();
        userAuthor.setUsername(author);

        User userCoAuthor = new User();
        userCoAuthor.setUsername(coAuthor);
        userCoAuthor.getFollowers().add(userAuthor);
        userCoAuthor.getFollowed().add(userAuthor);

        userAuthor.getFollowers().add(userCoAuthor);
        userAuthor.getFollowed().add(userCoAuthor);


        PostKey postKey = new PostKey(userAuthor, postId);
        Post post = new Post(postId, userAuthor);
        post.setAdded(LocalDateTime.now());

        when(userRepository.findById(author)).thenReturn(Optional.of(userAuthor));
        when(userRepository.findById(coAuthor)).thenReturn(Optional.of(userCoAuthor));
        when(postRepository.findById(postKey)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));
        postService.coCreator(author, postId, coAuthor);

        verify(userRepository, times(1)).findById(author);
        verify(userRepository, times(1)).findById(coAuthor);
        verify(postRepository, times(1)).findById(postKey);

        verify(postRepository, times(2)).save(any(Post.class));
    }

    @Test
    void likePostSuccessful() {
        String who = "who";
        long postId = 1L;
        String author = "author";

        User userWho = new User();
        userWho.setUsername(who);

        User userAuthor = new User();
        userAuthor.setUsername(author);

        PostKey postKey = new PostKey(userAuthor, postId);
        Post post = new Post(postId, userAuthor);
        post.setAdded(LocalDateTime.now());

        when(userRepository.findById(who)).thenReturn(Optional.of(userWho));
        when(userRepository.findById(author)).thenReturn(Optional.of(userAuthor));
        when(postRepository.findById(postKey)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.like(who, postId, author);

        verify(userRepository, times(1)).findById(who);
        verify(userRepository, times(1)).findById(author);
        verify(postRepository, times(1)).findById(postKey);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void unlikePostSuccessful() {
        String who = "who";
        long postId = 1L;
        String author = "author";

        User userWho = new User();
        userWho.setUsername(who);

        User userAuthor = new User();
        userAuthor.setUsername(author);

        PostKey postKey = new PostKey(userAuthor, postId);
        Post post = new Post(postId, userAuthor);
        post.setAdded(LocalDateTime.now());
        post.getLikes().add(userWho);

        when(userRepository.findById(who)).thenReturn(Optional.of(userWho));
        when(userRepository.findById(author)).thenReturn(Optional.of(userAuthor));
        when(postRepository.findById(postKey)).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.unlike(who, postId, author);

        verify(userRepository, times(1)).findById(who);
        verify(userRepository, times(1)).findById(author);
        verify(postRepository, times(1)).findById(postKey);
        verify(postRepository, times(1)).save(any(Post.class));
    }

}
