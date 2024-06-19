package Neurons.inputNeurons;

import Individual.Individual;

public class Health extends InputNeuron {

  public Health() {
    super();
  }

  public double getValue(Individual individual) {
    return individual.getHealth();
  }

  public void action() {
    System.out.println("Health: " + individual.getHealth());
  }
}
