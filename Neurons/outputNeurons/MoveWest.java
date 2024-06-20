package Neurons.outputNeurons;

import Individual.Individual;

public class MoveWest extends OutputNeuron {

  public MoveWest() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getXPosition() != 0
          && individual.getMap().getBoard()[individual.getXPosition() - 1][individual.getYPosition()] != individual
              .getMap().individualRep) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();
        individual.setXPosition(individual.getXPosition() - 1);

        individual.getMap().update(oldX, oldY, individual);
      }
    } catch (Exception e) {
    }
  }
}
