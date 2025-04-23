package com.sia.task.fileDTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDTO {
  private String bucketName;
  private String name;
  private String eTag;
  private Long size;
  private Date lastModify;
  private String storageClass;
  private String ownerId;
  private String ownerName;
}
