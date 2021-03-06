package com.example.csv.common;

import com.example.csv.config.File;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper { // Apache commons csv 를 사용하여 파일 읽고 쓰기 위한 클래스

    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) { // 파일이 CSV 형식인지 확인하는 함수
        if (!TYPE.equals(file.getContentType())) {
            return false;
        }
        return true;
    }

    public static List<File> csvFiles(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); // 글씨 깨짐 방지
             CSVParser csvParser = new CSVParser(fileReader, // CSVParser : 구문 분석
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<File> fileUploads = new ArrayList<File>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) { // csvRecords 에서 객체를 하나씩 꺼내서 csvRecord 에 값을 넣어준다
                File fileUpload = new File(
                        Integer.parseInt(csvRecord.get("Id")),
                        csvRecord.get("City"),
                        csvRecord.get("Province")
                );
                fileUploads.add(fileUpload);
            }

            return fileUploads;

        } catch (IOException e) { // 예외처리 : CSV 분석 실패
            throw new RuntimeException(e.getMessage());
        }
    }
}