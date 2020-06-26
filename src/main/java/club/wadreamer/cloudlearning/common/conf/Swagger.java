package club.wadreamer.cloudlearning.common.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Swagger
 * @Description TODO
 * @Author bear
 * @Date 2020/2/26 22:02
 * @Version 1.0
 **/
@Configuration(value = "false")
@EnableSwagger2  //启动swagger注解 启动服务，浏览器输入"http://服务名:8080/swagger-ui.html"
public class Swagger {
    @Autowired
    private VideoConfig videoConfig;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.basePackage("club.wadreamer.cloudlearning.controller"))
                // 扫描指定包中的swagger注解
//                .apis(RequestHandlerSelectors.basePackage("club.wadreamer.cloudlearning.controller"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置标题
                .title("cloud videos API文档")
                //描述
                .description("更多请联系www.wadreamer.club")
                //作者信息
                //.contact(new Contact(v2Config.getName(), null, V2Config.getEmail_account()))
                //服务条款URL，页面上不可见
                .termsOfServiceUrl("www.wadreamer.club")
                //版本
                .version(videoConfig.getVersion())
                .build();
    }
}
