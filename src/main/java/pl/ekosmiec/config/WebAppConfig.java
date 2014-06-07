package pl.ekosmiec.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/lib/**").addResourceLocations("/lib/");
    }
}
