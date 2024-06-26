package Neurons.outputNeurons;

import Individual.Individual;

//Originally movesouth

public class MoveEast extends OutputNeuron {

  public MoveEast() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      individual.stepMovementY += 1;
      firing = false;
    } catch (Exception e) {

    }
  }
}
