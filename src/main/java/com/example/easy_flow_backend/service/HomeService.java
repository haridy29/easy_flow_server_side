package com.example.easy_flow_backend.service;

import com.example.easy_flow_backend.view.RegisterModel;
import org.springframework.http.ResponseEntity;

public interface HomeService {

    ResponseEntity<String> Register(RegisterModel registerModel);

}
