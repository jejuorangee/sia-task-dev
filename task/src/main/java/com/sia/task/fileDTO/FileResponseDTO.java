package com.sia.task.fileDTO;

import com.sia.task.fileEntity.FileEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileResponseDTO {
  private String originalFileName; // 원본 파일 이름
  private String convertedFileName; // 변환 후 파일 이름
  //private String filePath;
  //private String convertedFilePath;

  private int width; // 이미지 가로 크기
  private int height; // 이미지 세로 크기
  private int band_count; // 밴드수 ex) RGB = 3

  // entity dto 변환
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
