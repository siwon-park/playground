package com.doconsult.poiapp.controller;

import com.doconsult.poiapp.service.PoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PoiController {

    private final PoiService poiService;

    @GetMapping("/create/{pid}")
    public String createWord(@PathVariable long pid) throws Exception {
        poiService.create(pid);
        return "200";
    }

//    @GetMapping("/read")
//    public String readDocs() throws Exception{
//        poiService.read();
//        return "200";
//    }

//    @GetMapping("/readwrite")
//    public String writeDocs() throws Exception {
//        poiService.readAndWrite();
//        return "200";
//    }

}
