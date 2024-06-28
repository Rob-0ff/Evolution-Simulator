package Neurons.outputNeurons;

import Individual.Individual;

//Originally movesouth

public class MoveBackward extends OutputNeuron {

  public MoveBackward() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;

      if (individual.getOrientation() == 0) {
        individual.stepMovementX -= 1;
      } else if (individual.getOrientation() == 1) {
        individual.stepMovementY += 1;
        individual.stepMovementX -= 1;
      } else if (individual.getOrientation() == 2) {
        individual.stepMovementY += 1;
      } else if (individual.getOrientation() == 3) {
        individual.stepMovementY += 1;
        individual.stepMovementX += 1;
      }
      if (individual.getOrientation() == 4) {
        individual.stepMovementX += 1;
      } else if (individual.getOrientation() == 5) {
        individual.stepMovementY -= 1;
        individual.stepMovementX += 1;
      } else if (individual.getOrientation() == 6) {
        individual.stepMovementY -= 1;
      } else if (individual.getOrientation() == 7) {
        individual.stepMovementY -= 1;
        individual.stepMovementX -= 1;
      }

      firing = false;
    } catch (Exception e) {

    }
  }
}
