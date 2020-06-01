package it.fabioformosa.lab.sbm.mainapp.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.pf4j.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.fabio.formosa.lab.sbp.extensions.Greetings;

@RestController
@RequestMapping(value = "/greetings")
public class PluginController {

  @Autowired(required = false)
  private PluginManager pluginManager;
  //  @Autowired(required = false)
  //  private SpringBootPluginManager pluginManager;

  @GetMapping()
  public List<String> listExtensions() {
    List<Greetings> greetings = pluginManager.getExtensions(Greetings.class);
    return greetings.stream().map(Greetings::sayHello).collect(Collectors.toList());
  }

}
