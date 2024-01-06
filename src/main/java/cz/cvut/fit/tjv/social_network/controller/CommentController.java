package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.service.CommentService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("user/{author}/posts/{id}")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/comments")
    @ApiResponses({
            @ApiResponse(responseCode = "409",description = "Comment already exist!", content = @Content)
    })
    public Comment create(@RequestBody Comment comment){
        try {
            return commentService.create(comment);
        }catch (IllegalAccessError | EntityAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/comments")
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "Comments of the post are not unavailable, because user or post does not exist!")
    })
    public Collection<Comment> getComments(@PathVariable String author, @PathVariable Long id){
        try{
                return commentService.getAllCommentsOfUser(author,id);
        }catch (UserDoestExistException | PostDoesNotExistException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
