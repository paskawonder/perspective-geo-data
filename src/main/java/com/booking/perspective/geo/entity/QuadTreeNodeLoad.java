package com.booking.perspective.geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class QuadTreeNodeLoad {
    
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "expected_factor")
    private Integer expectedFactor;
    
}
