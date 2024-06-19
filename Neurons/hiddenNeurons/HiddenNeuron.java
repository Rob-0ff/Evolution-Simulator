package Neurons.hiddenNeurons;

import Individual.Individual;
import Neurons.Neuron;

public abstract class HiddenNeuron extends Neuron {

  public static int numHiddenNeurons = 2;

  double initialValue;

  public HiddenNeuron() {
    super();

    this.initialValue = Math.random();
  }

  public double getValue(Individual individual) {
    return 0;
  }

  @Override
  public String toString() {
    return "";
  }
}
