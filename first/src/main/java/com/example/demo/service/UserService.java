package com.example.demo.service;




import java.util.List;

public interface UserService {

    String login(String account, String password);

    Integer createheat(Integer id, String issue);

    void updateheat(Integer id, String issue);
}
