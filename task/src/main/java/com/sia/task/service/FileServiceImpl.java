package com.sia.task.service;

import java.util.List;
import java.util.stream.Collectors;

//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.sia.task.fileDTO.FileDTO;
import com.sia.task.fileDTO.FileRequestDTO;
import com.sia.task.fileDTO.FileResponseDTO;
import com.sia.task.fileEntity.FileEntity;
import com.sia.task.repositoty.FileRepositoty;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{
  
  private final FileRepositoty fileRepositoty;

  // @Override
  // @Transactional
  // public List<FileDTO> selectAll(FileRequestDTO requestDTO) {
  //   FileEntity entity = requestDTO.toEntity();
  //   return fileRepositoty.findAll(entity);
  // }

  // @Override
  // @Transactional
  // public boolean insert(FileDTO fileDTO) {
  //   throw new UnsupportedOperationException("Unimplemented method 'insert'");
  // }

  @Override
  public List<FileResponseDTO> selectAll() {
    List<FileEntity> entities = fileRepositoty.findAll();
    return entities.stream()
      .map(FileResponseDTO::fromEntity)
      .collect(Collectors.toList());
  }
  
  @Override
  @Transactional(readOnly = true)
  public String insert(FileRequestDTO fileRequestDTO) {
    FileEntity entity = fileRequestDTO.toEntity();
    return fileRepositoty.save(entity).getConvertedFileName();
  }

  @Override
  public List<FileResponseDTO> insertAll(List<FileRequestDTO> fileList) {
    List<FileEntity> entities = fileList.stream()
      .map(FileRequestDTO::toEntity)
      .collect(Collectors.toList());

      List<FileEntity> saved = fileRepositoty.saveAll(entities);

      return saved.stream()
        .map(FileResponseDTO::fromEntity)
        .collect(Collectors.toList());
  }
  
}
