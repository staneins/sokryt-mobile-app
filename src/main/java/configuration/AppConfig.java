package configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import repository.PoemRepo;
import repository.dbConnection.DBConnection;
import service.PoemService;

@Configuration
public class AppConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public DBConnection getDataSource(){
        return new DBConnection();
    }

    @Bean
    public PoemRepo getPoemRepo(){
        return new PoemRepo(getDataSource());
    }

    @Bean
    public PoemService getActorService(){
        return new PoemService(getPoemRepo());
    }
}
