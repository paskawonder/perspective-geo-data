package com.booking.perspective.geo.entity;

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
public class RectTreeNodeInheritance {
    
    private Long idParent;
    private Long idChild;
    
}
