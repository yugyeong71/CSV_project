package com.example.csv.config;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "fileUpload") // DB 테이블명
@Getter @NoArgsConstructor
public class File {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    public File(Integer id, String city, String province) {
        this.id = id;
        this.city = city;
        this.province = province;
    }

}
