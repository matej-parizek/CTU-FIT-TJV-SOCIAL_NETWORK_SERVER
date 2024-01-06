package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.Authorization;

import java.util.Optional;

public interface AuthorizationService{
    Authorization create(Authorization authorization);
    void deleteById(String username);
    Optional<Authorization> readById(String username,String password);
}
