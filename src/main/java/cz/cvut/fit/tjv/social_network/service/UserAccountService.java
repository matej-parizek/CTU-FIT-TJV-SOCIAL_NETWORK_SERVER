package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAccountService extends AbstractCrudService<UserAccount,String> {

    protected UserAccountService(CrudRepository<UserAccount,String> repository) {
        super(repository);

    }
    // TODO: 29.09.2023 kontrola zda to je spravne
    /**
     * User follows another user
     *
     * @param follower object of {@link UserAccount} user who start follow another user
     * @param followee object of {@link UserAccount} user who had been followed
     */
    public void follow(UserAccount follower, UserAccount followee){
        followee.addFollowers(follower);
        follower.addFollowed(followee);
    }

    public long numberOfUsers(){
        return repository.count();
    }

    @Override
    public void update(UserAccount entity) {
        String id = entity.getKEY();
        Objects.requireNonNull(id,"When updating, id (key) cannot be null");
        if(repository.existsById(id))
            repository.save(entity);
        else
            throw new RuntimeException();
    }

}
