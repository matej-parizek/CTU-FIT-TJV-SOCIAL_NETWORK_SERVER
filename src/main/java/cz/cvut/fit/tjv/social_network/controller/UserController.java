package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.AuthorizationService;
import cz.cvut.fit.tjv.social_network.service.UserService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoesntFollowException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@ApiResponses({
        @ApiResponse(responseCode = "404", description = "User does not exist", content = @Content),
        @ApiResponse(responseCode = "200")

})
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public UserController(UserService userService, AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses( {
        @ApiResponse(responseCode = "409", description = "Attempt to create existing user", content = @Content),
    })
    public User create(@RequestBody User data) {
        try {
            return userService.create(data);
        } catch (EntityAlreadyExistException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @GetMapping
    public Collection<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("/{username}")
    public User update(@RequestBody User user, @PathVariable String username) {
        try {
            if(!username.equals(user.getUsername()))
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            return userService.update(user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
    })
    public void delete(@PathVariable String username) {
        try {
            userService.deleteById(username);
            authorizationService.deleteById(username);

        } catch (EntityDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
    }
    @PutMapping("/{username}/follow")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "User cannot follow himself", content = @Content)
    })
    public void follow(@PathVariable String username, @RequestParam String follow){
        try {
            userService.follow(username, follow);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (UsersAreSameException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @DeleteMapping("/{username}/follow")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "User cannot follow himself", content = @Content)
    })
    public void unfollow(@PathVariable String username, @RequestParam String follow){
        try {
            userService.unfollow(username, follow);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (UsersAreSameException | UserDoesntFollowException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
    @GetMapping("/{username}")
    @ApiResponses({
            
    })
    public User getUser(@PathVariable String username){
        var userOpt = userService.readById(username);
        if(userOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userOpt.get();
    }
    @GetMapping("/{username}/followers")
    @ApiResponses({
            
    })
    public Iterable<User> getFollowers(@PathVariable String username){
        try {
            return userService.getFollowers(username);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{username}/follow")
    @ApiResponses({
    })
    public Iterable<User> getFollowed(@PathVariable  String username){
        try {
            return userService.getFollowed(username);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/sum-co-creator-likes")
    @ApiResponses({})
    public long coCreatorLikes(@PathVariable String username){
        try {
            return userService.sumLikesLikeCoWorker(username);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}/sum-post-likes")
    @ApiResponses({
            
    })    
    public long postLikes(@PathVariable String username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            return userService.sumAllPostLikes(username);
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/friends")
    @ApiResponses({})
    public Iterable<User> getFriends(@PathVariable String username){
        try{
            return userService.findFriends(username);
        }catch (UserDoestExistException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
