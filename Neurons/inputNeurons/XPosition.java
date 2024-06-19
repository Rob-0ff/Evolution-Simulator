package Neurons.inputNeurons;

import Individual.Individual;

public class XPosition extends InputNeuron {

  public XPosition() {
    super();
  }

  public double getValue(Individual individual) {
    return individual.getXPosition();
  }

  public void action() {
    System.out.println("XPosition: " + individual.getXPosition());
  }
}
