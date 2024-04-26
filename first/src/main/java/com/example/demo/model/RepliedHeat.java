package com.example.demo.model;

import lombok.Data;

@Data
public class RepliedHeat {
    private Integer id;
    private String replied;
    private Integer question_id;
    private Integer user_id;
}
