package control.self.igor.dailywisdom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

    // @Bean
    // public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
    // return args -> {
    // System.out.println("Let's inspect the beans provided by Spring Boot:");
    // String[] beanNames = ctx.getBeanDefinitionNames();
    // Arrays.sort(beanNames);
    // for (String beanName : beanNames) {
    // System.out.println(beanName);
    // }
    // };
    // }
}
