package com.doconsult.poiapp.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name; // 프로젝트 이름

    private String description; // 프로젝트 내용

    private String projectManager; // PM

    private String company; // 고객사

    private String customer; // 고객 담당자

    private String address; // 고객사 주소

    private Date startDate; // 프로젝트 시작일

    private Date endDate; // 종료일

    private Date finishDate; // 프로젝트 종료일

}
