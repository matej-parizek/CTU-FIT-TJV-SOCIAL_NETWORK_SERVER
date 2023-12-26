package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.domain.PostKey;
import cz.cvut.fit.tjv.social_network.repository.CommentRepository;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CommentServiceImp extends AbstractCrudServiceImpl<Comment, Long> implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentServiceImp(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment update(Comment entity) {
        throw new RuntimeException();
    }

    @Override
    protected JpaRepository<Comment, Long> getRepository() {
        return commentRepository;
    }

    @Override
    public Collection<Comment> getAllCommentsOfUser(String username, long id) {
        var userOpt = userRepository.findById(username);
        if(userOpt.isEmpty())
            throw new UserDoestExistException();
        var postOpt = postRepository.findById(new PostKey(userOpt.get(), id));
        if(postOpt.isEmpty())
            throw new PostDoesNotExistException();
        return commentRepository.findCommentByToPost(postOpt.get());
    }
}
