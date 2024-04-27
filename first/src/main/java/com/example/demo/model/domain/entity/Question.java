package com.example.demo.model.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Question {
    private Long id;
    private String issue;
    private Long grade;
}
