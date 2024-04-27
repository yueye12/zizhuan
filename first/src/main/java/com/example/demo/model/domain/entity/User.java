package com.example.demo.model.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String account;
    private String password;

    /** 用户年龄 */
    private String age;

    /** 用户姓名 */
    private String name;

    /** 用户性别 */
    private String sex;

    /** 用户其他信息 */
    private String userCondition; // 将 condition 改为 user_condition

}
