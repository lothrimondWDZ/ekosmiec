package pl.ekosmiec.config;

import nz.net.ultraq.web.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
 
@Configuration
@ComponentScan("pl.ekosmiec.controllers")
public class ThymeleafConfig {
 
	private static final String UTF_8 = "UTF-8";
	
	@Bean
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(1);
		return resolver;
	}
	
	@Bean
    SpringTemplateEngine templateEngine(final ServletContextTemplateResolver webTemplateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(webTemplateResolver);
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }

	@Bean
    ThymeleafViewResolver thymeleafViewResolver(final SpringTemplateEngine templateEngine) {
        final ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
        thymeleafViewResolver.setTemplateEngine(templateEngine);
        thymeleafViewResolver.setCharacterEncoding(UTF_8);
        return thymeleafViewResolver;
    }

}
