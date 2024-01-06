package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.CommentRepository;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImpUnitTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CommentServiceImp commentService;

    @Test
    void testUpdate() {
        Comment comment = new Comment();
        comment.setIdComment(1L);

        Mockito.when(commentRepository.save(comment)).thenReturn(comment);

        Comment updatedComment = commentService.update(comment);

        assertNotNull(updatedComment);
        assertEquals(1L, updatedComment.getId());
    }

    @Test
    void testGetRepository() {
        JpaRepository<Comment, Long> repository = commentService.getRepository();
        assertNotNull(repository);
    }

    @Test
    void testGetAllCommentsOfUser() {
        String username = "testUser";
        long postId = 1L;
        User user = new User();
        user.setUsername(username);
        Post post = new Post();
        post.setKey(new PostKey(user, postId));

        Comment comment1 = new Comment();
        comment1.setIdComment(1L);
        comment1.setToPost(post);
        Comment comment2 = new Comment();
        comment2.setIdComment(2L);
        comment2.setToPost(post);
        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.of(post));
        Mockito.when(commentRepository.findCommentByToPost(post)).thenReturn(List.of(comment1, comment2));
        Collection<Comment> comments = commentService.getAllCommentsOfUser(username, postId);
        assertNotNull(comments);

        assertEquals(2, comments.size());
        assertTrue(comments.contains(comment1));
        assertTrue(comments.contains(comment2));
    }

    @Test
    void testGetAllCommentsOfUserUserDoesNotExistException() {
        String username = "nonexistentUser";
        long postId = 1L;

        Mockito.when(userRepository.findById(username)).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> commentService.getAllCommentsOfUser(username, postId));
    }

    @Test
    void testGetAllCommentsOfUserPostDoesNotExistException() {
        String username = "testUser";
        long postId = 1L;

        User user = new User();
        user.setUsername(username);

        Mockito.when(userRepository.findById(username)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        assertThrows(PostDoesNotExistException.class, () -> commentService.getAllCommentsOfUser(username, postId));
    }
}
