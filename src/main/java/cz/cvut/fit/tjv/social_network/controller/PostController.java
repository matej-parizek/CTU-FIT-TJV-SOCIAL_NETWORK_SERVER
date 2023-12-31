package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@RequestMapping("/user/{author}/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post create(@PathVariable String author,@RequestBody Post post){
        try {
            //TODO: udelat kdyz by Post.author != author
            return postService.create(post);
        }catch (IllegalAccessError e){ // TODO: 15.12.2023
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
    @PostMapping("/{id}/co-create")
    public void coCreate(@PathVariable String author,@PathVariable long id, @RequestParam String coAuthor){
        try {
            //TODO: udelat kdyz by Post.author != author
            postService.coCreator(author,id,coAuthor);
        }catch (IllegalAccessError e){ // TODO: 15.12.2023
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    //TODO
    @PutMapping("/{id}/likes")
    public void likePost(@PathVariable String author,@PathVariable long id, @RequestParam String like){
        try {
            postService.like(like,id,author);
            //todo errors
        }catch (Exception ignored){}
    }
    @DeleteMapping("/{id}/likes")
    public void unlikePost(@PathVariable String author,@PathVariable long id, @RequestParam String like){
        try {
            postService.unlike(like,id,author);
            //todo errors
        }catch (Exception ignored){}
    }
    @GetMapping
    public Collection<Post> getAllPosts(@PathVariable String author){
        try {
            return postService.readAllPostByAuthor(author);
        }
        // TODO: 14.12.2023 vytvorit http error
        catch (Exception e){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
    }
    @GetMapping("/{id}")
    public Post getPost(@PathVariable String author, @PathVariable long id){
        try{
                var postOpt = postService.readById(author,id);
                if(postOpt.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT);
                return postOpt.get();
            }// TODO: 15.12.2023 errory
        catch (Exception e){throw new ResponseStatusException(HttpStatus.CONFLICT);}
    }
    @GetMapping("/{id}/likes")
    public Collection<User> getLikes(@PathVariable String author, @PathVariable  long id){
        //todo errors
       try {
           return postService.likes(author,id);
        }catch (Exception e){        // TODO: 15.12.2023
        throw new ResponseStatusException(HttpStatus.CONFLICT);
       }
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String author, @PathVariable long id) {
        postService.deleteById(author,id);
    }
}
