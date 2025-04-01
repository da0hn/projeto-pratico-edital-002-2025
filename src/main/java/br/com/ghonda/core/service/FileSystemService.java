package br.com.ghonda.core.service;

import br.com.ghonda.core.dto.FileDetailPayload;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {

    FileDetailPayload uploadObject(MultipartFile file);

    String getObject(String objectName);

}
