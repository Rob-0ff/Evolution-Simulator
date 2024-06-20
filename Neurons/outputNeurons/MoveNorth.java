package Neurons.outputNeurons;

import Individual.Individual;

public class MoveNorth extends OutputNeuron {

  public MoveNorth() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getYPosition() != 0
          && individual.getMap().getBoard()[individual.getXPosition()][individual.getYPosition() - 1] != individual
              .getMap().individualRep) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        individual.setYPosition(individual.getYPosition() - 1);

        individual.getMap().update(oldX, oldY, individual);
      }
    } catch (Exception e) {
    }
  }
}
