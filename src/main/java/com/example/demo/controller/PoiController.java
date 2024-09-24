package com.example.demo.controller;

import com.example.demo.service.PoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PoiController {

    private final PoiService poiService;

    @GetMapping("/create")
    public String createWord() {
        poiService.create();
        return "200";
    }

}
