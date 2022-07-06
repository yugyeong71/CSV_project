package com.example.csv;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CsvApplication {

    public static void main(String[] args) {
        SpringApplication.run(CsvApplication.class, args);
    }

}
