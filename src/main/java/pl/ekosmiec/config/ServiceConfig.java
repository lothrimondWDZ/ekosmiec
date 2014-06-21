package pl.ekosmiec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ekosmiec.services.ContainersService;
import pl.ekosmiec.dao.*;

@Configuration
public class ServiceConfig {

	@Bean
	public ContainersService ContainersService(){
		return new ContainersService();
	}
	@Bean
	public ContainersTypesDao ContainersTypesDao(){
		return new ContainersTypesDaoImpl();
	}
}
