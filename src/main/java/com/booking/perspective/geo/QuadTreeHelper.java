package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.QuadTreeNode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class QuadTreeHelper {
    
    public void split(QuadTreeNode leaf) {
        if (!leaf.getChilds().isEmpty()) {
            throw new IllegalStateException(leaf.getId() + " is not a leaf");
        }
        BigDecimal[] mid = {
                leaf.getLeftTopLat().add(leaf.getRightBotLat()).divide(BigDecimal.TWO, RoundingMode.CEILING),
                leaf.getRightBotLon().add(leaf.getLeftTopLon()).divide(BigDecimal.TWO, RoundingMode.CEILING)
        };
        Set<QuadTreeNode> childs = Set.of(
                new QuadTreeNode(leaf.getLeftTopLat(), leaf.getLeftTopLon(), mid[0], mid[1]),
                new QuadTreeNode(leaf.getLeftTopLat(), mid[1], mid[0], leaf.getRightBotLon()),
                new QuadTreeNode(mid[0], leaf.getLeftTopLon(), leaf.getRightBotLat(), mid[1]),
                new QuadTreeNode(mid[0], mid[1], leaf.getRightBotLat(), leaf.getRightBotLon())
        );
        leaf.setChilds(new HashSet<>(childs));
        for (QuadTreeNode adj: leaf.getAdjs()) {
            adj.getAdjs().remove(leaf);
        }
        for (QuadTreeNode child: childs) {
            for (QuadTreeNode adj: adjs(child, leaf.getAdjs())) {
                child.getAdjs().add(adj);
                adj.getAdjs().add(child);
            }
            for (QuadTreeNode child2: adjs(child, leaf.getAdjs())) {
                child.getAdjs().add(child2);
            }
        }
        leaf.getAdjs().clear();
    }
    
    private Collection<QuadTreeNode> adjs(QuadTreeNode node, Collection<QuadTreeNode> candidates) {
        List<QuadTreeNode> adjs = new ArrayList<>();
        BigDecimal[][] p1 = { {node.getLeftTopLat(), node.getLeftTopLon()}, {node.getRightBotLat(), node.getRightBotLon()} };
        for (QuadTreeNode adj: candidates) {
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
        return p1[1][1].compareTo(p2[0][1]) == 0 && p1[0][0].compareTo(p2[1][0]) > -1 && p1[1][0].compareTo(p2[0][0]) < 1;
    }
    
}
