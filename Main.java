import Individual.Individual;
import Map.Map;

public class Main {

  public static void main(String[] args) {
    Individual individual = new Individual();

    Map map = new Map();

    map.addIndividual(individual);

    map.printBoard();
  }

}