package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.QuadTreeNode;
import com.booking.perspective.geo.entity.QuadTreeNodeLoad;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class QuadTreeHelper {
    
    public Collection<QuadTreeNode> split(QuadTreeNode leaf) {
        if (!leaf.getChilds().isEmpty()) {
            throw new IllegalStateException(leaf.getId() + " is not a leaf");
        }
        BigDecimal[] mid = {
                leaf.getLeftTopLat().subtract(leaf.getRightBotLat()).divide(BigDecimal.TWO, RoundingMode.CEILING),
                leaf.getRightBotLon().subtract(leaf.getLeftTopLon()).divide(BigDecimal.TWO, RoundingMode.CEILING)
        };
        long id = leaf.getId();
        List<QuadTreeNode> impacted = new ArrayList<>();
        List<QuadTreeNode> childs = List.of(
                new QuadTreeNode(++id, leaf.getLeftTopLat(), leaf.getLeftTopLon(), mid[0], mid[1], new ArrayList<>(), new ArrayList<>(), new QuadTreeNodeLoad(id, 0)),
                new QuadTreeNode(++id, leaf.getLeftTopLat(), mid[1], mid[0], leaf.getRightBotLon(), new ArrayList<>(), new ArrayList<>(), new QuadTreeNodeLoad(id, 0)),
                new QuadTreeNode(++id, mid[0], leaf.getLeftTopLon(), leaf.getRightBotLat(), mid[1], new ArrayList<>(), new ArrayList<>(), new QuadTreeNodeLoad(id, 0)),
                new QuadTreeNode(++id, mid[0], mid[1], leaf.getRightBotLat(), leaf.getRightBotLon(), new ArrayList<>(), new ArrayList<>(), new QuadTreeNodeLoad(id, 0))
        );
        leaf.setChilds(childs);
        impacted.add(leaf);
        impacted.addAll(childs);
        for (QuadTreeNode child: childs) {
            for (QuadTreeNode adj: adjs(child, leaf.getAdjs())) {
                child.getAdjs().add(adj);
                adj.getAdjs().add(child);
                impacted.add(adj);
            }
            for (QuadTreeNode child2: adjs(child, leaf.getAdjs())) {
                child.getAdjs().add(child2);
            }
        }
        leaf.getAdjs().clear();
        return impacted;
    }
    
    private Collection<QuadTreeNode> adjs(QuadTreeNode node, Collection<QuadTreeNode> candidates) {
        List<QuadTreeNode> adjs = new ArrayList<>();
        BigDecimal[][] p1 = { {node.getLeftTopLat(), node.getLeftTopLon()}, {node.getRightBotLat(), node.getRightBotLon()} };
        for (QuadTreeNode adj: candidates) {
            BigDecimal[][] p2 = { {adj.getLeftTopLat(), adj.getLeftTopLon()}, {adj.getRightBotLat(), adj.getRightBotLon()} };
            if (node.getId() - adj.getId() != 0 && adjs(p1, p2)) {
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
