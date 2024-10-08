package com.doconsult.poiapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.path}")
    private String filePath;

    /*
    * 파일 다운로드 시 링크로 접속하여 정적 리소스를 접근하기 위핸 리소스 핸들러
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        WebMvcConfigurer.super.addResourceHandlers(registry);
        // *: 한단계 아래의 경로에 대해서만 와일드카드 적용 / **: 재귀적으로 모든 경로에 대한 와일드 카드 적용
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + filePath);
    }


    /*
    * CORS 에러를 해결하기 위한 Cors 맵핑
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
