package b2w.spring.config;

import b2w.controller.ApplicationManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@Configuration
@ComponentScan(basePackages = {"b2w.controller, b2w.model, b2w.spring.config, b2w.view"})
@PropertySource(value = {"file:/home/arthur/projects/rskp-b2w/src/main/resources/spring.properties"})
public class SpringConfig {

    /**
     *
     */
    @Value("${sqlite.url}")
    private String sqliteUrl;

    /**
     *
     * @return
     */
    @Bean("sqliteUrl")
    public String getSqliteUrl(){
        return sqliteUrl;
    }

    @Bean("appManager")
    public ApplicationManager getAppManager(){
        return new ApplicationManager();
    }
}