package it.fabioformosa.lab.sbm.mainapp;

import org.laxture.sbp.SpringBootPluginManager;
import org.laxture.sbp.spring.boot.PropertyPluginStatusProvider;
import org.laxture.sbp.spring.boot.SbpProperties;
import org.pf4j.PluginLoader;
import org.pf4j.PluginStatusProvider;
import org.pf4j.RuntimeMode;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

//@Configuration
public class AppConfig {
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

    @Bean
    public SpringBootPluginManager pluginManager(SbpProperties properties) {
        // Setup RuntimeMode
        System.setProperty("pf4j.mode", properties.getRuntimeMode().toString());

        // Setup Plugin folder
        String pluginsRoot = StringUtils.hasText(properties.getPluginsRoot()) ? properties.getPluginsRoot() : "plugins";
        System.setProperty("pf4j.pluginsDir", pluginsRoot);
        String appHome = System.getProperty("app.home");
        if (RuntimeMode.DEPLOYMENT == properties.getRuntimeMode()
                && StringUtils.hasText(appHome)) {
            System.setProperty("pf4j.pluginsDir", appHome + File.separator + pluginsRoot);
        }

        SpringBootPluginManager pluginManager = new SpringBootPluginManager(
                new File(pluginsRoot).toPath()) {
            @Override
            protected PluginLoader createPluginLoader() {
                return new CustomPluginLoader(this);
            }

            @Override
            protected PluginStatusProvider createPluginStatusProvider() {
                if (PropertyPluginStatusProvider.isPropertySet(properties)) {
                    return new PropertyPluginStatusProvider(properties);
                }
                return super.createPluginStatusProvider();
            }
        };

        pluginManager.setProfiles(properties.getPluginProfiles());
        pluginManager.presetProperties(flatProperties(properties.getPluginProperties()));
        pluginManager.setExactVersionAllowed(properties.isExactVersionAllowed());
        pluginManager.setSystemVersion(properties.getSystemVersion());
        pluginManager.setAutoStartPlugin(properties.isAutoStartPlugin());

        return pluginManager;
    }

    private Map<String, Object> flatProperties(Map<String, Object> propertiesMap) {
        Stack<String> pathStack = new Stack<>();
        Map<String, Object> flatMap = new HashMap<>();
        propertiesMap.entrySet().forEach(mapEntry -> {
            recurse(mapEntry, entry -> {
                pathStack.push(entry.getKey());
                if (entry.getValue() instanceof Map) return;
                flatMap.put(String.join(".", pathStack), entry.getValue());

            }, entry -> {
                pathStack.pop();
            });
        });
        return flatMap;
    }

    private void recurse(Map.Entry<String, Object> entry,
                         Consumer<Map.Entry<String, Object>> preConsumer,
                         Consumer<Map.Entry<String, Object>> postConsumer) {
        preConsumer.accept(entry);

        if (entry.getValue() instanceof Map) {
            Map<String, Object> entryMap = (Map<String, Object>) entry.getValue();
            for (Map.Entry<String, Object> subEntry : entryMap.entrySet()) {
                recurse(subEntry, preConsumer, postConsumer);
            }
        }

        postConsumer.accept(entry);
    }

}