package cz.cvut.fit.tjv.social_network.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface DomainEntities<K> extends Serializable {
    @JsonIgnore
    K getId();
}
