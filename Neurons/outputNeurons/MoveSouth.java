package Neurons.outputNeurons;

import Individual.Individual;

//originally moveeast
public class MoveSouth extends OutputNeuron {

  public MoveSouth() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getXPosition() != individual.getMap().getXSize() - 1) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        if (individual.getMap().getBoard()[oldX + 1][oldY].setIndividual(individual)) {
          individual.getMap().getBoard()[oldX][oldY].removeIndividual();
        }
      }
    } catch (Exception e) {
    }
  }
}