import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import Individual.Individual;
import Map.*;

public class Main {
  static int numIndividuals = 150;
  static Map map = new Map();
  static CyclicBarrier newBarrier = new CyclicBarrier(numIndividuals, () -> limitReached(map));

  public static void main(String[] args) throws InterruptedException {

    for (int i = 0; i < 30; i++) {

      System.out.println("---------- " + i + " ---------");

      // Create new Individuals for each iteration
      List<Individual> individuals = new ArrayList<>();
      for (int j = 0; j < numIndividuals; j++) {
        Individual individual = new Individual(map, newBarrier);
        individuals.add(individual);
        map.addIndividual(individual);
      }

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