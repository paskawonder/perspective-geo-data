package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.GeoTreeNodeAdjacency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoTreeNodeAdjacencyRepository extends CrudRepository<GeoTreeNodeAdjacency, Long> {
}
