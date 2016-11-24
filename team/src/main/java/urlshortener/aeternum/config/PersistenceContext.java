package urlshortener.aeternum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import urlshortener.common.repository.*;
import urlshortener.aeternum.web.SignIn;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ClickRepositoryImpl;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.repository.ShortURLRepositoryImpl;
import urlshortener.common.repository.UserRepository;
import urlshortener.common.repository.UserRepositoryImpl;

@Configuration
public class PersistenceContext {

	@Autowired
    protected JdbcTemplate jdbc;

	@Bean
	ShortURLRepository shortURLRepository() {
		return new ShortURLRepositoryImpl(jdbc);
	}
 	
	@Bean
	ClickRepository clickRepository() {
		return new ClickRepositoryImpl(jdbc);
	}

    @Bean
    CountryResRepository countryResRepository() { return new CountryResRepositoryImpl(jdbc);
    }

    @Bean
    UserRepository userRepository() {
        return new UserRepositoryImpl(jdbc);
    }

    @Bean
    SignIn signIn() { return new SignIn();}
}
