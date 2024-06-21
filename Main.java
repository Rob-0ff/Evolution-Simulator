import Individual.Individual;
import Map.*;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Map map = new Map();

    for (int i = 0; i < 30; i++) {
      map.clearIndividuals();

      System.out.println("---------- " + i + " ---------");

      for (int j = 0; j < 100; j++) {
        Individual individual = new Individual(map);

        map.addIndividual(individual);
      }

      for (int j = 0; j < 150; j++) {
        Thread.sleep(30);

        for (Individual ind : map.getIndividuals()) {
          ind.getBrain().runBrain();
        }
      }
      System.out.println("------------Restart--------------");
    }
  }
}