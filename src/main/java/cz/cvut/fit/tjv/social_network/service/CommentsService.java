package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comments;
import cz.cvut.fit.tjv.social_network.domain.CommentsKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class CommentsService extends AbstractCrudService<Comments, CommentsKey>{
    public CommentsService(CrudRepository<Comments, CommentsKey> repository) {
        super(repository);
    }

    @Override
    public void update(Comments entity) {

    }
}
