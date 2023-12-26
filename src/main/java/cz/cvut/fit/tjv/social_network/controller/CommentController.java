package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequestMapping("user/{username}/posts/{id_post}")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/comments")
    public Comment create(@RequestBody Comment comment){
        try {
            return commentService.create(comment);
        }catch (IllegalAccessError e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/comments")
    public Iterable<Comment> getComments(@PathVariable String username, @PathVariable String id_post){
        return null;
    }
}
