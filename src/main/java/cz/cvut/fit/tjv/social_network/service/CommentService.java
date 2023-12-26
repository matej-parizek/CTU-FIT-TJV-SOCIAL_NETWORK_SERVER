package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Comment;

import java.util.Collection;

public interface CommentService extends CrudService<Comment, Long>{
    Collection<Comment> getAllCommentsOfUser(String username, long id);
}
