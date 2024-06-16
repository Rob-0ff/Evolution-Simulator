package Neurons.outputNeurons;

import Individual.Individual;

public class MoveNorth extends OutputNeuron {

  public MoveNorth() {
    super();
  }

  public void action(Individual individual) {
    int x = individual.getXPosition();
    int y = individual.getYPosition();
    System.out.println("Moving to: " + x + ", " + y);
  }
}
