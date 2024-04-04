package kr.KWGraduate.BookPharmacy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI openAPI(){

        Server server = new Server();
        server.setDescription("배포 서버 전용");
        server.setUrl("https://api.bookpharmacy.store");

        Server localhost = new Server();
        localhost.setDescription("백엔드 개발시 사용");
        localhost.setUrl("http://localhost:8080");

        OpenAPI openAPI =  new OpenAPI()
                            .info(new Info()
                                .title("책국 프로젝트 API")
                                .description("서버 api 제공")
                                .version("1.0.0"));

        openAPI.setServers(Arrays.asList(server, localhost));

        return openAPI;
    }
}