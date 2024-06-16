package Neurons.inputNeurons;

import Individual.Individual;

public class Health extends InputNeuron {

  public Health() {
    super();
  }

  public void action(Individual individual) {
    System.out.println("Health: " + individual.getHealth());
  }
}
