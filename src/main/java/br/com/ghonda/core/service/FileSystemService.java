package br.com.ghonda.core.service;

import br.com.ghonda.core.dto.FileDetailPayload;
import br.com.ghonda.core.exceptions.FileSystemException;
import org.springframework.web.multipart.MultipartFile;

public interface FileSystemService {

    FileDetailPayload uploadObject(MultipartFile file) throws FileSystemException;

    String getObjectUrl(String objectName) throws FileSystemException;

}
