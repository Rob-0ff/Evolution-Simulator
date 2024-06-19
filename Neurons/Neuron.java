package Neurons;

import java.util.ArrayList;

import Individual.Individual;
import Neurons.hiddenNeurons.*;
import Neurons.outputNeurons.*;

public abstract class Neuron {

  protected Individual individual;

  protected double intakeValue = 0;

  protected ArrayList<Connection> toNeurons = new ArrayList<Connection>();

  protected ArrayList<Connection> fromNeurons = new ArrayList<Connection>();

  protected Neuron() {
    System.out.println(this.getClass().getName());
  }

  public double getIntakeValue() {
    return this.intakeValue;
  }

  public void addToIntakeValue(double val) {
    this.intakeValue += val;
  }

  public ArrayList<Connection> getToNeurons() {
    return this.toNeurons;
  }

  public ArrayList<Connection> getFromNeurons() {
    return this.fromNeurons;
  }

  public abstract double getValue(Individual individual);
}