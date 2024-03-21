package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.GeoTreeNodeInheritance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoTreeNodeInheritanceRepository extends CrudRepository<GeoTreeNodeInheritance, Long> {
}
