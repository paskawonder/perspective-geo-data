package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.geo.repository.GeoTreeNodeRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoService {
    
    private final long geoTreeRootId;
    private final GeoTreeNodeRepository geoTreeNodeRepository;
    private final GeoTreeHelper geoTreeHelper;
    
    @Autowired
    public GeoService(@Value("${geo.geo-tree.root.id}") Long geoTreeRootId, GeoTreeNodeRepository geoTreeNodeRepository, GeoTreeHelper geoTreeHelper) {
        this.geoTreeRootId = geoTreeRootId;
        this.geoTreeNodeRepository = geoTreeNodeRepository;
        this.geoTreeHelper = geoTreeHelper;
    }
    
    @Transactional
    public List<GeoTreeNode> getLeaves() {
        GeoTreeNode root = geoTreeNodeRepository.findById(geoTreeRootId).orElseThrow();
        List<GeoTreeNode> nodes = List.of(root);
        List<GeoTreeNode> leaves = new ArrayList<>();
        while (!nodes.isEmpty()) {
            List<GeoTreeNode> childs = new ArrayList<>();
            for (GeoTreeNode node: nodes) {
                if (node.getChilds().isEmpty()) {
                    leaves.add(node);
                } else {
                    childs.addAll(node.getChilds());
                }
            }
            nodes = childs;
        }
        return leaves;
    }
    
    @Transactional
    public GeoTreeNode resolve(BigDecimal lat, BigDecimal lon) {
        GeoTreeNode node = geoTreeNodeRepository.findById(geoTreeRootId).orElseThrow();
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat().compareTo(lat) > -1 && e.getLeftTopLon().compareTo(lon) < 1 && e.getRightBotLat().compareTo(lat) < 1 && e.getRightBotLon().compareTo(lon) > -1)
                    .findAny().orElseThrow();
        }
        return node;
    }
    
    @Transactional
    public List<GeoTreeNode> adjs(BigDecimal lat, BigDecimal lon) {
        return new ArrayList<>(resolve(lat, lon).getAdjs());
    }
    
    @Transactional
    public void split(BigDecimal lat, BigDecimal lon) {
        GeoTreeNode node = resolve(lat, lon);
        geoTreeHelper.split(node);
        geoTreeNodeRepository.save(node);
    }

}
