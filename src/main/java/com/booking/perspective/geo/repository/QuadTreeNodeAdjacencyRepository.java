package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.QuadTreeNodeAdjacency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadTreeNodeAdjacencyRepository extends CrudRepository<QuadTreeNodeAdjacency, Long> {
}
