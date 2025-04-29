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

@Service
public class S3UploadService {
  private final AmazonS3Client s3Client;

  public S3UploadService(AmazonS3Client s3Client){
    this.s3Client = s3Client;
  }
  
  @Value("${UPLOAD_BUCKET_NAME}")
  private String bucket;
  @Value("${FOLDER_PATH}")
  private String folder_path;

  public List<FileRequestDTO> s3upload(List<FileRequestDTO> requestList){
    InputStream in = null;
    long size = 0;
    String contentType = "";
    for (FileRequestDTO requestDTO : requestList) {
      try{
        in = Files.newInputStream(requestDTO.getConvertedFilePath());
        size= Files.size(requestDTO.getConvertedFilePath());
  
        contentType = Files.probeContentType(requestDTO.getFilePath());
  
      } catch(IOException e){
        System.out.println(e);
        return new ArrayList<>();
      }
      ObjectMetadata metadata = new ObjectMetadata();
      metadata.setContentLength(size);
      metadata.setContentType(contentType);
  
      String key = folder_path + requestDTO.getConvertedFileName();

      // 확인 로그
      System.out.println(bucket);
      System.out.println(key);
      System.out.println(in);
      System.out.println(metadata.getContentLength());
      System.out.println(metadata.getContentType());

      PutObjectRequest putRequest = new PutObjectRequest(bucket, key, in, metadata);
      s3Client.putObject(putRequest);
    }
    return requestList;
  }
}
