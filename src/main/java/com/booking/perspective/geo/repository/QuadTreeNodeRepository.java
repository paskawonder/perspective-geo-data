package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.QuadTreeNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadTreeNodeRepository extends CrudRepository<QuadTreeNode, Long> {
}
