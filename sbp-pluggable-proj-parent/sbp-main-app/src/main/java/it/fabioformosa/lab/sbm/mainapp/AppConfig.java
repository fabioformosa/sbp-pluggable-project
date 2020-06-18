package it.fabioformosa.lab.sbm.mainapp;

import org.laxture.sbp.SpringBootPluginManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public SpringBootPluginManager pluginManager() {
        return new CustomPluginManager();
    }

    //  @Autowired
    //  DataSourceProperties dataSourceProperties;

    //  @Bean
    //  @Primary
    //  DataSource dataSource() {
    //    return new DataSourceSpy(realDataSource());
    //  }
    //
    //  @Bean
    //  @ConfigurationProperties(prefix = DataSourceProperties.PREFIX)
    //  DataSource realDataSource() {
    //    DataSource dataSource = DataSourceBuilder
    //        .create(dataSourceProperties.getClassLoader())
    //        .url(dataSourceProperties.getUrl())
    //        .username(dataSourceProperties.getUsername())
    //        .password(dataSourceProperties.getPassword())
    //        .build();
    //    return dataSource;
    //  }
}