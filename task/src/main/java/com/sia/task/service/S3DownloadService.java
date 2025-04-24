package com.sia.task.service;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

  public boolean getOneObject(FileDTO fileDTO) {
    Path base = Paths.get(downloadBaseDir);
    Path target;
    try{
      Files.createDirectories(base);
      System.out.println("다운로드 디렉토리 생성됨 : "+base);
      target = base.resolve(fileDTO.getName());
    } catch (IOException e){
      //throw new RuntimeException("다운로드 디렉토리 생성 실패 : "+ e );
      System.out.println("다운로드 디렉토리 생성 실패 : "+ e );
      return false;
    }
      S3Object sobj = amazonS3.getObject(new GetObjectRequest(bucket, fileDTO.getName()));
      InputStream in = sobj.getObjectContent();

      long result;
      try {
        result = Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("파일 복사 실패 ! : "+ e);
        return false;
      }

      // 파일이 생성됐는지 확인하기 위함
      System.out.println(result);
      return true;
    }

  public boolean getObject(List<FileDTO> fileDTO) {
    //List<FileDTO> results = new ArrayList<FileDTO>();
    
    //HttpHeaders httpHeaders = new HttpHeaders();
   
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
        return false;
      }
      //FileDTO dto = new FileDTO();
      
        S3Object sobj = amazonS3.getObject(new GetObjectRequest(bucket, file.getName()));
        InputStream in = sobj.getObjectContent();
      
      long result;
      try {
        result = Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("파일 복사 실패 ! : "+ e);
        return false;
      }

      // 파일이 생성됐는지 확인하기 위함
      System.out.println(file.getName() +" : "+ result);
        // S3ObjectInputStream objectInputStream = ((S3Object)sobj).getObjectContent();

        // byte[] bytes = IOUtils.toByteArray(objectInputStream);

        // String fileName = URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20");
        // httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // httpHeaders.setContentLength(bytes.length);
        // httpHeaders.setContentDispositionFormData("attachment", fileName); 
        
       // dto.setData(bytes);
        //dto.setName(fileName);
         
      // } catch (Exception e) {
      //   throw new Exception("에러 발생 : " + e);
      // }
      
    //}
    // for (FileDTO datas : results) {
    //   System.out.println(datas.getName()+" : bytes : "+datas.getData());
    // }
    //System.out.println(httpHeaders);
    //return new ResponseEntity<>(results, httpHeaders, HttpStatus.OK);
    }
    return true;
  }
}
