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

@Entity // JPA 엔티티 DB 테이블과 맵핑됨
@Getter
@NoArgsConstructor // 생성자
@Table(name="COGFILES")
public class FileEntity {
  @Id // 기본키키
  @Column(name = "converted_file_name", length = 255, nullable = false)
  private String convertedFileName; // 변환된 파일 이름
  
  private String originalFileName; // 파일 원본 이름

  
  //private String filePath;
  //private String convertedFilePath;

  private int width; // 이미지 가로 크기
  private int height; // 이미지 세로 크기
  private int band_count; // 밴드수 RGB = 3


  // 빌더 패턴 생성자
  // 가독성 업
  // 객체 생성 실수 방지
  // 선택적 필드 구성 유리
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
