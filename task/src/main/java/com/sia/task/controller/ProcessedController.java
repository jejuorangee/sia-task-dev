package com.sia.task.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.sia.task.fileDTO.FileResponseDTO;
import com.sia.task.service.FileService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/file")
public class ProcessedController {
  private final FileService fileService; 

  @GetMapping("/processed")
  public String processedView() {
    System.out.println("ProcessedController.java processedView() 호출");
    return "redirect:/processedFile.html";
  }
  

  @GetMapping("/processed/list")
  public ResponseEntity<List<FileResponseDTO>> processedList() {
    System.out.println("ProcessedController.java /processed/list 호출");
    List<FileResponseDTO> processedList = fileService.selectAll();
    
    return new ResponseEntity<>(processedList,HttpStatus.OK);
  } 
}
