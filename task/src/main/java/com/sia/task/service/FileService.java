package com.sia.task.service;

import java.util.List;

// import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;
import com.sia.task.fileDTO.FileResponseDTO;

public interface FileService {
  // 전체 조회
  List<FileResponseDTO> selectAll();
  // 삽입
  String insert(FileRequestDTO fileRequestDTO);

  List<FileResponseDTO> insertAll(List<FileRequestDTO> fileList);
}
