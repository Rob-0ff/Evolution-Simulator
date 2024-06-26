package Neurons.inputNeurons;

import Individual.Individual;

public class Orientation extends InputNeuron {

  public Orientation() {
    super();
  }

  public double getValue(Individual individual) {
    return individual.getOrientation();
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
