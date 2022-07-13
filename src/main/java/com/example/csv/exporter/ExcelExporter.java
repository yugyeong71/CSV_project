package com.example.csv.exporter;

import com.example.csv.config.File;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExporter { // Apache Poi 를 사용하여 Excel 파일로 다운로드

    private XSSFWorkbook workbook; // Excel 2007 버전 이후인 경우 XSSF, workbook == 하나의 액셀 파일

    private XSSFSheet sheet; // Excel 파일의 sheet

    private List<File> fileUploads;

    public ExcelExporter(List<File> fileUploads){
        this.fileUploads = fileUploads;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){
        sheet = workbook.createSheet("Files"); // sheet 생성

        Row row = sheet.createRow(0); // sheet 내의 Row 생성, 0부터 시정

        // 폰트 스타일
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(false);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id", style);
        createCell(row, 1, "City", style);
        createCell(row, 2, "Province", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style){ // 하나의 row에 여러 개의 cell을 생성
        sheet.autoSizeColumn(columnCount); // 컬럼의 크기를 자동으로 설정
        Cell cell = row.createCell(columnCount);

        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        } else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines(){
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (File file : fileUploads){ // cell 추가
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, file.getId(), style);
            createCell(row, columnCount++, file.getCity(), style);
            createCell(row, columnCount++, file.getProvince(), style);
        }
    }

    public void export (HttpServletResponse response) throws IOException{
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
