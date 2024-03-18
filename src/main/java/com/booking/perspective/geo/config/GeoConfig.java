package com.booking.perspective.geo.config;

import com.booking.perspective.geo.RectTreeHelper;
import com.booking.perspective.geo.entity.RectTreeNode;
import com.booking.perspective.geo.repository.RectTreeNodeRepository;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GeoConfig {
    
    public RectTreeNode rectTreeNode(RectTreeNodeRepository rectTreeNodeRepository, @Value("${geo.rect-tree.root.id") Long rectTreeRootId) {
        return rectTreeNodeRepository.findById(rectTreeRootId).orElseThrow();
    }
    
    public void initRectTree(RectTreeNode root, RectTreeHelper rectTreeHelper, RectTreeNodeRepository rectTreeNodeRepository) {
        RectTreeNode node = root;
        List<RectTreeNode> leafs = new ArrayList<>();
        Collection<RectTreeNode> impacted = new ArrayList<>();
        for (RectTreeNode leaf: leafs) {
            impacted.addAll(rectTreeHelper.split(leaf));
        }
        
    }
    
}
