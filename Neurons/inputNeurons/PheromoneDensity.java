package Neurons.inputNeurons;

import java.util.ArrayList;

import Individual.Individual;
import Map.Cell;

public class PheromoneDensity extends InputNeuron {

  public PheromoneDensity() {
    super();
  }

  public double getValue(Individual individual) {
    ArrayList<Cell> neighbourhood = individual.getNeighbourhood();

    double totalPheromones = 0;
    for (Cell cell : neighbourhood) {
      totalPheromones += cell.getPheromones();
    }

    return totalPheromones / neighbourhood.size();
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
