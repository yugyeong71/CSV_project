package com.example.csv.controller;

import com.example.csv.common.CSVHelper;
import com.example.csv.common.ResponseMessage;
import com.example.csv.config.File;
import com.example.csv.exporter.ExcelExporter;
import com.example.csv.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class FileController {

    @Autowired FileService fileService;

    @PostMapping("/upload") // CSV 파일 업로드 요청
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) { // 파일이 CSV 형식인 경우
            try {
                fileService.save(file);
                message = "파일 업로드 성공"; // 요청 성공 메시지
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            catch (Exception e) { // 예외처리 : 파일 업로드 실패
                message = "파일 업로드 실패";
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/export/excel") // Excel 파일로 다운로드
    public void exportToExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream"); // Excel 타입
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss"); // 다운로드 받은 날짜 및 시간
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename = newZones_" + currentDateTime + ".xlsx"; // 파일명 설정
        response.setHeader(headerKey, headerValue);

        List<File> fileUploads = fileService.getAllTutorials();

        ExcelExporter excelExporter = new ExcelExporter(fileUploads);

        excelExporter.export(response);
    }

    @GetMapping("/download")
    public String test() {
        return "/downloadFile";
    }

}
