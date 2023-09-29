package cz.cvut.fit.tjv.social_network.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
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
    @ManyToMany
    private final Set<UserAccount> followed = new HashSet<>();
    @ManyToMany
    private final Set<UserAccount> followers = new HashSet<>();


    public UserAccount(String username, String password) {
        this.password=password;
        this.username=username;
    }


    public UserAccount() {

    }
    public void addFollowers(UserAccount ... users){
        followers.addAll(Arrays.asList(users));
    }
    public void addFollowed(UserAccount ... users){
        followed.addAll(Arrays.asList(users));
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
                ", followers=" + followers +
                '}';
    }
}
