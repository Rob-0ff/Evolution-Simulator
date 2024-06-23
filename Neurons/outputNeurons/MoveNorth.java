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

        individual.getMap().getBoard()[oldX - 1][oldY].setIndividual(individual);
      }
      firing = false;
    } catch (Exception e) {
    }
  }
}
