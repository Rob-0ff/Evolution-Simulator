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
      if (individual.getYPosition() != individual.getMap().getYSize() - 1) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        individual.getMap().getBoard()[oldX][oldY + 1].setIndividual(individual);
      }
      firing = false;
    } catch (Exception e) {

    }
  }
}
