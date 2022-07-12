package com.example.csv.service;

import com.example.csv.common.CSVHelper;
import com.example.csv.config.File;
import com.example.csv.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    @Autowired FileRepository repository;

    @Transactional
    public void save(MultipartFile file) { // CSV 데이터를 DB에 저장
        try {
            List<File> fileUploads = CSVHelper.csvFiles(file.getInputStream());
            repository.saveAll(fileUploads);
        } catch (IOException e) { // 예외처리 : 데이터 저장에 실패했을 경우
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public List<File> getAllTutorials() { // DB에서 데이터를 읽고 반환
        return repository.findAll();
    }
}
