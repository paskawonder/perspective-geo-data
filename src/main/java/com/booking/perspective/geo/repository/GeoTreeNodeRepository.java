package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.GeoTreeNode;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoTreeNodeRepository extends CrudRepository<GeoTreeNode, String> {
    
    List<GeoTreeNode> findByChildsIsEmpty();
    
}
