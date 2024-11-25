package Individual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.reflections.Reflections;

import Map.Cell;
import Map.Map;
import Neurons.hiddenNeurons.HiddenNeuron;
import Neurons.inputNeurons.InputNeuron;
import Neurons.outputNeurons.OutputNeuron;

public class Individual extends Thread {
  static int numSteps = 100;

  Iterator<Class<? extends HiddenNeuron>> hiddenIterator;
  Iterator<Class<? extends OutputNeuron>> outputIterator;
  Iterator<Class<? extends InputNeuron>> inputIterator;
  static double hapsburgCoefficient = 0.1;

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
  ArrayList<Integer> codonNumberArrayList = new ArrayList<Integer>();
  CyclicBarrier completedBarrier;
  CyclicBarrier startingBarrier;
  CyclicBarrier midpointBarrier;

  public int stepMovementX = 0;
  public int stepMovementY = 0;

  Map map;

  ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
  ArrayList<HiddenNeuron> hiddenNeurons = new ArrayList<HiddenNeuron>();
  ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();

  Brain brain;
  Cell currentCell;

  int[] RGB = new int[3];
  double health = 100;
  int age = 0;
  int orientation = 4;

  static String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
      "F" };

  static int connections = 10;

  public Individual(Map map, CyclicBarrier startingBarrier, CyclicBarrier midpointBarrier,
      CyclicBarrier completedBarrier) {
    this.completedBarrier = completedBarrier;
    this.startingBarrier = startingBarrier;
    this.midpointBarrier = midpointBarrier;

    this.map = map;
    int randomIndex = 0;
    int codonNumber = 0;
    Integer codon = 0;
    for (int i = 0; i < 8 * connections; i++) {
      // generate random number between 0 and 15
      // * 16, because type coercion floors instead of rounds
      randomIndex = (int) (Math.random() * 16);
      DNA += hexDigits[randomIndex];
      // codonNumberArrayList.add(randomIndex);
      codon = codon | ((randomIndex & 0xF) << (28 - (codonNumber % 8) * 4));

      ++codonNumber;
      if (codonNumber % 8 == 0) {
        codonNumberArrayList.add(codon);
        codon = 0;
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

  // Copy constructor
  public Individual(Individual urMum) {
    this.completedBarrier = urMum.completedBarrier;
    this.startingBarrier = urMum.startingBarrier;
    this.midpointBarrier = urMum.midpointBarrier;

    this.map = urMum.map;
    this.DNA = urMum.DNA;

    convertDNAToRGB();

    for (int i = 0; i < urMum.codonNumberArrayList.size(); ++i) {
      codonNumberArrayList.add(urMum.codonNumberArrayList.get(i));
    }
    mootate();
    Reflections hiddenReflections = new Reflections("Neurons.hiddenNeurons");
    Reflections inputReflections = new Reflections("Neurons.inputNeurons");
    Reflections outputReflections = new Reflections("Neurons.outputNeurons");

    setHiddenIterator(hiddenReflections.getSubTypesOf(HiddenNeuron.class).iterator());
    setInputIterator(inputReflections.getSubTypesOf(InputNeuron.class).iterator());
    setOutputIterator(outputReflections.getSubTypesOf(OutputNeuron.class).iterator());

    intiateNeuronLists();
    brain = new Brain(this);
  }

  private void mootate() {
    if (Math.random() > hapsburgCoefficient) {
      return;
    }
    int mutationCodon = (int) (Math.random() * codonNumberArrayList.size());
    int mutationBit = (int) (Math.random() * 31);
    int mutationMask = 1 << mutationBit;
    // XOR the bit with the mask to flip it
    codonNumberArrayList.set(mutationCodon, codonNumberArrayList.get(mutationCodon) ^ mutationMask);
  }

  public String getDNA() {
    return DNA;
  }

  public ArrayList<String> getCodons() {
    return codonArrayList;
  }

  public ArrayList<Integer> getNumericCodons() {
    return codonNumberArrayList;
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

  public void setHealth(double health) {
    this.health = health;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getAge() {
    return age;
  }

  public void printBrain() {
    System.out.println(this.brain.toString());
  }

  public int getBrainSize() {
    return connections;
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

  public int getRawOrientation() {
    return orientation;
  }

  public int getOrientation() {
    return Math.abs(orientation) % 8;
  }

  public void setOrientation(int orientation) {
    this.orientation = orientation;
  }

  // Additional methods

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

  public ArrayList<Cell> getNeighbourhood() {
    ArrayList<Cell> neighbourhood = new ArrayList<Cell>();

    int x = currentCell.getX();
    int y = currentCell.getY();

    for (int i = -2; i < 3; i++) {
      for (int j = -2; j < 3; j++) {
        if (i == 0 && j == 0)
          continue;
        if (x + i < 0 || x + i >= map.getXSize())
          continue;
        if (y + j < 0 || y + j >= map.getYSize())
          continue;
        neighbourhood.add(map.getBoard()[x + i][y + j]);
      }
    }

    return neighbourhood;
  }

  @Override
  public void run() {
    try {
      startingBarrier.await();
      for (int i = 0; i < numSteps; i++) {
        brain.runBrain();
        brain.clearIntakes();
        midpointBarrier.await();
        Thread.sleep(125);
      }
      completedBarrier.await();
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

  public double getFitness() {
    // TODO: Implement properly
    return 0;
  }
}
