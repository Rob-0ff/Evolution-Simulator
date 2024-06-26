import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import Individual.Individual;
import Map.Cell;
import Map.Map;

public class Main {
  static int numIndividuals = 100;
  static Map map = new Map();
  static CyclicBarrier startingBarrier = new CyclicBarrier(numIndividuals);
  static CyclicBarrier midpointBarrier = new CyclicBarrier(numIndividuals, () -> midpointUpdates());
  static CyclicBarrier completedBarrier = new CyclicBarrier(numIndividuals, () -> {
    try {
      limitReached(map);
    } catch (IOException e) {
    }
  });

  public static void main(String[] args) throws InterruptedException, IOException {

    for (int i = 0; i < 500; i++) {

      System.out.println("---------- " + i + " ---------");

      List<Individual> individuals = new ArrayList<>();
      for (int j = 0; j < numIndividuals; j++) {
        Individual individual = new Individual(map, startingBarrier, midpointBarrier, completedBarrier);

        individuals.add(individual);
        map.addIndividual(individual);
      }

      // map.visualiseNeuralNetwork();

      for (Individual ind : individuals) {
        ind.start();
      }

      for (Individual ind : individuals) {
        ind.join();
      }

      System.out.println(individuals.get(0).getAge());

      System.out.println("------------Restart--------------");
    }
  }

  public static void limitReached(Map map) throws IOException {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    map.clearIndividuals();
  }

  public static void midpointUpdates() {
    for (Cell[] row : map.getBoard()) {
      for (Cell cell : row) {
        cell.setPheromones(cell.getPheromones() * 0.999);
      }
    }

    // map.updatePanel();
  }
}