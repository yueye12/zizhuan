package com.example.demo.model;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class RepliedUser {
    private Integer id;
    private String replied;
    private Integer question_id;
    private Integer user_id;
    private Timestamp updated_at;


}
