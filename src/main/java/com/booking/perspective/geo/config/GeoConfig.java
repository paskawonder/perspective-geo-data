package com.booking.perspective.geo.config;

import com.booking.perspective.geo.QuadTreeHelper;
import com.booking.perspective.geo.entity.QuadTreeNode;
import com.booking.perspective.geo.repository.QuadTreeNodeRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class GeoConfig {
    
    public QuadTreeNode QuadTreeNode(QuadTreeNodeRepository QuadTreeNodeRepository, @Value("${geo.rect-tree.root.id") Long QuadTreeRootId) {
        return QuadTreeNodeRepository.findById(QuadTreeRootId).orElseThrow();
    }
    
    @Transactional
    public void initQuadTree(QuadTreeNode root, QuadTreeHelper QuadTreeHelper, QuadTreeNodeRepository QuadTreeNodeRepository) {
        List<QuadTreeNode> nodes = List.of(root);
        List<QuadTreeNode> impacted = new ArrayList<>();
        while (!nodes.isEmpty()) {
            for (QuadTreeNode node: nodes) {
                if (node.getChilds().isEmpty()) {
                    impacted.addAll(QuadTreeHelper.split(node));
                }
                for (QuadTreeNode child: node.getChilds()) {
                    child.getLoad().setExpectedFactor(node.getLoad().getExpectedFactor() - 1);
                }
            }
            nodes = nodes.stream().filter(e -> e.getLoad().getExpectedFactor() > 0).toList();
        }
        QuadTreeNodeRepository.saveAll(impacted);
    }
    
}
