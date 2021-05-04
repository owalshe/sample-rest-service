package rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebApplication {

	private static final Logger LOG = LoggerFactory.getLogger(SpringBootWebApplication.class);
	
    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
    
//    @Bean
//    CommandLineRunner initDatabase(PersonRepository repository) {
//        return args -> {
//        	LOG.info("Preloading " + repository.save(new Person("data1", "data2")));
//        };
//    }
}
