package com.sia.task.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sia.task.fileDTO.FileDTO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3DownloadService {
  private final AmazonS3 amazonS3;

  @Value("${ORIGINAL_BUCKET_NAME}")
  private String bucket;
  @Value("${app.download.base-dir}")
  private String downloadBaseDir;

  // 단 건 다운로드
  // public FileDTO getOneObject(FileDTO fileDTO) {
  //   Path base = Paths.get(downloadBaseDir);
  //   Path target;
  //   try{
  //     Files.createDirectories(base);
  //     System.out.println("다운로드 디렉토리 생성됨 : "+base);
  //     target = base.resolve(fileDTO.getName());
  //   } catch (IOException e){
  //     //throw new RuntimeException("다운로드 디렉토리 생성 실패 : "+ e );
  //     System.out.println("다운로드 디렉토리 생성 실패 : "+ e );
  //     return new FileDTO();
  //   }
  //     S3Object sobj = amazonS3.getObject(new GetObjectRequest(bucket, fileDTO.getName()));
  //     InputStream in = sobj.getObjectContent();

  //     long isDownload;
  //     try {
  //       isDownload = Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
  //     } catch (IOException e) {
  //       e.printStackTrace();
  //       System.out.println("파일 복사 실패 ! : "+ e);
  //       return new FileDTO();
  //     }

  //     // 파일이 생성됐는지 확인 로그
  //     System.out.println(isDownload);
  //     return fileDTO;
  //   }


  // 다 건 다운로드
  public List<FileDTO> getObject(List<FileDTO> fileDTO) {
    List<FileDTO> result = new ArrayList<FileDTO>();

    for (FileDTO file : fileDTO) {
      Path base = Paths.get(downloadBaseDir);
      Path target;
      try{
        Files.createDirectories(base);
        System.out.println("다운로드 디렉토리 생성됨 : "+base);
        target = base.resolve(file.getName());
      } catch (IOException e){
        //throw new RuntimeException("다운로드 디렉토리 생성 실패 : "+ e );
        System.out.println("다운로드 디렉토리 생성 실패 : "+ e );
        return result;
      }
      
      S3Object sobj = amazonS3.getObject(new GetObjectRequest(bucket, file.getName()));
      InputStream in = sobj.getObjectContent();
      
      long isDownload;
      try {
        isDownload = Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(file.getName()+" 다운로드 중");
        file.setFilePath(target);
        result.add(file);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("파일 복사 실패 ! : "+ e);
        return result;
      }
      
      // 파일이 생성됐는지 확인 로그
      System.out.println(file.getName() +" : "+ isDownload + file.getFilePath()+ "다운로드 완료");
    }
    // 다운로드 성공 시
    return result;
  }
}
