package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.repository.PostRepository;
import cz.cvut.fit.tjv.social_network.repository.UserRepository;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoesntFollowException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UserDoestExistException;
import cz.cvut.fit.tjv.social_network.service.exceptions.user.UsersAreSameException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplUnitTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    /**
    * zajišťuje, že mock objekty jsou připraveny
    *  před každým testem a nevyžadovalo by se duplikování kódu inicializace v každé testovací metodě.
    * */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateUser_valid() {
        var user = new User("test", "John Doe", "John Doe RealName");
        var userOpt = Optional.of(user);
        Mockito.when(userRepository.findById("test")).thenReturn(userOpt);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        var result = userService.update(user);

        Assertions.assertEquals(user.getId(), result.getId());
        Assertions.assertEquals(user.getRealName(), result.getRealName());
        Assertions.assertEquals(user.getInfo(), result.getInfo());
        Mockito.verify(userRepository, Mockito.times(1)).findById("test");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }


    @Test
    void testUpdateUserDoesNotExist() {
        User nonExistingUser = new User("nonExistingUser", "Non-existing User", "Some info");
        when(userRepository.findById(nonExistingUser.getId())).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> userService.update(nonExistingUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testFindFriends() {
        User user = new User("user1", "User One", "Info 1");
        User friend1 = new User("friend1", "Friend One", "Info 2");
        User friend2 = new User("friend2", "Friend Two", "Info 3");

        user.getFollowed().add(friend1);
        user.getFollowed().add(friend2);
        user.getFollowers().add(friend1);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        var friends = userService.findFriends(user.getId());

        assertEquals(1, friends.size());
        assertTrue(friends.contains(friend1));
    }

    @Test
    void testFollow() {
        User follower = new User("follower", "Follower", "Info 1");
        User followed = new User("followed", "Followed", "Info 2");

        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById(followed.getId())).thenReturn(Optional.of(followed));

        userService.follow(follower.getId(), followed.getId());

        assertTrue(follower.getFollowed().contains(followed));
        assertTrue(followed.getFollowers().contains(follower));
        verify(userRepository, times(2)).save(any());
    }

    @Test
    void testFollowSameUser() {
        User user = new User("user", "User", "Info");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(UsersAreSameException.class, () -> userService.follow(user.getId(), user.getId()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testFollowUserDoesntExist() {
        User follower = new User("follower", "Follower", "Info 1");

        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> userService.follow(follower.getId(), "nonExistingUser"));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUnfollow() {
        User follower = new User("follower", "Follower", "Info 1");
        User followed = new User("followed", "Followed", "Info 2");

        follower.getFollowed().add(followed);
        followed.getFollowers().add(follower);

        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById(followed.getId())).thenReturn(Optional.of(followed));

        userService.unfollow(follower.getId(), followed.getId());

        assertFalse(follower.getFollowed().contains(followed));
        assertFalse(followed.getFollowers().contains(follower));
        verify(userRepository, times(2)).save(any());
    }

    @Test
    void testUnfollowSameUser() {
        User user = new User("user", "User", "Info");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(UsersAreSameException.class, () -> userService.unfollow(user.getId(), user.getId()));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUnfollowUserDoesntExist() {
        User follower = new User("follower", "Follower", "Info 1");

        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById("nonExistingUser")).thenReturn(Optional.empty());

        assertThrows(UserDoestExistException.class, () -> userService.unfollow(follower.getId(), "nonExistingUser"));
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUnfollowUserDoesntFollow() {
        User follower = new User("follower", "Follower", "Info 1");
        User nonFollowed = new User("nonFollowed", "Non-Followed", "Info 2");

        when(userRepository.findById(follower.getId())).thenReturn(Optional.of(follower));
        when(userRepository.findById(nonFollowed.getId())).thenReturn(Optional.of(nonFollowed));

        assertThrows(UserDoesntFollowException.class, () -> userService.unfollow(follower.getId(), nonFollowed.getId()));
        verify(userRepository, never()).save(any());
    }
}
