package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.QuadTreeNode;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadTreeNodeRepository extends CrudRepository<QuadTreeNode, Long> {
    
    @Override
    List<QuadTreeNode> findAll();
    
}
