package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Authorization;
import cz.cvut.fit.tjv.social_network.repository.AuthorizationRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityAlreadyExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.EntityDoesNotExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.PasswordIsIncorrectException;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuthorizationServiceImp implements AuthorizationService{
    private final AuthorizationRepository authorizationRepository;

    public AuthorizationServiceImp(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }


    @Override
    public Authorization create(Authorization authorization) {
        if(authorizationRepository.existsById(authorization.getUsername()))
            throw  new EntityAlreadyExistException();
        return authorizationRepository.save(authorization);
    }

    @Override
    public void deleteById(String username) {
        if (!authorizationRepository.existsById(username))
            throw new EntityDoesNotExistException();
        authorizationRepository.deleteById(username);
    }

    @Override
    public Optional<Authorization> readById(String username,String password) {
        if (!authorizationRepository.existsById(username))
            throw new EntityDoesNotExistException();
        var authorizationOpt = authorizationRepository.findById(username);
        if(authorizationOpt.isEmpty())
            throw new EntityDoesNotExistException();
        var authorization = authorizationOpt.get();
        if(authorization.getPassword().equals(password))
            return Optional.of(authorization);
        throw new PasswordIsIncorrectException();
    }
}
