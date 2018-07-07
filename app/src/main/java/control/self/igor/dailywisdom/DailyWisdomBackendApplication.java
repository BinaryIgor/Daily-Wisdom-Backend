package control.self.igor.dailywisdom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DailyWisdomBackendApplication {

    public static void main(String[] args) {
	SpringApplication.run(DailyWisdomBackendApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper() {
	ObjectMapper mapper = new ObjectMapper();
	mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
	return mapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Authorization",
				"Cache-Control", "Access-Control-Request-Method", "Access-Control-Request-Headers")
			.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
			.allowedMethods("GET", "POST", "PUT", "DELETE");
	    }
	};
    }
}
