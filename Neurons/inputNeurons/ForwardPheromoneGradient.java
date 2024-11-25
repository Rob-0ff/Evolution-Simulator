package Neurons.inputNeurons;

import Individual.Individual;
import Map.Cell;
import java.util.ArrayList;

public class ForwardPheromoneGradient extends InputNeuron {

  public ForwardPheromoneGradient() {
    super();
  }

  public double getValue(Individual individual) {
    ArrayList<Cell> forwardCells = new ArrayList<Cell>();

    for (int i = 1; i < 3; i++) {
      int newX = individual.getXPosition(), newY = individual.getYPosition();
      switch (individual.getOrientation()) {
        case 0:
          newX += i;
          break;
        case 1:
          newX += i;
          newY += i;
          break;
        case 2:
          newY += i;
          break;
        case 3:
          newX -= i;
          newY += i;
          break;
        case 4:
          newX -= i;
          break;
        case 5:
          newY -= i;
          newX += i;
          break;
        case 6:
          newY -= i;
          break;
        case 7:
          newX += i;
          newY -= i;
          break;
      }

      if (individual.getMap().isValidCell(newX, newY)) {
        forwardCells.add(individual.getMap().getBoard()[newX][newY]);
      }
    }

    if (forwardCells.size() == 0) {
      return Double.MIN_VALUE; // TODO - check if this is handled correctly
    }

    double pheromoneGradient = individual.getCell().getPheromones();
    double sumPheromones = 0;

    for (Cell cell : forwardCells) {
      sumPheromones -= cell.getPheromones();
    }

    double avgPheromone = sumPheromones / forwardCells.size();

    return pheromoneGradient - avgPheromone;
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
