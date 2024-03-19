package com.booking.perspective.geo.repository;

import com.booking.perspective.geo.entity.QuadTreeNodeInheritance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuadTreeNodeInheritanceRepository extends CrudRepository<QuadTreeNodeInheritance, Long> {
}
