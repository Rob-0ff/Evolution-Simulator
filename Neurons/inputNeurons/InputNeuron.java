package Neurons.inputNeurons;

import Individual.Individual;
import Neurons.Neuron;

public abstract class InputNeuron extends Neuron {
  public static int numInputNeurons = 6;

  InputNeuron() {
    super();
  }

  public abstract double getValue(Individual individual);

  @Override
  public String toString() {
    return "";
  }
}
