package com.sia.task.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sia.task.fileDTO.FileDTO;
import com.sia.task.service.S3DownloadService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping(value = "/file", method=RequestMethod.POST)
@RequiredArgsConstructor
public class SaveFileController {

  private final S3DownloadService s3Service;

  // 변환 및 업로드 요청이 들어오면
  @PostMapping("/download")
  public String downloadFiles(FileDTO fileDTO){
    System.out.println("SaveFileContriller.java /file/download 호출됨");
    // 한번에 넘어온 데이터 자르기
    String fileNames[] = fileDTO.getName().split(",");
    
    // 데이터가 2개 이상이면
    if(fileNames.length > 1){
      // DTO 리스트 생성
      List<FileDTO> fileDTOList = new ArrayList<FileDTO>();
      for (int i=0; i<fileNames.length; i++) {
        FileDTO fileDTO2 = new FileDTO();
        fileDTO2.setName(fileNames[i]);
        // 자른 데이터 리스트에 담기
        fileDTOList.add(fileDTO2);
      }
      // 리스트 확인
      for (FileDTO file : fileDTOList) {
        System.out.println(file.getName());
      }
      // 다건 변환 및 업로드 서비스 호출
      try {
        s3Service.getObject(fileDTOList);
      } catch (Exception e) {
        System.out.println("error message : "+e);
        e.printStackTrace();
      }
      
    }
    else { // 한건 변환 및 업로드 호출
      System.out.println(fileDTO.getName());
      try {
        s3Service.getOneObject(fileDTO);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    
    return "uploadedFiles";
  }
}
