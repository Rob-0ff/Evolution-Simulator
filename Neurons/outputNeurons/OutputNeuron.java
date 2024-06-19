package Neurons.outputNeurons;

import Individual.Individual;
import Neurons.Neuron;

public abstract class OutputNeuron extends Neuron {
  public static int numOutputNeurons = 4;

  OutputNeuron() {
    super();
  }

  public abstract void activate();

  public double getValue(Individual individual) {
    return 0;
  }

  @Override
  public String toString() {
    return this.getClass().getName();
  }
}
