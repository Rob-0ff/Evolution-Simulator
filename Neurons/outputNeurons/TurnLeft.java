package Neurons.outputNeurons;

import Individual.Individual;

public class TurnLeft extends OutputNeuron {

  public TurnLeft() {
    super();
  }

  public void activate(Individual individual) {
    try {
      firing = true;
      individual.setOrientation(individual.getRawOrientation() - 1);
      individual.getMap().updatePanel();
      firing = false;
    } catch (Exception e) {

    }
  }
}