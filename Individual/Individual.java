package Individual;

import Neurons.inputNeurons.*;
import Neurons.hiddenNeurons.*;
import Neurons.outputNeurons.*;

import Neurons.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Individual {
  static Iterator<Class<? extends HiddenNeuron>> hiddenIterator;
  static Iterator<Class<? extends OutputNeuron>> outputIterator;
  static Iterator<Class<? extends InputNeuron>> inputIterator;

  public static void setHiddenIterator(Iterator<Class<? extends HiddenNeuron>> hiddenIterator) {
    Individual.hiddenIterator = hiddenIterator;
  }

  public static void setOutputIterator(Iterator<Class<? extends OutputNeuron>> outputIterator) {
    Individual.outputIterator = outputIterator;
  }

  public static void setInputIterator(Iterator<Class<? extends InputNeuron>> inputIterator) {
    Individual.inputIterator = inputIterator;
  }

  String DNA = "";
  ArrayList<String> codonArrayList = new ArrayList<String>();

  ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
  ArrayList<HiddenNeuron> hiddenNeurons = new ArrayList<HiddenNeuron>();
  ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();

  Brain brain;
  int xPosition = 0;
  int yPosition = 0;

  double health = 100;

  static String[] hexLetters = new String[] { "A", "B", "C", "D", "E", "F" };
  static String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

  static int brainSize = 6;

  public Individual() {
    for (int i = 0; i < 8 * brainSize; i++) {
      if (Math.random() < 0.5)
        DNA += hexDigits[(int) (Math.random() * 10)];
      else {
        DNA += hexLetters[(int) (Math.random() * 6)];
      }
    }

    for (int i = 0; i < DNA.length(); i += 8) {
      codonArrayList.add(DNA.substring(i, i + 8));
    }

    intiateNeuronLists();

    System.out.println("Creating brain");
    brain = new Brain(this);
  }

  public String getDNA() {
    return DNA;
  }

  public ArrayList<String> getCodons() {
    return codonArrayList;
  }

  public int getXPosition() {
    return xPosition;
  }

  public int getYPosition() {
    return yPosition;
  }

  public void setXPosition(int xPosition) {
    this.xPosition = xPosition;
  }

  public void setYPosition(int yPosition) {
    this.yPosition = yPosition;
  }

  public double getHealth() {
    return health;
  }

  public void printBrain() {
    System.out.println(this.brain.toString());
  }

  public int getBrainSize() {
    return brainSize;
  }

  public void intiateNeuronLists() {
    while (inputIterator.hasNext()) {
      try {
        inputNeurons.add(inputIterator.next().getDeclaredConstructor().newInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    while (hiddenIterator.hasNext()) {
      try {
        hiddenNeurons.add(hiddenIterator.next().getDeclaredConstructor().newInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    while (outputIterator.hasNext()) {
      try {
        outputNeurons.add(outputIterator.next().getDeclaredConstructor().newInstance());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }

  public ArrayList<InputNeuron> getInputNeurons() {
    return inputNeurons;
  }

  public ArrayList<HiddenNeuron> getHiddenNeurons() {
    return hiddenNeurons;
  }

  public ArrayList<OutputNeuron> getOutputNeurons() {
    return outputNeurons;
  }

  public Brain getBrain() {
    return this.brain;
  }
}