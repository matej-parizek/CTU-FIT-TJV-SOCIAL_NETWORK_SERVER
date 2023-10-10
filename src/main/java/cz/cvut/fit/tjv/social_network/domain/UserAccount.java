package cz.cvut.fit.tjv.social_network.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "User_account")
@Getter @Setter
public class UserAccount implements DomainEntity<String>{
    @Id
    private String username;
    @Column(nullable = false)
    private String password;

    private String realName;

    private String info;


    // TODO: 28.09.2023 Kontrola zda je to vazba many to many
    @ManyToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "username"

    )
    private final Set<UserAccount> followed = new HashSet<>();



    public UserAccount(String username, String password) {
        this.password= Objects.requireNonNull(password);
        this.username=Objects.requireNonNull(username);
    }


    public UserAccount() {

    }
    public void addFollowers(UserAccount user){
        Objects.requireNonNull(user,"User (Follower) cannot be null");
    }
    public void addFollowed(UserAccount user){
        Objects.requireNonNull(user,"User (Followee) cannot be null");

    }
    @Override
    public String getKEY() {
        return this.getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount userAccount = (UserAccount) o;

        return username.equals(userAccount.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", info='" + info + '\'' +
                ", followed=" + followed +
                '}';
    }
}
