package Neurons.outputNeurons;

import Individual.Individual;

//originally moveeast
public class MoveSouth extends OutputNeuron {

  public MoveSouth() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      individual.stepMovementX += 1;
      firing = false;
    } catch (Exception e) {
    }
  }
}