package com.sia.task.fileDTO;

import java.nio.file.Path;

import com.sia.task.fileEntity.FileEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileRequestDTO {
  private String originalFileName; //원본 파일 이름
  private String convertedFileName; // 변환된 파일 이름
  private Path filePath; // 원본 파일 경로
  private Path convertedFilePath; // 변환된 파일 경로

  private int width; // 이미지 가로 크기
  private int height; // 이미지 세로 크기
  private int band_count; // 밴드 수 ex) RGB = 3

  // dto entity 변환 메서드
  public FileEntity toEntity(){
    return FileEntity.builder()
      .originalFileName(this.originalFileName)
      .convertedFileName(this.convertedFileName)
      //.filePath(this.filePath.toString())
      //.convertedFilePath(this.convertedFilePath.toString())
      .width(this.width)
      .height(this.height)
      .band_count(this.band_count)
      .build();
  }
}
