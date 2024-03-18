package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.RectTreeNode;
import com.booking.perspective.geo.entity.RectTreeNodeLoad;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class RectTreeHelper {
    
    public Collection<RectTreeNode> split(RectTreeNode node) {
        if (!node.getChilds().isEmpty()) {
            throw new IllegalStateException(node.getId() + " is not a leaf");
        }
        double[] mid = {
                (node.getLeftTopLat() - node.getRightBotLat()) / 2d,
                (node.getRightBotLon() - node.getLeftTopLon()) / 2d
        };
        long id = node.getId();
        List<RectTreeNode> impacted = new ArrayList<>();
        List<RectTreeNode> childs = List.of(
                new RectTreeNode(++id, node.getLeftTopLat(), node.getLeftTopLon(), mid[0], mid[1], new ArrayList<>(), new ArrayList<>(), new RectTreeNodeLoad(id, 0)),
                new RectTreeNode(++id, node.getLeftTopLat(), mid[1], mid[0], node.getRightBotLon(), new ArrayList<>(), new ArrayList<>(), new RectTreeNodeLoad(id, 0)),
                new RectTreeNode(++id, mid[0], node.getLeftTopLon(), node.getRightBotLat(), mid[1], new ArrayList<>(), new ArrayList<>(), new RectTreeNodeLoad(id, 0)),
                new RectTreeNode(++id, mid[0], mid[1], node.getRightBotLat(), node.getRightBotLon(), new ArrayList<>(), new ArrayList<>(), new RectTreeNodeLoad(id, 0))
        );
        node.setChilds(childs);
        impacted.add(node);
        impacted.addAll(childs);
        for (RectTreeNode child: childs) {
            for (RectTreeNode adj: adjs(child, node.getAdjs())) {
                child.getAdjs().add(adj);
                adj.getAdjs().add(child);
                impacted.add(adj);
            }
            for (RectTreeNode child2: adjs(child, node.getAdjs())) {
                child.getAdjs().add(child2);
            }
        }
        node.getAdjs().clear();
        return impacted;
    }
    
    private Collection<RectTreeNode> adjs(RectTreeNode node, Collection<RectTreeNode> candidates) {
        List<RectTreeNode> adjs = new ArrayList<>();
        double[][] p1 = { {node.getLeftTopLat(), node.getLeftTopLon()}, {node.getRightBotLat(), node.getRightBotLon()} };
        for (RectTreeNode adj: candidates) {
            double[][] p2 = { {adj.getLeftTopLat(), adj.getLeftTopLon()}, {adj.getRightBotLat(), adj.getRightBotLon()} };
            if (node.getId() - adj.getId() != 0 && adjs(p1, p2)) {
                adjs.add(adj);
            }
        }
        return adjs;
    }
    
    private boolean adjs(double[][] p1, double[][] p2) {
        return isBelow(p1, p2) || isBelow(p2, p1) || isRight(p1, p2) || isRight(p2, p1);
    }
    
    private boolean isBelow(double[][] p1, double[][] p2) {
        return p1[1][0] == p2[0][0] && p1[0][1] <= p2[1][1] && p1[1][1] >= p2[0][1];
    }
    
    private boolean isRight(double[][] p1, double[][] p2) {
        return p1[1][1] == p2[0][1] && p1[0][0] >= p2[1][0] && p1[1][0] <= p2[0][0];
    }
    
}
