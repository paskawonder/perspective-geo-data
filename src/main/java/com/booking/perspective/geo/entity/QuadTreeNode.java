package com.booking.perspective.geo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private BigDecimal leftTopLat;
    @Column(name = "left_top_lon")
    private BigDecimal leftTopLon;
    @Column(name = "right_bot_lat")
    private BigDecimal rightBotLat;
    @Column(name = "right_bot_lon")
    private BigDecimal rightBotLon;
    @ManyToMany
    @JoinTable(
            name = "quad_tree_node_inheritance",
            joinColumns = @JoinColumn(name = "id_parent"),
            inverseJoinColumns = @JoinColumn(name = "id_child")
    )
    private Collection<QuadTreeNode> childs = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "quad_tree_node_adjacency",
            joinColumns = @JoinColumn(name = "id_1"),
            inverseJoinColumns = @JoinColumn(name = "id_2")
    )
    private Collection<QuadTreeNode> adjs = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "id")
    private QuadTreeNodeLoad load;
    
}
