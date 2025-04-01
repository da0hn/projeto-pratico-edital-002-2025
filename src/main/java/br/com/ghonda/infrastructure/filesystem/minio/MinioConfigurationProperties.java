package br.com.ghonda.infrastructure.filesystem.minio;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "minio")
public record MinioConfigurationProperties(
    String url,
    String accessKey,
    String secretKey,
    String bucketName
) {
}
