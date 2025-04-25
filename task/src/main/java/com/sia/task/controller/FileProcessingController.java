package com.sia.task.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sia.task.fileDTO.FileDTO;
import com.sia.task.service.FileProcessingService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/file", method=RequestMethod.POST)
public class FileProcessingController {
  private final FileProcessingService processingService;

  @PostMapping("/process/one")
  public ResponseEntity<String> fileProcessing(@RequestBody FileDTO fileDTO) {
    System.out.println("FileProcessingController.java /process/one 호출됨");

    // 확인용 로그
    System.out.println(fileDTO.getName());

    List<FileDTO> fileList = new ArrayList<FileDTO>();
    fileList.add(fileDTO);
      // 파일 처리 서비스 호출
    processingService.fileProcessing(fileList);
      return new ResponseEntity<>("processedFile", HttpStatus.OK);
  }
  
  
  @PostMapping("/process/multiple")
  public ResponseEntity<String> fileProcessing(@RequestBody List<FileDTO> fileList) {
    System.out.println("FileProcessingController.java /process/multiple 호출됨");

    // 확인용 로그
    for (FileDTO file : fileList) {
      System.out.println(file.getName());
    }
    
    // 파일 처리 서비스 호출
    processingService.fileProcessing(fileList);
    return new ResponseEntity<>("processedFile", HttpStatus.OK);
  }
}