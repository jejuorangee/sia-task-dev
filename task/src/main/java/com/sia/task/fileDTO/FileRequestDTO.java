package com.sia.task.fileDTO;

import java.nio.file.Path;

import com.sia.task.fileEntity.FileEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileRequestDTO {
  private String originalFileName;
  private String convertedFileName;
  private Path filePath;
  private Path convertedFilePath;

  private int width;
  private int height;
  private int band_count;

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
