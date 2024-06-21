package Neurons.outputNeurons;

import Individual.Individual;

//Originally movenorth
public class MoveWest extends OutputNeuron {

  public MoveWest() {
    super();
  }

  public void activate(Individual individual) {
    try {
      if (individual.getYPosition() != 0) {
        int oldX = individual.getXPosition();
        int oldY = individual.getYPosition();

        if (individual.getMap().getBoard()[oldX][oldY - 1].setIndividual(individual)) {
          individual.getMap().getBoard()[oldX][oldY].removeIndividual();
        }
      }
    } catch (Exception e) {
    }
  }
}
