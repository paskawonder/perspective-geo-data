package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.RectTreeNode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RectTreeNodeRepository extends CrudRepository<RectTreeNode, Long> {

}
