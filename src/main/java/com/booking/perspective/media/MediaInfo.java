package com.booking.perspective.media;

import java.math.BigDecimal;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@NoArgsConstructor
@Setter
@DynamoDbBean
public class MediaInfo {
    
    private String id;
    private BigDecimal lat;
    private BigDecimal lng;
    private String geoLeafId;
    
    public MediaInfo(String id, BigDecimal lat, BigDecimal lng, String geoLeafId) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.geoLeafId = geoLeafId;
    }
    
    @DynamoDbSortKey
    public String getId() {
        return id;
    }
    
    @DynamoDbAttribute(value = "lat")
    public BigDecimal getLat() {
        return lat;
    }
    
    @DynamoDbAttribute(value = "lnd")
    public BigDecimal getLng() {
        return lng;
    }
    
    @DynamoDbPartitionKey
    public String getGeoLeafId() {
        return geoLeafId;
    }
    
}
