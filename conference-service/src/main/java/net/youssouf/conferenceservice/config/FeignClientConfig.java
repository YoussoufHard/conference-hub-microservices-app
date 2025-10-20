package net.youssouf.conferenceservice.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import feign.okhttp.OkHttpClient;
import net.youssouf.conferenceservice.exception.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }

    // Si vous avez besoin d'ajouter des en-têtes d'authentification ou d'autres en-têtes personnalisés
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Exemple d'ajout d'un en-tête d'authentification
            // String token = "YOUR_ACCESS_TOKEN";
            // requestTemplate.header("Authorization", "Bearer " + token);
            
            // Vous pouvez ajouter d'autres en-têtes personnalisés ici
            // requestTemplate.header("X-Custom-Header", "CustomValue");
        };
    }
}
