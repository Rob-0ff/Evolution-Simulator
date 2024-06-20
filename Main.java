import Individual.Individual;
import Map.*;
import Neurons.hiddenNeurons.HiddenNeuron;
import Neurons.inputNeurons.InputNeuron;
import Neurons.outputNeurons.OutputNeuron;
import Neurons.*;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

import org.reflections.Reflections;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Map map = new Map();

    for (int i = 0; i < 10; i++) {
      map.clearIndividuals();

      System.out.println("---------- " + i + " ---------");

      for (int j = 0; j < 50; j++) {
        Individual individual = new Individual();

        map.addIndividual(individual);
      }

      for (int j = 0; j < 75; j++) {
        Thread.sleep(250);

        for (Individual ind : map.getIndividuals()) {
          ind.getBrain().runBrain();
        }
      }
      System.out.println("------------Restart--------------");
    }
  }
}