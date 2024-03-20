package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.QuadTreeNode;
import com.booking.perspective.geo.repository.QuadTreeNodeRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoService {
    
    private final long quadTreeRootId;
    private final QuadTreeNodeRepository quadTreeNodeRepository;
    private final QuadTreeHelper quadTreeHelper;
    
    @Autowired
    public GeoService(@Value("${geo.quad-tree.root.id}") Long quadTreeRootId, QuadTreeNodeRepository quadTreeNodeRepository, QuadTreeHelper quadTreeHelper) {
        this.quadTreeRootId = quadTreeRootId;
        this.quadTreeNodeRepository = quadTreeNodeRepository;
        this.quadTreeHelper = quadTreeHelper;
    }
    
    public List<QuadTreeNode> get() {
        return quadTreeNodeRepository.findAll();
    }
    
    public QuadTreeNode resolve(BigDecimal lat, BigDecimal lon) {
        QuadTreeNode node = quadTreeNodeRepository.findById(quadTreeRootId).orElseThrow();
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat().compareTo(lat) > -1 && e.getLeftTopLon().compareTo(lon) > -1 && e.getRightBotLat().compareTo(lat) < 1 && e.getRightBotLat().compareTo(lon) < 1)
                    .findAny().orElseThrow();
        }
        return node;
    }
    
    @Transactional
    public void defaultSplit() {
        QuadTreeNode root = quadTreeNodeRepository.findById(quadTreeRootId).orElseThrow();
        List<QuadTreeNode> nodes = List.of(root);
        while (!nodes.isEmpty()) {
            List<QuadTreeNode> next = new ArrayList<>();
            for (QuadTreeNode node: nodes) {
                if (node.getChilds().isEmpty()) {
                    quadTreeHelper.split(node);
                    for (QuadTreeNode child: node.getChilds()) {
                        child.setLoadFactor(node.getLoadFactor() - 1);
                    }
                } else if (node.getLoadFactor() > 0) {
                    next.addAll(node.getChilds());
                }
            }
            nodes = next;
        }
        quadTreeNodeRepository.save(root);
    }
    

}
