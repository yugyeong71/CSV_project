package com.example.csv.controller;

import com.example.csv.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;

    @GetMapping("/upload")
    public String UploadForm(){
        return "test/upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws IOException{
        fileService.saveFile(file);

        return "redirect:/";
    }


}
