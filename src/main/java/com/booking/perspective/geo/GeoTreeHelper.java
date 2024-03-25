package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.GeoTreeNode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class GeoTreeHelper {
    
    public void split(GeoTreeNode leaf) {
        if (!leaf.getChilds().isEmpty()) {
            throw new IllegalStateException(leaf.getId() + " is not a leaf");
        }
        BigDecimal[] mid = {
                leaf.getLeftTopLat().add(leaf.getRightBotLat()).divide(BigDecimal.TWO, RoundingMode.CEILING),
                leaf.getRightBotLon().add(leaf.getLeftTopLon()).divide(BigDecimal.TWO, RoundingMode.CEILING)
        };
        Set<GeoTreeNode> childs = Set.of(
                new GeoTreeNode(leaf.getLeftTopLat(), leaf.getLeftTopLon(), mid[0], mid[1]),
                new GeoTreeNode(leaf.getLeftTopLat(), mid[1], mid[0], leaf.getRightBotLon()),
                new GeoTreeNode(mid[0], leaf.getLeftTopLon(), leaf.getRightBotLat(), mid[1]),
                new GeoTreeNode(mid[0], mid[1], leaf.getRightBotLat(), leaf.getRightBotLon())
        );
        leaf.setChilds(new HashSet<>(childs));
        for (GeoTreeNode adj: leaf.getAdjs()) {
            adj.getAdjs().remove(leaf);
        }
        for (GeoTreeNode child: childs) {
            for (GeoTreeNode adj: adjs(child, leaf.getAdjs())) {
                child.getAdjs().add(adj);
                adj.getAdjs().add(child);
            }
            for (GeoTreeNode child2: adjs(child, childs)) {
                child.getAdjs().add(child2);
            }
        }
        leaf.getAdjs().clear();
    }
    
    private Collection<GeoTreeNode> adjs(GeoTreeNode node, Collection<GeoTreeNode> candidates) {
        List<GeoTreeNode> adjs = new ArrayList<>();
        BigDecimal[][] p1 = { {node.getLeftTopLat(), node.getLeftTopLon()}, {node.getRightBotLat(), node.getRightBotLon()} };
        for (GeoTreeNode adj: candidates) {
            BigDecimal[][] p2 = { {adj.getLeftTopLat(), adj.getLeftTopLon()}, {adj.getRightBotLat(), adj.getRightBotLon()} };
            if (!node.equals(adj) && adjs(p1, p2)) {
                adjs.add(adj);
            }
        }
        return adjs;
    }
    
    private boolean adjs(BigDecimal[][] p1, BigDecimal[][] p2) {
        return isBelow(p1, p2) || isBelow(p2, p1) || isRight(p1, p2) || isRight(p2, p1);
    }
    
    private boolean isBelow(BigDecimal[][] p1, BigDecimal[][] p2) {
        return p1[1][0].compareTo(p2[0][0]) == 0 && p1[0][1].compareTo(p2[1][1]) < 1 && p1[1][1].compareTo(p2[0][1]) > -1;
    }
    
    private boolean isRight(BigDecimal[][] p1, BigDecimal[][] p2) {
        return touchLon(p1, p2) && p1[0][0].compareTo(p2[1][0]) > -1 && p1[1][0].compareTo(p2[0][0]) < 1;
    }
    
    private boolean touchLon(BigDecimal[][] p1, BigDecimal[][] p2) {
        return p1[1][1].compareTo(p2[0][1]) == 0 || (p1[0][1].compareTo(BigDecimal.valueOf(-180)) == 0 && p2[1][1].compareTo(BigDecimal.valueOf(180)) == 0);
    }
    
}
