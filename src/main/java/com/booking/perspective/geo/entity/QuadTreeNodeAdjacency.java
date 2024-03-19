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
public class QuadTreeNodeAdjacency {
    
    @Column(name = "id_1")
    private Long id1;
    @Column(name = "id_2")
    private Long id2;
    
}
