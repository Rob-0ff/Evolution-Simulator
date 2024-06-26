package Neurons.outputNeurons;

import Individual.Individual;
import Neurons.Neuron;

public abstract class OutputNeuron extends Neuron {
  public static int numOutputNeurons = 1;

  boolean firing = false;

  OutputNeuron() {
    super();
  }

  public abstract void activate(Individual individual);

  public double getValue(Individual individual) {
    return 0;
  }

  public boolean getFiring() {
    return this.firing;
  }

  @Override
  public String toString() {
    return this.getClass().getName();
  }
}
