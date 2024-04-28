package io.github.bootystar.helper.minio;

import io.github.bootystar.helper.minio.client.MinioEnhancedClient;
import io.minio.MinioClient;

public abstract class MinioHelper {

    public static MinioEnhancedClient createEnhancedClient(String endpoint, String accessKey, String secretKey, String bucketName){
        MinioClient build = MinioClient
                .builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        return new MinioEnhancedClient(build, bucketName);
    }




}
