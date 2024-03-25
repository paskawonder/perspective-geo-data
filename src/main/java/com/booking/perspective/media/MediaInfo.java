package com.booking.perspective.media;

import com.booking.perspective.geo.entity.GeoTreeNode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class MediaInfo {
    
    @Id
    @Column(name = "id", columnDefinition="CHAR(36)")
    private String id;
    @Column(name = "lat")
    private BigDecimal lat;
    @Column(name = "lon")
    private BigDecimal lon;
    @Column(name = "geo_leaf_id", columnDefinition="CHAR(36)")
    private String geoLeafId;
    @ManyToOne
    @JoinColumn(name="geo_leaf_id", insertable=false, updatable=false)
    private GeoTreeNode geoLeaf;
    
    public MediaInfo(String id, BigDecimal lat, BigDecimal lon, String geoLeafId) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.geoLeafId = geoLeafId;
    }
    
}
