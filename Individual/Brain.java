package Individual;

import Neurons.Neuron;
import Neurons.inputNeurons.*;
import Neurons.outputNeurons.*;
import Neurons.hiddenNeurons.*;
import Neurons.Connection;

import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Iterator;

public class Brain {
  Individual individual;

  public Brain(Individual individual) {
    this.individual = individual;

    for (String codon : individual.getCodons()) {
      Neuron start = null;
      Neuron finish = null;
      double weight;

      int length = codon.length() * 4;
      String bin = new BigInteger(codon, 16).toString(2);
      bin = String.format("%" + length + "s", bin).replace(' ', '0');

      String startNeuronType = bin.substring(0, 1);
      String startNeuronSubClass = bin.substring(1, 6);
      String finishNeuronType = bin.substring(6, 7);
      String finishNeuronSubClass = bin.substring(7, 12);
      String weightSign = bin.substring(12, 13);
      String weightString = bin.substring(13);

      if (startNeuronType.equals("0")) {
        start = individual.getInputNeurons()
            .get(Integer.parseInt(startNeuronSubClass, 2) % InputNeuron.numInputNeurons);
      } else {
        start = individual.getHiddenNeurons()
            .get(Integer.parseInt(startNeuronSubClass, 2) % HiddenNeuron.numHiddenNeurons);
      }

      if (finishNeuronType.equals("0")) {
        finish = individual.getHiddenNeurons()
            .get(Integer.parseInt(finishNeuronSubClass, 2) % HiddenNeuron.numHiddenNeurons);
      } else {
        finish = individual.getOutputNeurons()
            .get(Integer.parseInt(finishNeuronSubClass, 2) % OutputNeuron.numOutputNeurons);
      }

      weight = Integer.parseInt(weightString, 2) / 100000.0;

      if (weightSign.equals("0")) {
        weight *= -1;
      }

      start.getToNeurons().add(new Connection(finish, weight, true));
      finish.getFromNeurons().add(new Connection(start, weight, false));

    }

    checkAndRemoveRedundantPaths();

    System.out.println("Input:");
    for (Neuron n : individual.getInputNeurons()) {
      for (Connection con : n.getToNeurons()) {
        System.out.println(n.getClass().getName() + con.toString());
      }
      for (Connection con : n.getFromNeurons()) {
        System.out.println(n.getClass().getName() + con.toString());
      }
    }

    System.out.println("Hidden:");
    for (Neuron n : individual.getHiddenNeurons()) {
      for (Connection con : n.getToNeurons()) {
        System.out.println(n.getClass().getName() + con.toString());
      }
      for (Connection con : n.getFromNeurons()) {
        System.out.println(n.getClass().getName() + con.toString());
      }
    }
  }

  public void checkAndRemoveRedundantPaths() {
    boolean exit = false;
    for (HiddenNeuron hn : individual.getHiddenNeurons()) {
      if (hn.getToNeurons().isEmpty()) {
        for (Connection con : hn.getFromNeurons()) {
          for (Connection con2 : con.getNeuron().getToNeurons()) {
            if (con2.getNeuron() == hn && con2.getWeight() == con.getWeight()) {
              con.getNeuron().getToNeurons().remove(con2);
              hn.getFromNeurons().remove(con);
              exit = true;
              break;
            }
          }
          if (exit) {
            exit = false;
            break;
          }
        }
      }
    }
  }

  public void runBrain() {
    for (InputNeuron n : individual.getInputNeurons()) {
      for (Connection con : n.getToNeurons()) {
        con.getNeuron().addToIntakeValue(n.getValue(individual) * con.getWeight());
      }
    }

    for (HiddenNeuron n : individual.getHiddenNeurons()) {
      for (Connection con : n.getToNeurons()) {
        con.getNeuron().addToIntakeValue(n.getValue(individual) * con.getWeight());
      }
    }

    for (HiddenNeuron n : individual.getHiddenNeurons()) {
      if (sigmoidActivationFunction(n.getIntakeValue())) {
        for (Connection con : n.getToNeurons()) {
          con.getNeuron().addToIntakeValue(n.getValue(individual) * con.getWeight());
        }
      }
    }

    for (OutputNeuron n : individual.getOutputNeurons()) {
      if (sigmoidActivationFunction(n.getIntakeValue())) {
        n.activate(individual);
      }
    }
  }

  public boolean sigmoidActivationFunction(double x) {
    double tanh = (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));

    if (tanh < 0) {
      return false;
    } else {
      return true;
    }
    // double sigmoidValue = 1 / 1 - Math.exp(-x);

    // if (sigmoidValue < 0.5) {
    // return false;
    // } else {
    // return true;
    // }
  }

  @Override
  public String toString() {
    return "";
  }
}
