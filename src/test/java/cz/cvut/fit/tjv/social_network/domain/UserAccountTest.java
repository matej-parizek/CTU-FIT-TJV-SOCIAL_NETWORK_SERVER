package cz.cvut.fit.tjv.social_network.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class UserAccountTest {

    @Test
    void getKEY() {
    }

    @Test
    void testEquals() {
    }

    @Test
    void getUsername() {
    }

    @Test
    void getPassword() {
    }

    @Test
    void getRealName() {
    }

    @Test
    void getInfo() {
    }

    @Test
    void getFollowed() {
    }
    @Test
    void getFollowersAddNull(){
        UserAccount user1= new UserAccount("user1","12345678");
        UserAccount user2= new UserAccount("user2","12345678");
        UserAccount user3= null;
        assertDoesNotThrow(()->user1.addFollowed(user2));
        assertThrows(NullPointerException.class,()->user1.addFollowed(user3));
    }
    @Test
    void getFollowersCorrect() {
        UserAccount user1= new UserAccount("user1","12345678");
        UserAccount user2= new UserAccount("user2","12345678");
        UserAccount user3= new UserAccount("user3","12345678");
        UserAccount user4= new UserAccount("user4","12345678");
            //
        user1.getFollowers().addAll( Arrays.asList(user2,user3,user4,user2));
        Set<UserAccount> correct = new HashSet<UserAccount>();
        correct.add(user2);
        correct.add(user3);
        correct.add(user4);
        assertEquals(correct,user1.getFollowers());
    }
}