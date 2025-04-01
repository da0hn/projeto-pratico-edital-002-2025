package br.com.ghonda.infrastructure.filesystem.minio;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    @Bean
    public MinioClient minioClient(final MinioConfigurationProperties properties) {
        return MinioClient.builder()
            .endpoint(properties.url())
            .credentials(properties.accessKey(), properties.secretKey())
            .build();
    }

}
