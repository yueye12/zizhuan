package com.example.demo.model.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {
    private Long id;
    private String issue;
    private Long grade;
    private Long status;
}
