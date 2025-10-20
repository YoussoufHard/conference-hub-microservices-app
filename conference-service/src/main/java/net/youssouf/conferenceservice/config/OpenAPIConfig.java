package net.youssouf.conferenceservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Value("${openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL du serveur en environnement de développement");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("URL du serveur en environnement de production");

        Contact contact = new Contact();
        contact.setEmail("youssouf@example.com");
        contact.setName("Youssouf");
        contact.setUrl("https://www.conference-service.com");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("API du service de conférence")
                .version("1.0")
                .contact(contact)
                .description("Cette API expose les endpoints pour gérer les conférences et les avis.")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
