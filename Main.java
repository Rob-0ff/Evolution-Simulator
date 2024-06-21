import Individual.Individual;
import Map.*;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Map map = new Map();

    for (int i = 0; i < 1; i++) {
      map.clearIndividuals();

      System.out.println("---------- " + i + " ---------");

      for (int j = 0; j < 25; j++) {
        Individual individual = new Individual(map);

        map.addIndividual(individual);
      }

      for (int j = 0; j < 75; j++) {
        Thread.sleep(75);

        for (Individual ind : map.getIndividuals()) {
          ind.getBrain().runBrain();
        }
      }
      System.out.println("------------Restart--------------");
    }
  }
}