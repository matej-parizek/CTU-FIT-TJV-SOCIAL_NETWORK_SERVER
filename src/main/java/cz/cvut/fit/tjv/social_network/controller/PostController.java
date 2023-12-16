package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/user/{author}/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> getAllPosts(@PathVariable Optional<String> author){
        if(author.isPresent()) {
            try {
                return postService.readAllPostByAuthor(author.get());
            }
            // TODO: 14.12.2023 vytvorit http error
            catch (Exception e){
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/{id}")
    public Post getPost(@PathVariable Optional<String> author, @PathVariable long id){
        if(author.isPresent()){
            try{
                var postOpt = postService.readById(author.get(),id);
                if(postOpt.isEmpty())
                    throw new ResponseStatusException(HttpStatus.NO_CONTENT);
                return postOpt.get();
            }// TODO: 15.12.2023 errory
            catch (Exception e){throw new ResponseStatusException(HttpStatus.CONFLICT);}
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }

    @GetMapping("/{id}/likes")
    public Collection<User> getLikes(@PathVariable Optional<String> author, @PathVariable  long id){
       try {
           if(author.isEmpty())
               throw new ResponseStatusException(HttpStatus.CONFLICT);
           var likesOpt = postService.readById(author.get(),id);
            if(likesOpt.isEmpty())
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            var likes= likesOpt.get();
            return likes.getLikes();
        }catch (Exception e){        // TODO: 15.12.2023
        throw new ResponseStatusException(HttpStatus.CONFLICT);
       }
    }

    @PostMapping
    public Post create(@PathVariable Optional<String> author,@RequestBody Post post){
        try {
            if(author.isEmpty())
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            return postService.create(post);
        }catch (IllegalAccessError e){ // TODO: 15.12.2023
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
}
