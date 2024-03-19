package com.booking.perspective.service;

import com.booking.perspective.geo.QuadTreeHelper;
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
public class LocationService {
    
    private final long quadTreeRootId;
    private final QuadTreeNodeRepository quadTreeNodeRepository;
    private final QuadTreeHelper quadTreeHelper;
    
    @Autowired
    public LocationService(@Value("${geo.quad-tree.root.id}") Long quadTreeRootId, QuadTreeNodeRepository quadTreeNodeRepository, QuadTreeHelper quadTreeHelper) {
        this.quadTreeRootId = quadTreeRootId;
        this.quadTreeNodeRepository = quadTreeNodeRepository;
        this.quadTreeHelper = quadTreeHelper;
    }

    public String save(BigDecimal lat, BigDecimal lon) {
        QuadTreeNode node = resolve(quadTreeNodeRepository.findById(quadTreeRootId).orElseThrow(), lat, lon);
        return String.valueOf(node.getId());
    }
    
    private QuadTreeNode resolve(QuadTreeNode root, BigDecimal lat, BigDecimal lon) {
        QuadTreeNode node = root;
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat().compareTo(lat) > -1 && e.getLeftTopLon().compareTo(lon) > -1 && e.getRightBotLat().compareTo(lat) < 1 && e.getRightBotLat().compareTo(lon) < 1)
                    .findAny().orElseThrow();
        }
        return node;
    }
    
    @Transactional
    public void defaultSplit() {
        List<QuadTreeNode> nodes = List.of(quadTreeNodeRepository.findById(quadTreeRootId).orElseThrow());
        List<QuadTreeNode> impacted = new ArrayList<>();
        while (!nodes.isEmpty()) {
            for (QuadTreeNode node: nodes) {
                if (node.getChilds().isEmpty()) {
                    impacted.addAll(quadTreeHelper.split(node));
                    for (QuadTreeNode child: node.getChilds()) {
                        child.getLoad().setExpectedFactor(node.getLoad().getExpectedFactor() - 1);
                    }
                }
            }
            nodes = nodes.stream().flatMap(e -> e.getChilds().stream()).filter(e -> e.getLoad().getExpectedFactor() > 0).toList();
        }
        quadTreeNodeRepository.saveAll(impacted);
    }
    

}
