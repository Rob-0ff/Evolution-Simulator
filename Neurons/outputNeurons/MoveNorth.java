package Neurons.outputNeurons;

import Individual.Individual;

//Originally movewest

public class MoveNorth extends OutputNeuron {

  public MoveNorth() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      if (individual.getXPosition() != 0) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        individual.stepMovementX -= 1;
      }
      firing = false;
    } catch (Exception e) {
    }
  }
}
