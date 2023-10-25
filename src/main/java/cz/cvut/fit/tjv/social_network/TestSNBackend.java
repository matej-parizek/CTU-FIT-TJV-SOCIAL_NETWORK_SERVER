package cz.cvut.fit.tjv.social_network;

import cz.cvut.fit.tjv.social_network.domain.Comment;
import cz.cvut.fit.tjv.social_network.domain.Post;
import cz.cvut.fit.tjv.social_network.domain.User;
import cz.cvut.fit.tjv.social_network.service.CommentService;
import cz.cvut.fit.tjv.social_network.service.PostService;
import cz.cvut.fit.tjv.social_network.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URI;
import java.time.LocalDateTime;

public class TestSNBackend {
    public static void main(String[] args) {
        ApplicationContext springContainer = new AnnotationConfigApplicationContext("cz.cvut.fit.tjv.social_network");
        var us = springContainer.getBean(UserService.class);
        var ps = springContainer.getBean(PostService.class, us);
        var cs = springContainer.getBean(CommentService.class,us,ps);

        var u1 = new User("pepa","123");
        var u2 = new User("petr","123");
        var u3 = new User("pavla","123");
        var u4 = new User("jirka","123");
        var u5 = new User("adam","123");
        var u6 = new User("sophie","123");
        var u7 = new User("kuba","123");
//
        var p1 = new Post(URI.create("PEPA/URI/01"),u1); p1.setAdded(LocalDateTime.now());
        var p2 = new Post(URI.create("PEPA/URI/02"),u1);p2.setAdded(LocalDateTime.now());
        var p3 = new Post(URI.create("KUBA/01"),u7);p3.setAdded(LocalDateTime.now());


        us.create(u1);
        us.create(u2);
        us.create(u3);
        us.create(u4);
        us.create(u5);
        us.create(u6);
        us.create(u7);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);

        ps.wasLiked("sophie",URI.create("KUBA/01"),"kuba");
        ps.wasLiked("adam",URI.create("PEPA/URI/01"),"pepa");
        ps.wasLiked("kuba",URI.create("PEPA/URI/01"),"pepa");
        ps.wasLiked("kuba",URI.create("PEPA/URI/02"),"pepa");

        us.follow("sophie","kuba");
        us.follow("kuba","sophie");
        us.follow("kuba","pepa");
        us.follow("pepa","sophie");

        var com1 = new Comment(1L,u4,p1);
        com1.setText("New com");
        cs.create(com1);
        var com2 = new Comment(2L,u4,p1);
        com2.setText("New com2");
        cs.create(com2);

        ps.coCreator(p3.getKey().getAuthor().getUsername(),p3.getId().getUri(), "sophie");

        System.out.println(p1.toString());
    }
}
