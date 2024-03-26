package com.booking.perspective.geo.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id", "childs", "adjs"})
@Entity
public class GeoTreeNode {
    
    @Id
    @Column(name = "id", columnDefinition="CHAR(36)")
    private String id;
    @Column(name = "left_top_lat")
    private BigDecimal leftTopLat;
    @Column(name = "left_top_lng")
    private BigDecimal leftTopLng;
    @Column(name = "right_bot_lat")
    private BigDecimal rightBotLat;
    @Column(name = "right_bot_lng")
    private BigDecimal rightBotLng;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Set<GeoTreeNode> childs = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "geo_tree_node_adjacency",
            joinColumns = @JoinColumn(name = "id_1"),
            inverseJoinColumns = @JoinColumn(name = "id_2")
    )
    private Set<GeoTreeNode> adjs = new HashSet<>();
    
    public GeoTreeNode(BigDecimal leftTopLat, BigDecimal leftTopLng, BigDecimal rightBotLat, BigDecimal rightBotLng) {
        this.id = UUID.randomUUID().toString();
        this.leftTopLat = leftTopLat;
        this.leftTopLng = leftTopLng;
        this.rightBotLat = rightBotLat;
        this.rightBotLng = rightBotLng;
        this.childs = new HashSet<>();
        this.adjs = new HashSet<>();
    }
    
}
