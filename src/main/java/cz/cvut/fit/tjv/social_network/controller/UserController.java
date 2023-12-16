package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.UserService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserService userService;

    public UserController (UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public User create(@RequestBody User data){

        // 500 problem serveru
        // 400 request spatně

        // Zjisteni v logu a je tam exception
        // Osetreni controllerAdvice nebo rucne
        try{
            return userService.create(data);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    //TODO
    @PutMapping()
    public User update(@RequestBody User data){
        try{
            return userService.update(data);
        }catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{username}")
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "User, which you wanna delete, doesn't exist")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.GONE);
        try {
            userService.deleteById(username.get());
        }catch (EntityDoesNotExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}")
    @ApiResponses({
            @ApiResponse(responseCode = "406"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public User getUser(@PathVariable Optional<String> username){
        if (username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        var userOpt = userService.readById(username.get());
        if(userOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userOpt.get();
    }
    @GetMapping("/{username}/followers")
    public Collection<User> getFollowers(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

        try {
            return userService.getFollowers(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/{username}/followed")
    public Collection<User> getFollowed(@PathVariable Optional<String> username){
        if(username.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);

        try {
            return userService.getFollowed(username.get());
        }catch (UserDoestExistException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
