package cz.cvut.fit.tjv.social_network.service;

import cz.cvut.fit.tjv.social_network.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserAccountServiceImpl extends AbstractCrudServiceImpl<UserAccount,String> implements UserAccountService {

    protected UserAccountServiceImpl(JpaRepository<UserAccount,String> repository) {
        super(repository);

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
