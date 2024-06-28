package Neurons.inputNeurons;

import Individual.Individual;
import Map.Cell;
import java.util.ArrayList;

public class RightPheromoneGradient extends InputNeuron {

  public RightPheromoneGradient() {
    super();
  }

  public double getValue(Individual individual) {
    ArrayList<Cell> rightwardCells = new ArrayList<Cell>();

    for (int i = 1; i < 3; i++) {
      int newX = individual.getXPosition(), newY = individual.getYPosition();
      switch (individual.getOrientation()) {
        case 0:
          newY -= i;
          break;
        case 1:
          newX -= i;
          newY -= i;
          break;
        case 2:
          newX -= i;
          break;
        case 3:
          newX -= i;
          newY += i;
          break;
        case 4:
          newY += i;
          break;
        case 5:
          newX += i;
          newY += i;
          break;
        case 6:
          newX += i;
          break;
        case 7:
          newX += i;
          newY -= i;
          break;
      }

      if (individual.getMap().isValidCell(newX, newY)) {
        rightwardCells.add(individual.getMap().getBoard()[newX][newY]);
      }
    }

    double pheromoneGradient = individual.getCell().getPheromones();

    for (Cell cell : rightwardCells) {
      pheromoneGradient -= cell.getPheromones();
    }

    return pheromoneGradient;
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
