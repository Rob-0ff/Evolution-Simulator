package Neurons.hiddenNeurons;

import Individual.Individual;
import Neurons.Neuron;

public abstract class HiddenNeuron extends Neuron {

  public static int numHiddenNeurons = 2;

  double memory;

  public HiddenNeuron() {
    super();
  }

  public double getValue(Individual individual) {
    return 0;
  }

  @Override
  public double getIntakeValue() {
    return memory + super.getIntakeValue();
  }

  public void addToMemory(double memory) {
    this.memory += memory;
  }

  @Override
  public String toString() {
    return "";
  }
}
