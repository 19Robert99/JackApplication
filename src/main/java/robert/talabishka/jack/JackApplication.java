package robert.talabishka.jack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

import java.nio.charset.Charset;

@SpringBootApplication
public class JackApplication {

    public static void main(String[] args) {
        SpringApplication.run(JackApplication.class, args);
    }

    @Bean
    public HttpMessageConverter<String> createStringHttpMessageConverter() {
        return new StringHttpMessageConverter
                (Charset.forName("UTF-8"));
    }

}
