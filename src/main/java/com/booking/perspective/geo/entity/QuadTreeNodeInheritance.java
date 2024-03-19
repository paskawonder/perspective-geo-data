package com.booking.perspective.geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuadTreeNodeInheritance {
    
    @Column(name = "id_parent")
    private Long idParent;
    @Column(name = "id_child")
    private Long idChild;
    
}
