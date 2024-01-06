package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.PostService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.LikeDoesntExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.post.PostDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreNotFriendsException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
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
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "User does not exist!", content = @Content),
            @ApiResponse(responseCode = "409", description = "Post with this id already exist!",content = @Content),
            @ApiResponse(responseCode = "405", description = "Author of the post is not current user!",content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public Post create(@PathVariable String author,@RequestBody Post post){
        try {
            if(!post.getKey().getAuthor().getUsername().equals(author))
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
            return postService.create(post);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (EntityAlreadyExistException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
    @PostMapping("/{id}/co-create")
    @ApiResponses({
            @ApiResponse(responseCode = "405", description = "User cannot create Co-create post for himself!" ,content = @Content),
            @ApiResponse(responseCode = "404", description = "User or post does not exist!", content = @Content),
            @ApiResponse(responseCode = "409",description = "CoCreation cannot be between users, which are not friends!",content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public void coCreate(@PathVariable String author,@PathVariable long id, @RequestParam String coAuthor){
        try {
            if(author.equals(coAuthor))
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
            postService.coCreator(author,id,coAuthor);
        }catch (IllegalAccessError | UsersAreNotFriendsException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }catch (PostDoesNotExistException | UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/likes")
    @ApiResponses({
            @ApiResponse(responseCode = "405",description = "Post or User does not exist!",content = @Content),
            @ApiResponse(responseCode = "409", description = "User cannot like his own post!",content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public void likePost(@PathVariable String author,@PathVariable long id, @RequestParam String like){
        try {
            postService.like(like,id,author);
        }catch (UserDoestExistException | PostDoesNotExistException e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }catch (UsersAreSameException e){
            throw new HttpClientErrorException(HttpStatus.CONFLICT);
        }
    }


    @DeleteMapping("/{id}/likes")
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "User or post does not exist!", content = @Content),
            @ApiResponse(responseCode = "409",description = "Post is not liked from user!", content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public void unlikePost(@PathVariable String author,@PathVariable long id, @RequestParam String like){
        try {
            postService.unlike(like,id,author);
        }catch (PostDoesNotExistException | UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (LikeDoesntExistException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User does not exist!", content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public Collection<Post> getAllPosts(@PathVariable String author){
        try {
            return postService.readAllPostByAuthor(author);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "User or post does not exist!",content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public Post getPost(@PathVariable String author, @PathVariable long id){
        try{
                var postOpt = postService.readById(author,id);
                if(postOpt.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                return postOpt.get();
            }
        catch (UserDoestExistException | PostDoesNotExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/likes")
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "User or post does not exist!", content = @Content),
            @ApiResponse(responseCode = "200")
    })
    public Collection<User> getLikes(@PathVariable String author, @PathVariable  long id){
       try {
           return postService.likes(author,id);
        }catch (UserDoestExistException | PostDoesNotExistException e){
        throw new ResponseStatusException(HttpStatus.CONFLICT);
       }
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String author, @PathVariable long id) {
        postService.deleteById(author,id);
    }
    @GetMapping("/followed")
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "User does not exist!", content = @Content)
    })
    public Collection<Post> followedPosts(@PathVariable String author){
        try{
            return postService.getAllFollowedPosts(author);
        }catch (UserDoestExistException e){
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }
}
