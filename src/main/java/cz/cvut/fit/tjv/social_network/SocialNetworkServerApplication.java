package cz.cvut.fit.tjv.social_network;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SocialNetworkServerApplication {

    public static void main(String[] args) {
        UserAccount userAccount = new UserAccount("parizmat","Sounema-39");
        SpringApplication.run(SocialNetworkServerApplication.class, args);
    }

}
