package Neurons.outputNeurons;

import Individual.Individual;
import Map.Cell;

//Originally movesouth

public class DropPheromones extends OutputNeuron {

  public DropPheromones() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      Cell currentCell = individual.getCell();

      currentCell.setPheromones(currentCell.getPheromones() + 0.1);

      firing = false;
    } catch (Exception e) {

    }
  }
}
