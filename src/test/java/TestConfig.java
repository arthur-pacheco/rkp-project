import b2w.controller.ApplicationManager;
import b2w.spring.config.SpringConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableCaching
@ComponentScan(basePackages = { "b2w.controller", "b2w.model", "b2w.spring.config", "b2w.view"},
                excludeFilters = @ComponentScan.Filter(classes = {
                SpringConfig.class }, type = FilterType.ASSIGNABLE_TYPE))
@PropertySource(value = {"file:/home/arthur/projects/rskp-b2w/src/main/resources/spring-test.properties"})
public class TestConfig {

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