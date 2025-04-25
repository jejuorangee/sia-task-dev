package com.sia.task;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sia.task.fileDTO.FileDTO;
import com.sia.task.service.S3DownloadService;

@SpringBootTest
public class DownloadServiceTest {
  @Autowired
  private S3DownloadService downloadService;
  
  @Test
  void downloadTest(){
    FileDTO file = new FileDTO();
    file.setName("1464.tif");
    List<FileDTO> fileList = new ArrayList<FileDTO>();
    fileList.add(file);

    downloadService.getObject(fileList);
  }

}
