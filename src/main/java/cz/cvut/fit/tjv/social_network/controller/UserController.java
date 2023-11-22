package cz.cvut.fit.tjv.social_network.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//HTTP request na ty bude reagovat

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController (UserService userService){
        this.userService=userService;
    }
    // RequestBody == ???

    /**
     * create json
     *
     * pomoci setUsername se vybere Username se jeste zmeni na username
     *
     * pokud neco nechceme v json tak pouzijeme @JsonIgnore
     */

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
    //@DeleteMapping()
    //PutMapping()

    //ResponseStatus
}
// todo otazka lombok jak pouzit JsonIgnore
//OralStrou

//documentace swagger