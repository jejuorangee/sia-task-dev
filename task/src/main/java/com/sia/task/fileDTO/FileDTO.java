package com.sia.task.fileDTO;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
// import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
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

  // S3 다운로드 데이터
  private byte[] data;
  private Map<String, String> httpheaders;

}
