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

    // TO-DO: ResponseBody로 수정하여 결과에 따른 리턴 코드 분기화 필요
    // 200: 성공, 401: Unauthorized (인증 정보 없음), 403: Forbidden (권한 없음), 404: Not Found
    @GetMapping("/create/{pid}")
    public String createWord(@PathVariable long pid) throws Exception {
        poiService.create(pid);
        return "200";
    }

    @GetMapping("/download")
    public String downloadFile() {
        return "";
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
