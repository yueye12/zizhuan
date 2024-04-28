package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Questions {
    private Integer id;
    private String issue;
    private Integer module_id;
    private Integer grade;
    private int state;
}
