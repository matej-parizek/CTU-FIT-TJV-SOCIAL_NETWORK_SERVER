package cz.cvut.fit.tjv.social_network.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "user_account")
@Getter @Setter
public class UserAccount implements DomainEntity<String>{
    @Id
    private String username;

    private String realName;

    private String info;

    // TODO: 28.09.2023 Kontrola zda je to vazba many to many
    @ManyToMany
    private final Set<UserAccount> followed = new HashSet<>();
    @ManyToMany
    private final Set<UserAccount> followers = new HashSet<>();

    public UserAccount(String username) {
        this.username = username;
    }

    public UserAccount(String username, String realName) {
        this(username);
        this.realName = realName;
    }

    public UserAccount(String username, String realName, String info) {
        this(username,realName);
        this.info = info;
    }

    public UserAccount() {

    }

    @Override
    public String getID() {
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
}
