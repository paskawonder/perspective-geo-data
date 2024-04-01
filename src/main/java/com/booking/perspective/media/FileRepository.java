package com.booking.perspective.media;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Repository
public class FileRepository {
    
    private final S3Client s3Client;
    private final String mediaBucket;
    
    public FileRepository(S3Client s3Client, @Value("${media.storage.s3-bucket}") String mediaBucket) {
        this.s3Client = s3Client;
        this.mediaBucket = mediaBucket;
    }
    
    public void save(String id, byte[] bytes) {
        PutObjectRequest request = PutObjectRequest.builder().bucket(mediaBucket).key(id).build();
        s3Client.putObject(request, RequestBody.fromBytes(bytes));
    }
    
    public Map<String, String> getUrls(Collection<String> ids) {
        return ids.stream().collect(Collectors.toMap(
            id -> id,
            id -> {
                try (S3Presigner presigner = S3Presigner.builder().s3Client(s3Client).build()) {
                    GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofHours(1))
                        .getObjectRequest(GetObjectRequest.builder().bucket(mediaBucket).key(id).build()).build();
                    return presigner.presignGetObject(request).url().toExternalForm();
                }
            })
        );
    }
    
}
