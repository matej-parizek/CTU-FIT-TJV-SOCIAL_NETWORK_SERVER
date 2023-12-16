package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.repository.CommentRepository;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

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
}
