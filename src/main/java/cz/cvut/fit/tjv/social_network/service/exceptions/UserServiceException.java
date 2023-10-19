package cz.cvut.fit.tjv.social_network.service.exceptions;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;

import java.util.Objects;

public class UserServiceException extends RuntimeException{
    static void followUser(UserAccount followee, UserAccount follower){
        if( followee == null)
            throw new RuntimeException("Followee cannot be null");
        if(follower == null)
            throw new RuntimeException("Follower cannot be null");
        if(followee.getPassword() == null || followee.getUsername() == null)
            throw new RuntimeException("Followee cannot have password or username as null");
        if(follower.getPassword() == null || follower.getUsername() == null)
            throw new RuntimeException("Follower cannot have password or username as null");
        if(follower.equals(followee))
            throw new RuntimeException("User cannot follow himself");
    }
}
