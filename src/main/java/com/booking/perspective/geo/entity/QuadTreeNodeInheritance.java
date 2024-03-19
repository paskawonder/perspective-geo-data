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

@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuadTreeNodeInheritance {
    
    @EmbeddedId
    private Id id;
    
    public QuadTreeNodeInheritance(String idParent, String idChild) {
        this.id = new Id(idParent, idChild);
    }
    
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    private static class Id implements Serializable {
        @Column(name = "id_parent", columnDefinition="CHAR(36)")
        private String idParent;
        @Column(name = "id_child", columnDefinition="CHAR(36)")
        private String idChild;
    }
    
}
