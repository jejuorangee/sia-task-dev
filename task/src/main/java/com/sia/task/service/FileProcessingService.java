package com.sia.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sia.task.fileDTO.FileDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileProcessingService {
  private final S3DownloadService downloadService;
  private final ConvertCOGService convertService;
  //private final FileService fileService;
  //private final UploadService uploadService;
  
  public void fileProcessing(List<FileDTO> fileList){
    // 파일 다운로드
    List<FileDTO> downloadList = downloadService.getObject(fileList);
    
    // 파일 COG 변환
    convertService.fileConvertCOG(downloadList);

    // 변환 파일 DB 저장
    // fileService

    // 파일 업로드
    // uploadService
  }
}