package br.com.ghonda.infrastructure.filesystem.minio;

import br.com.ghonda.core.dto.FileDetailPayload;
import br.com.ghonda.core.service.FileSystemService;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Slf4j
@Service
@AllArgsConstructor
public class MinioFileSystemService implements FileSystemService {

    private static final int PART_SIZE_10_MB = 10 * 1024 * 1024;

    private static final int AUTO_SIZE = -1;

    private final MinioClient minioClient;

    private final MinioConfigurationProperties properties;

    @Override
    public FileDetailPayload uploadObject(final MultipartFile file) {
        try {

            final var hash = this.gerarHash(file.getBytes());

            if (!this.bucketAlreadyExists(this.properties.bucketName())) {
                log.warn("Bucket {} não existe. Criando...", this.properties.bucketName());
                this.minioClient.makeBucket(
                    MakeBucketArgs.builder()
                        .bucket(this.properties.bucketName())
                        .build()
                );
            }

            this.minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(this.properties.bucketName())
                    .object(hash)
                    .stream(file.getInputStream(), AUTO_SIZE, PART_SIZE_10_MB)
                    .contentType(file.getContentType())
                    .build()
            );

            log.info("Arquivo enviado com sucesso. Hash: {}", hash);

            return FileDetailPayload.builder()
                .bucketName(this.properties.bucketName())
                .objectName(hash)
                .contentType(file.getContentType())
                .hash(hash)
                .build();
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getObjectUrl(final String objectName) {
        try {
            return this.minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(this.properties.bucketName())
                    .object(objectName)
                    .method(Method.GET)
                    .expiry((int) this.properties.presignedUrlExpiration().toMillis())
                    .build()
            );
        }
        catch (final ServerException | InsufficientDataException | ErrorResponseException | IOException | NoSuchAlgorithmException |
                     InvalidKeyException | InvalidResponseException | XmlParserException | InternalException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean bucketAlreadyExists(final String bucket) throws Exception {
        return this.minioClient.bucketExists(
            BucketExistsArgs.builder()
                .bucket(bucket)
                .build()
        );
    }

    private String gerarHash(final byte[] data) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        final var hashBytes = digest.digest(data);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes);
    }

}
