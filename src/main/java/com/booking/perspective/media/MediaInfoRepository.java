package com.booking.perspective.media;

import java.util.List;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaInfoRepository extends CrudRepository<MediaInfo, String> {
    
    List<MediaInfo> findByGeoLeafIdIn(Set<String> geoLeafId);
    
}
