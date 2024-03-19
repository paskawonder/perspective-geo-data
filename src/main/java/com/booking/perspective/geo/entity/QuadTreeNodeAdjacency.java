package com.booking.perspective.geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuadTreeNodeAdjacency {
    
    @EmbeddedId
    private Id id;
    
    public QuadTreeNodeAdjacency(Long id1, Long id2) {
        id = new Id(id1, id2);
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    private static class Id implements Serializable {
        @Column(name = "id_1")
        private Long id1;
        @Column(name = "id_2")
        private Long id2;
    }
    
}
