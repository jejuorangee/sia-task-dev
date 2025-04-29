package com.sia.task.fileDTO;

import com.sia.task.fileEntity.FileEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponseDTO {
  private String originalFileName;
  private String convertedFileName;
  //private String filePath;
  //private String convertedFilePath;

  private int width;
  private int height;
  private int band_count;

  public static FileResponseDTO fromEntity(FileEntity entity){
    return FileResponseDTO.builder()
      .originalFileName(entity.getOriginalFileName())
      .convertedFileName(entity.getConvertedFileName())
      //.filePath(entity.getFilePath())
      //.convertedFilePath(entity.getConvertedFilePath())
      .width(entity.getWidth())
      .height(entity.getHeight())
      .band_count(entity.getBand_count())
      .build();
  }
}
