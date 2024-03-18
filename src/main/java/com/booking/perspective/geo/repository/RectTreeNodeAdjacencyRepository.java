package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.RectTreeNodeAdjacency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RectTreeNodeAdjacencyRepository extends CrudRepository<RectTreeNodeAdjacency, Long> {

}
