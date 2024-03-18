package com.booking.perspective.geo.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RectTreeNode {
    
    private Long id;
    private Double leftTopLat;
    private Double leftTopLon;
    private Double rightBotLat;
    private Double rightBotLon;
    private Collection<RectTreeNode> childs = new ArrayList<>();
    private Collection<RectTreeNode> adjs = new ArrayList<>();
    private RectTreeNodeLoad load;
    
}
