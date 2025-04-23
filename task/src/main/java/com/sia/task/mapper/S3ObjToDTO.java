package com.sia.task.mapper;

import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.sia.task.fileDTO.FileDTO;

@Component
public class S3ObjToDTO {
  public FileDTO fileDTO(S3ObjectSummary summary){
    FileDTO fileDTO = new FileDTO();

    fileDTO.setBucketName(summary.getBucketName());
    fileDTO.setName(summary.getKey());
    fileDTO.setETag(summary.getETag());
    fileDTO.setSize(summary.getSize());
    fileDTO.setLastModify(summary.getLastModified());
    fileDTO.setStorageClass(summary.getStorageClass());

    return fileDTO;
  }
}
