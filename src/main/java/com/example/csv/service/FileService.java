package com.example.csv.service;

import com.example.csv.config.File;
import com.example.csv.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final FileRepository fileRepository;

    public Long saveFile(MultipartFile files) throws IOException{
        if(files.isEmpty()){
            return null;
        }

        String origName = files.getOriginalFilename();

        String uuid = UUID.randomUUID().toString();

        String extension = origName.substring(origName.lastIndexOf("."));

        String savedName = uuid + extension;

        String savedPath = fileDir + savedName;

        File file = File.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();

        files.transferTo(new File(savedPath));

        File savedFile = fileRepository.save(file);

        return savedFile.getId();
    }
}
