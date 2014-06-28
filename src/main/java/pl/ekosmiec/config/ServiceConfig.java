package pl.ekosmiec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.ekosmiec.services.ContainersService;
import pl.ekosmiec.beans.DostepnyCzas;
import pl.ekosmiec.beans.Grupa;
import pl.ekosmiec.beans.Harmonogram;
import pl.ekosmiec.beans.Historia;
import pl.ekosmiec.beans.OdbiorSmieci;
import pl.ekosmiec.beans.PlanowanyOdbiorSmieci;
import pl.ekosmiec.beans.PrzewidywanaProdukcja;
import pl.ekosmiec.beans.RozpatrywanaGrupa;
import pl.ekosmiec.beans.WspolczynnikiAlgorytmu;
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
	@Bean
	public DAOGeneratora daoGeneratora(){
		return new DAOGeneratoraImpl();
	}
	//pozostałe beany:
	@Bean
	public DostepnyCzas dostepnyCzas(){
		return new DostepnyCzas();
	}
	@Bean
	public Grupa grupa(){
		return new Grupa();
	}
	@Bean
	public Harmonogram harmonogram(){
		return new Harmonogram();
	}
	@Bean
	public Historia historia(){
		return new Historia();
	}
	@Bean
	public OdbiorSmieci odbiorSmieci(){
		return new OdbiorSmieci();
	}
	@Bean
	public PlanowanyOdbiorSmieci planowanyOdbiorSmieci(){
		return new PlanowanyOdbiorSmieci();
	}
	@Bean
	public PrzewidywanaProdukcja przewidywanaProdukcja(){
		return new PrzewidywanaProdukcja();
	}
	@Bean
	public RozpatrywanaGrupa rozpatrywanaGrupa(){
		return new RozpatrywanaGrupa();
	}
	@Bean
	public WspolczynnikiAlgorytmu wspolczynnikiAlgorytmu(){
		return new WspolczynnikiAlgorytmu();
	}
}
