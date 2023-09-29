package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import cz.cvut.fit.tjv.social_network.repository.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.api.VerificationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserAccountServiceTest {
    @MockBean
    UserAccountRepository repository;
    @Autowired
    private UserAccountService service;
//    @Test
//    void readById(){
//        UserAccount user1=new UserAccount("user1");
//        UserAccount user2=new UserAccount("user2");
//        UserAccount user3=new UserAccount("user3");
//        Mockito.atLeastO;
//        var user = service.readById("user1");
//       assertEquals(user1,user.orElse(null));
//    }
    @Test
    void createUser(){
        UserAccount userDtoRequest = new UserAccount();
        userDtoRequest.setUsername("Alex");
        userDtoRequest.setPassword("123");

//        UserDto found = userService.create(userDtoRequest);
//        assertThat(found.getAuthToken()).isNotEmpty();
    }
    @Test
    void singInWithoutId(){
        UserAccount user = new UserAccount();
        assertThrows(RuntimeException.class,()->service.create(user));
    }
    @Test
    void follow() {
    }

    @Test
    void numberOfUsers() {
    }

    @Test
    void update() {
    }
}