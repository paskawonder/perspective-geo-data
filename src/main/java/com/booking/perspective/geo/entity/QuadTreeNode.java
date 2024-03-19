package com.booking.perspective.geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class QuadTreeNode {
    
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "left_top_lat")
    private Double leftTopLat;
    @Column(name = "left_top_lon")
    private Double leftTopLon;
    @Column(name = "right_bot_lat")
    private Double rightBotLat;
    @Column(name = "right_bot_lon")
    private Double rightBotLon;
    @ManyToMany
    private Collection<QuadTreeNode> childs = new ArrayList<>();
    @ManyToMany
    private Collection<QuadTreeNode> adjs = new ArrayList<>();
    @ManyToMany
    private QuadTreeNodeLoad load;
    
}
