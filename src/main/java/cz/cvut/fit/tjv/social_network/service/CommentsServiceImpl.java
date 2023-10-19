package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comments;
import cz.cvut.fit.tjv.social_network.domain.CommentsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentsServiceImpl extends AbstractCrudServiceImpl<Comments, CommentsKey> {
    public CommentsServiceImpl(JpaRepository<Comments, CommentsKey> repository) {
        super(repository);
    }


    @Override
    public void update(Comments entity) {

    }
}
