package cz.cvut.fit.tjv.social_network.domain;

import java.io.Serializable;

public interface DomainEntity <K> extends Serializable {
    K getKEY();
}

