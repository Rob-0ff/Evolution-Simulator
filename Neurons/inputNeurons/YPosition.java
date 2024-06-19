package Neurons.inputNeurons;

import Individual.Individual;

public class YPosition extends InputNeuron {

  public YPosition() {
    super();
  }

  public double getValue(Individual individual) {
    return individual.getYPosition();
  }

  public void action() {
    System.out.println("YPosition: " + individual.getYPosition());
  }
}
