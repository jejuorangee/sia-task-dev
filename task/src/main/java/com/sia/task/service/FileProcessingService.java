package com.sia.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

//import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileProcessingService {
  private final S3DownloadService downloadService;
  private final ConvertCOGService convertService;
  private final COGMetadataExtractorService extractorService;
  private final FileService fileService;
  private final S3UploadService uploadService;
  
  public void fileProcessing(List<FileRequestDTO> fileList){
    // 파일 다운로드
    List<FileRequestDTO> downloadList = downloadService.getObject(fileList);
    
    // 파일 COG 변환
    List<FileRequestDTO> convertedList = convertService.fileConvertCOG(downloadList);

    // COG 메타데이터 추출
    List<FileRequestDTO> metadatas = extractorService.extractData(convertedList);
    // 변환 파일 DB 저장
    if(metadatas.size() > 1){
      fileService.insertAll(fileList);
    }else {
      fileService.insert(metadatas.get(0));
    }

    // 파일 업로드
    uploadService.s3upload(metadatas);
  }
}