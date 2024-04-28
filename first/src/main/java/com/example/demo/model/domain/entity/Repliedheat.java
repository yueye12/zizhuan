package com.example.demo.model.domain.entity;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Repliedheat {
    private Integer id;
    private String  replied;
    private Integer questionId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
