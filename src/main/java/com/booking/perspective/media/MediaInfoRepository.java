package com.booking.perspective.media;

import java.util.List;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

@Repository
public class MediaInfoRepository {
    
    private final DynamoDbTable<MediaInfo> table;
    
    public MediaInfoRepository(DynamoDbEnhancedClient client) {
        this.table = client.table("MediaInfo", TableSchema.fromBean(MediaInfo.class));
    }
    
    public void save(MediaInfo mediaInfo) {
        table.putItem(mediaInfo);
    }
    
    public List<MediaInfo> findByGeoLeafIdIn(String geoLeafId) {
        PageIterable<MediaInfo> scan = table.scan(ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("geoLeafId = :id").putExpressionValue(":id", AttributeValue.fromS(geoLeafId)).build()
                ).build()
        );
        return scan.items().stream().toList();
    }
    
}
