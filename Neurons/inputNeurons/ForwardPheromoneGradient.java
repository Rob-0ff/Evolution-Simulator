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

      // if (individual == individual.getMap().getIndividuals().get(0))
      // System.out.println("New: " + newX + ", " + newY);
      if (individual.getMap().isValidCell(newX, newY)) {
        forwardCells.add(individual.getMap().getBoard()[newX][newY]);
      }
    }

    if (forwardCells.size() == 0.0) {
      return Double.MIN_VALUE; // TODO - check if this is handled correctly
    }

    double pheromoneGradient = individual.getCell().getPheromones();

    for (Cell cell : forwardCells) {
      pheromoneGradient -= cell.getPheromones();

      // if (individual == individual.getMap().getIndividuals().get(0)) {
      // System.out.println("(" + cell.getX() + "," + cell.getY() + ") - " +
      // cell.getPheromones());
      // }

    }

    // if (individual == individual.getMap().getIndividuals().get(0)) {
    // System.out.println("Gradient: " + pheromoneGradient);
    // System.out.println("Current: " + individual.getXPosition() + ", " +
    // individual.getYPosition());
    // System.out.println("Size: " + forwardCells.size());
    // System.out.println("O: " + individual.getOrientation() +
    // "\n--------------------");
    // }

    return pheromoneGradient / 3;
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
