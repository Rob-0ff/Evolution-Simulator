package Neurons.outputNeurons;

import Individual.Individual;

public class MoveEast extends OutputNeuron {

  public MoveEast() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getXPosition() != individual.getMap().getXSize() - 1
          && individual.getMap().getBoard()[individual.getXPosition() + 1][individual.getYPosition()] != 'O') {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        individual.setXPosition(individual.getXPosition() + 1);

        individual.getMap().update(oldX, oldY, individual);
      }
    } catch (Exception e) {
    }
  }
}