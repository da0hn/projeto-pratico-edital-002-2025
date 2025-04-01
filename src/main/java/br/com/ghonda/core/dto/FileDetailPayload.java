package br.com.ghonda.core.dto;

import lombok.Builder;

@Builder
public record FileDetailPayload(
    String hash,
    String objectName,
    String contentType,
    String bucketName
) {
}
