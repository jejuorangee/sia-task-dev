package com.sia.task.fileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name="COGFILES")
public class FileEntity {
  @Id
  @Column(name = "converted_file_name", length = 255, nullable = false)
  private String convertedFileName;
  
  private String originalFileName;

  
  //private String filePath;
  //private String convertedFilePath;

  private int width;
  private int height;
  private int band_count;

  @Builder
  public FileEntity(String originalFileName, String convertedFileName,  
                    int width, int height, int band_count){ //String filePath, String convertedFilePath,
    this.originalFileName = originalFileName;
    this.convertedFileName = convertedFileName;
    //this.filePath = filePath;
    //this.convertedFilePath = convertedFilePath;
    this.width = width;
    this.height = height;
    this.band_count = band_count;
  }
}
