package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.UserService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoesntFollowException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody User data) {
        //TODO
        // 500 problem serveru
        // 400 request spatně

        // Zjisteni v logu a je tam exception
        // Osetreni controllerAdvice nebo rucne
        try {
            return userService.create(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    //TODO
    @PutMapping
    public User update(@RequestBody User data) {
        try {
            return userService.update(data);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Optional<String> username) {
        if (username.isEmpty())
            throw new ResponseStatusException(HttpStatus.GONE);
        try {
            userService.deleteById(username.get());
        } catch (EntityDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{username}/follow")
    public void follow(@PathVariable Optional<String> username, @RequestParam Optional<String> follow){
        if(username.isEmpty() || follow.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            userService.follow(username.get(), follow.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (UsersAreSameException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/{username}/unfollow")
    public void unfollow(@PathVariable Optional<String> username, @RequestParam Optional<String> unfollow){
        if(username.isEmpty() || unfollow.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            userService.unfollow(username.get(), unfollow.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (UsersAreSameException | UserDoesntFollowException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

    }
    @GetMapping("/{username}")
    public User getUser(@PathVariable Optional<String> username){
        if (username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        var userOpt = userService.readById(username.get());
        if(userOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userOpt.get();
    }
    @GetMapping("/{username}/followers")
    public Iterable<User> getFollowers(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

        try {
            return userService.getFollowers(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{username}/followed")
    public Iterable<User> getFollowed(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            return userService.getFollowed(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/co-creator-likes")
    public long coCreatorLikes(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            return userService.sumLikesLikeCoWorker(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/all-likes")
    public long allLikes(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            return userService.sumAllLikes(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/post-likes")
    public long postLikes(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try {
            return userService.sumAllPostLikes(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{username}/friends")
    public Iterable<User> getFriends(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        try{
            return userService.findFriends(username.get());
        }catch (UserDoestExistException e ){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
