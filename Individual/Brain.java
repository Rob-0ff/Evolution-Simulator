package Individual;

import Neurons.Neuron;
import Neurons.inputNeurons.*;
import Neurons.outputNeurons.*;
import Neurons.hiddenNeurons.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Brain {
  ArrayList<Neuron> startingNeurons = new ArrayList<Neuron>();

  // TODO - INDEXING NEEDS TO BE WORKED ON FOR CREATING NEURAL PATHWAYS
  public Brain(String DNA) {
    int index1 = 0;
    int index2 = 2;

    while (index2 < DNA.length()) {
      int neuronType = Integer.parseInt(DNA.substring(index1, index2), 16) % 3;

      if (neuronType == 0) {
        startingNeurons.add(createInputNeuron(DNA, index1, index2));
      } else if (neuronType == 1) {
        startingNeurons.add(createHiddenNeuron(DNA, index1, index2));
      }

      index1 += 2;
      index2 += 2;
    }
  }

  public InputNeuron createInputNeuron(String DNA, int index1, int index2) {
    int inputType = Integer.parseInt(DNA.substring(index1, index2), 16) % InputNeuron.numInputNeurons;

    switch (inputType) {
      case 0:
        return new Health();
      default:
        return null;
    }
  }

  public HiddenNeuron createHiddenNeuron(String DNA, int index1, int index2) {
    int hiddenType = Integer.parseInt(DNA.substring(index1, index2), 16) % HiddenNeuron.numHiddenNeurons;

    switch (hiddenType) {
      case 0:
        return new H1();
      default:
        return null;
    }
  }
}
