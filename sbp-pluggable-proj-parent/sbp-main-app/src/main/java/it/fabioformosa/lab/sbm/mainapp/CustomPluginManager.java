package it.fabioformosa.lab.sbm.mainapp;

import org.laxture.sbp.SpringBootPluginManager;
import org.pf4j.CompoundPluginLoader;
import org.pf4j.DefaultPluginLoader;
import org.pf4j.DevelopmentPluginLoader;
import org.pf4j.PluginLoader;

public class CustomPluginManager extends SpringBootPluginManager {

  @Override
  protected PluginLoader createPluginLoader() {
    return new CompoundPluginLoader()
        .add(new DevelopmentPluginLoader(this), this::isDevelopment)
        .add(new SpringBootExecutableJarPluginLoader(this), this::isNotDevelopment)
        .add(new DefaultPluginLoader(this), this::isNotDevelopment);
  }

}
