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
