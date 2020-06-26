package club.wadreamer.cloudlearning;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CloudlearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudlearningApplication.class, args);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(100));
        //该方法已降级
        //factory.setMaxRequestSize("30MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(1000));
        return factory.createMultipartConfig();
    }

    public class GwsTomcatConnectionCustomizer implements TomcatConnectorCustomizer {
        public GwsTomcatConnectionCustomizer() {
        }

        @Override
        public void customize(Connector connector) {
            connector.setAttribute("connectionTimeout", 30000000);
        }
    }

}
