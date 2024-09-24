package com.example.demo.controller;

import com.example.demo.service.PoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PoiController {

    private final PoiService poiService;



}
