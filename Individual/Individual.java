package Individual;

import Neurons.inputNeurons.*;
import Neurons.hiddenNeurons.*;
import Neurons.outputNeurons.*;
import Map.Cell;
import Map.Map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.reflections.Reflections;

public class Individual extends Thread {
  static int numSteps = 200;
  CyclicBarrier barrier;

  Iterator<Class<? extends HiddenNeuron>> hiddenIterator;
  Iterator<Class<? extends OutputNeuron>> outputIterator;
  Iterator<Class<? extends InputNeuron>> inputIterator;

  public void setHiddenIterator(Iterator<Class<? extends HiddenNeuron>> hiddenIterator) {
    this.hiddenIterator = hiddenIterator;
  }

  public void setOutputIterator(Iterator<Class<? extends OutputNeuron>> outputIterator) {
    this.outputIterator = outputIterator;
  }

  public void setInputIterator(Iterator<Class<? extends InputNeuron>> inputIterator) {
    this.inputIterator = inputIterator;
  }

  String DNA = "";
  ArrayList<String> codonArrayList = new ArrayList<String>();

  Map map;

  ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
  ArrayList<HiddenNeuron> hiddenNeurons = new ArrayList<HiddenNeuron>();
  ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();

  Brain brain;
  Cell currentCell;

  int[] RGB = new int[3];
  double health = 100;

  static String[] hexLetters = new String[] { "A", "B", "C", "D", "E", "F" };
  static String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

  static int connections = 15;

  public Individual(Map map, CyclicBarrier barrier) {
    this.barrier = barrier;
    this.map = map;
    for (int i = 0; i < 8 * connections; i++) {
      if (Math.random() < 0.5)
        DNA += hexDigits[(int) (Math.random() * 10)];
      else {
        DNA += hexLetters[(int) (Math.random() * 6)];
      }
    }

    convertDNAToRGB();

    for (int i = 0; i < DNA.length(); i += 8) {
      codonArrayList.add(DNA.substring(i, i + 8));
    }

    Reflections hiddenReflections = new Reflections("Neurons.hiddenNeurons");
    Reflections inputReflections = new Reflections("Neurons.inputNeurons");
    Reflections outputReflections = new Reflections("Neurons.outputNeurons");

    setHiddenIterator(hiddenReflections.getSubTypesOf(HiddenNeuron.class).iterator());
    setInputIterator(inputReflections.getSubTypesOf(InputNeuron.class).iterator());
    setOutputIterator(outputReflections.getSubTypesOf(OutputNeuron.class).iterator());

    intiateNeuronLists();
    brain = new Brain(this);
  }

  @Override
  public void run() {
    try {
      for (int i = 0; i < numSteps; i++) {
        // Simulate individual action
        brain.runBrain();
        Thread.sleep(100);
      }
      barrier.await();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }

  private void convertDNAToRGB() {
    String rHex = DNA.substring(0, 2);
    String gHex = DNA.substring(2, 4);
    String bHex = DNA.substring(4, 6);

    RGB[0] = Integer.parseInt(rHex, 16);
    RGB[1] = Integer.parseInt(gHex, 16);
    RGB[2] = Integer.parseInt(bHex, 16);
  }

  public String getDNA() {
    return DNA;
  }

  public ArrayList<String> getCodons() {
    return codonArrayList;
  }

  public int getXPosition() {
    return currentCell.getX();
  }

  public int getYPosition() {
    return currentCell.getY();
  }

  public double getHealth() {
    return health;
  }

  public int[] getRGB() {
    return RGB;
  }

  public void setCell(Cell cell) {
    this.currentCell = cell;
  }

  public Cell getCell() {
    return this.currentCell;
  }

  public void printBrain() {
    System.out.println(this.brain.toString());
  }

  public int getBrainSize() {
    return connections;
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

  public Map getMap() {
    return this.map;
  }

  public void setMap(Map map) {
    this.map = map;
  }
}
