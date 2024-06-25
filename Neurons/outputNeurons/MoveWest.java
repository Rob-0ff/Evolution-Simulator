package Neurons.outputNeurons;

import Individual.Individual;

//Originally movenorth
public class MoveWest extends OutputNeuron {

  public MoveWest() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      if (individual.getYPosition() != 0) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        individual.stepMovementY -= 1;
      }
      firing = false;
    } catch (Exception e) {
    }
  }
}
