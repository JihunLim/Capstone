package com.capstone.precare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * Swagger 설정 (자동 API 문서 생성 라이브러리)
 *
 * created 18.08.28  by 임지훈
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jihunim.kakaoix.controller") )
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getArrayList());

    }

    private ArrayList<ResponseMessage> getArrayList(){
        ArrayList<ResponseMessage> lists = new ArrayList<ResponseMessage>();

        lists.add(new ResponseMessageBuilder().code(500).message("서버에러").responseModel(new ModelRef("Error")).build());
        lists.add(new ResponseMessageBuilder().code(403).message("권한없음").responseModel(new ModelRef("Forbbiden")).build());

        return lists;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("KakaoIX 백엔드 개발자 과제 _ 임지훈(940204)")
                .contact("임지훈, 010-6298-5198, limjihun204@naver.com")
                .build();
    }


}
