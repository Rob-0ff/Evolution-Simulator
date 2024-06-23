import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import Individual.Individual;
import Map.*;

public class Main {
  static int numIndividuals = 30;
  static Map map = new Map();
  static CyclicBarrier startingBarrier = new CyclicBarrier(numIndividuals);
  static CyclicBarrier completedBarrier = new CyclicBarrier(numIndividuals, () -> limitReached(map));

  public static void main(String[] args) throws InterruptedException {

    for (int i = 0; i < 30; i++) {

      System.out.println("---------- " + i + " ---------");

      // Create new Individuals for each iteration
      List<Individual> individuals = new ArrayList<>();
      for (int j = 0; j < numIndividuals; j++) {
        Individual individual = new Individual(map, startingBarrier, completedBarrier);
        individuals.add(individual);
        map.addIndividual(individual);
      }

      // map.visualiseNeuralNetwork();

      // Start all individuals
      for (Individual ind : individuals) {
        ind.start();
      }

      for (Individual ind : individuals) {
        ind.join();
      }

      System.out.println("------------Restart--------------");
    }
  }

  public static void limitReached(Map map) {
    map.clearIndividuals();
  }
}