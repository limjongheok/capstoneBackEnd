package capston.capston.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내 서버가 응답을 할시 json을 자바스크립트에서 처리할 수 있게 할지 결정
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost");
        config.addAllowedOriginPattern("*");
        config.addAllowedOrigin("http://59.26.59.60:5000");
        config.addAllowedOrigin("http://59.26.59.60:8080");
        config.addAllowedOrigin("http://127.0.0.1:5000");
        config.addAllowedHeader("*"); // 모든 header 응답 허용
        config.addAllowedHeader("Access-Control-Allow-origin");
        config.addAllowedMethod("*"); // 모든 post,get,put,delete, patch요청 허용
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**",config);
        return new CorsFilter(source);
    }
}
