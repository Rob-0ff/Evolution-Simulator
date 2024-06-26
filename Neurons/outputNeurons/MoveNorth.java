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
      individual.stepMovementX -= 1;
      firing = false;
    } catch (Exception e) {
    }
  }
}
