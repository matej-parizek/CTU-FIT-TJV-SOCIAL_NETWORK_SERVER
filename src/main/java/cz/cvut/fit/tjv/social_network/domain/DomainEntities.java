package cz.cvut.fit.tjv.social_network.domain;

import java.io.Serializable;

public interface DomainEntities<K> extends Serializable {
    K getId();
}
