package com.booking.perspective.geo;

import com.booking.perspective.geo.entity.GeoTreeNode;
import com.booking.perspective.geo.repository.GeoTreeNodeRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoService {
    
    private final String geoTreeRootId;
    private final GeoTreeNodeRepository geoTreeNodeRepository;
    private final GeoTreeHelper geoTreeHelper;
    
    @Autowired
    public GeoService(@Value("${geo.geo-tree.root.id}") String geoTreeRootId, GeoTreeNodeRepository geoTreeNodeRepository, GeoTreeHelper geoTreeHelper) {
        this.geoTreeRootId = geoTreeRootId;
        this.geoTreeNodeRepository = geoTreeNodeRepository;
        this.geoTreeHelper = geoTreeHelper;
    }
    
    @Transactional
    public List<GeoTreeNode> getLeaves() {
        return geoTreeNodeRepository.findByChildsIsEmpty();
    }
    
    @Transactional
    public GeoTreeNode navigateToLeaf(Coordinates coordinates) {
        GeoTreeNode node = geoTreeNodeRepository.findById(geoTreeRootId).orElseThrow();
        while (!node.getChilds().isEmpty()) {
            node = node.getChilds().stream()
                    .filter(e -> e.getLeftTopLat().compareTo(coordinates.getLat()) > -1 && e.getLeftTopLng().compareTo(coordinates.getLng()) < 1 && e.getRightBotLat().compareTo(coordinates.getLat()) < 1 && e.getRightBotLng().compareTo(coordinates.getLng()) > -1)
                    .findAny().orElseThrow();
        }
        return node;
    }
    
    @Transactional
    public List<GeoTreeNode> adjs(Coordinates coordinates) {
        return new ArrayList<>(navigateToLeaf(coordinates).getAdjs());
    }
    
    @Transactional
    public void split(Coordinates coordinates) {
        GeoTreeNode node = navigateToLeaf(coordinates);
        geoTreeHelper.split(node);
        geoTreeNodeRepository.save(node);
    }

}
