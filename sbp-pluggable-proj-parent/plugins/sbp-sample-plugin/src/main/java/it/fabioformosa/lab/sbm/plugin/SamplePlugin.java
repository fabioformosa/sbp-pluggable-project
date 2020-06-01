package it.fabioformosa.lab.sbm.plugin;

import org.laxture.sbp.SpringBootPlugin;
import org.laxture.sbp.spring.boot.SpringBootstrap;
import org.pf4j.PluginWrapper;

public class SamplePlugin extends SpringBootPlugin {

  public SamplePlugin(PluginWrapper wrapper) {
    super(wrapper);
  }

  @Override
  protected SpringBootstrap createSpringBootstrap() {
    return new SpringBootstrap(this, SamplePluginStarter.class);
  }

}
