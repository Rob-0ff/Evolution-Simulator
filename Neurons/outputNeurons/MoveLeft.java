package Neurons.outputNeurons;

import Individual.Individual;

//Originally movesouth

public class MoveLeft extends OutputNeuron {

  public MoveLeft() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;

      if (individual.getOrientation() == 0) {
        individual.stepMovementY += 1;
      } else if (individual.getOrientation() == 1) {
        individual.stepMovementX += 1;
        individual.stepMovementY += 1;
      } else if (individual.getOrientation() == 2) {
        individual.stepMovementX += 1;
      } else if (individual.getOrientation() == 3) {
        individual.stepMovementX += 1;
        individual.stepMovementY -= 1;
      }
      if (individual.getOrientation() == 4) {
        individual.stepMovementY -= 1;
      } else if (individual.getOrientation() == 5) {
        individual.stepMovementX -= 1;
        individual.stepMovementY -= 1;
      } else if (individual.getOrientation() == 6) {
        individual.stepMovementX -= 1;
      } else if (individual.getOrientation() == 7) {
        individual.stepMovementX -= 1;
        individual.stepMovementY += 1;
      }

      firing = false;
    } catch (Exception e) {

    }
  }
}
