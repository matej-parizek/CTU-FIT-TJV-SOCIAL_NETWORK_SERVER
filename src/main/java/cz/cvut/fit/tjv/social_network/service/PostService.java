package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.net.URI;
@Component
public class PostService extends AbstractCrudService<Post, URI> {
    public PostService(CrudRepository<Post, URI> repository) {
        super(repository);
    }

    @Override
    public void update(Post entity) {

    }
}
