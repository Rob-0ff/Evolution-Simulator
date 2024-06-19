import Individual.Individual;
import Map.Map;
import Neurons.hiddenNeurons.HiddenNeuron;
import Neurons.inputNeurons.InputNeuron;
import Neurons.outputNeurons.OutputNeuron;

import Neurons.*;

import org.reflections.Reflections;

public class Main {

  public static void main(String[] args) {
    Reflections hiddenReflections = new Reflections("Neurons.hiddenNeurons");
    Reflections inputReflections = new Reflections("Neurons.inputNeurons");
    Reflections outputReflections = new Reflections("Neurons.outputNeurons");

    Individual.setHiddenIterator(hiddenReflections.getSubTypesOf(HiddenNeuron.class).iterator());
    Individual.setInputIterator(inputReflections.getSubTypesOf(InputNeuron.class).iterator());
    Individual.setOutputIterator(outputReflections.getSubTypesOf(OutputNeuron.class).iterator());

    Individual individual = new Individual();

    System.out.println("---------------------");
    individual.getBrain().runBrain();

    // System.out.println("Printing brain");
    // individual.printBrain();

    // Map map = new Map();

    // map.addIndividual(individual);

    // map.printBoard();
  }

}