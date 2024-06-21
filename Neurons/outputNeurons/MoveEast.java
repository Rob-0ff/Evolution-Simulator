package Neurons.outputNeurons;

import Individual.Individual;

//Originally movesouth

public class MoveEast extends OutputNeuron {

  public MoveEast() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getYPosition() != individual.getMap().getYSize() - 1) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        if (individual.getMap().getBoard()[oldX][oldY + 1].setIndividual(individual)) {
          individual.getMap().getBoard()[oldX][oldY].removeIndividual();
        }
      }
    } catch (Exception e) {

    }
  }
}
