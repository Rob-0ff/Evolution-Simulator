package Neurons.inputNeurons;

import Individual.Individual;

public class Age extends InputNeuron {

  public Age() {
    super();
  }

  public double getValue(Individual individual) {
    return individual.getAge();
  }

  public void action() {
    System.out.println("Age: " + individual.getAge());
  }
}
