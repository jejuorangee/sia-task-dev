package com.sia.task.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sia.task.fileDTO.FileRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3UploadService {
  private final AmazonS3Client s3Client;

  // public S3UploadService(AmazonS3Client s3Client){
  //   this.s3Client = s3Client;
  // }
  
  @Value("${UPLOAD_BUCKET_NAME}")
  private String bucket;
  @Value("${FOLDER_PATH}")
  private String folder_path;

  public List<FileRequestDTO> s3upload(List<FileRequestDTO> requestList){
    InputStream in = null; // 파일을 읽기 위한 스트림
    long size = 0; // 파일 크기 (바이트)
    String contentType = ""; // 파일 타입 ex) image/tiff 등
    for (FileRequestDTO requestDTO : requestList) {
      try{
        // 업로드할 파일의 InputStream 및 메타데이터
        in = Files.newInputStream(requestDTO.getConvertedFilePath());
        size= Files.size(requestDTO.getConvertedFilePath());
  
        contentType = Files.probeContentType(requestDTO.getFilePath());
  
      } catch(IOException e){
        System.out.println(e);
        return new ArrayList<>();
      }
      // S3에 파일을 업로드할 때 함께 보낼 메타데이터
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(size);  // Content-Length 설정
      metadata.setContentType(contentType); // Content-Type 설정
      
      // S3에 저장될 파일의 key (폴더 경로 + 파일명)
      String key = folder_path + requestDTO.getConvertedFileName();

      // 확인 로그
      System.out.println(bucket);
      System.out.println(key);
      System.out.println(in);
      System.out.println(metadata.getContentLength());
      System.out.println(metadata.getContentType());

      // S3에 실제 업로드
      PutObjectRequest putRequest = new PutObjectRequest(bucket, key, in, metadata);
      s3Client.putObject(putRequest);
    }
    return requestList;
  }
}
