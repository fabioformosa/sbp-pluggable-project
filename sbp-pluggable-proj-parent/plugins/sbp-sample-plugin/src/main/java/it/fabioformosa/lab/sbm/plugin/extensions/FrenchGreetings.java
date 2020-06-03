package it.fabioformosa.lab.sbm.plugin.extensions;

import it.fabio.formosa.lab.sbp.extensions.Greetings;

public class FrenchGreetings implements Greetings {

  @Override
  public String sayHello() {
    return "salut!";
  }

}
