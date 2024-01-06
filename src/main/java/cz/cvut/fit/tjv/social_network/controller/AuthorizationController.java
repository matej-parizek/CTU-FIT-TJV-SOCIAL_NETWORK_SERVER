package cz.cvut.fit.tjv.social_network.controller;

import cz.cvut.fit.tjv.social_network.domain.Authorization;
import cz.cvut.fit.tjv.social_network.service.AuthorizationService;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.PasswordIsIncorrectException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping
    @ApiResponse(responseCode = "409",description = "User already exist!")
    public void create(@RequestBody Authorization authorization){
        try {
            authorizationService.create(authorization);
        }catch (EntityAlreadyExistException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/{username}")
    @ApiResponses({
            @ApiResponse(responseCode = "409", description = "Password is incorrect!",content = @Content)
    })
     public Boolean getPassword(@PathVariable("username") String username,@RequestParam String password){
        try {
            if(authorizationService.readById(username,password).isEmpty())
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            return true;
        }catch (EntityDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }catch (PasswordIsIncorrectException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
