package Individual;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import Neurons.Connection;
import Neurons.Neuron;
import Neurons.hiddenNeurons.HiddenNeuron;
import Neurons.inputNeurons.InputNeuron;
import Neurons.outputNeurons.OutputNeuron;
public class Brain {
  Individual individual;

  public Brain(Individual individual) {
    this.individual = individual;
    ArrayList<String> codonStringList = individual.getCodons();
    ArrayList<Integer> codonNumberList = individual.getNumericCodons();

    for (int x = 0; x < codonStringList.size(); x++) {
      String bin = String.format("%32s", new BigInteger(codonStringList.get(x), 16).toString(2)).replace(' ', '0');
      String bin2 = String.format("%32s", Integer.toBinaryString(codonNumberList.get(x))).replace(' ', '0');
      if (bin.compareTo(bin2) != 0) {
        throw new IllegalArgumentException("Codon and Codon Number do not match: bin: " + bin + " bin2: " + bin2);
      }
    }
    for (Integer codon : codonNumberList) {
      Neuron start = null;
      Neuron finish = null;
      // double weight;

      int length = 32; // 32 bits
      // String bin = new BigInteger(codon, 16).toString(2);
      // bin = String.format("%" + length + "s", bin).replace(' ', '0');

      // String startNeuronType = bin.substring(0, 1);
      // String startNeuronSubClass = bin.substring(1, 6);
      // String finishNeuronType = bin.substring(6, 7);
      // String finishNeuronSubClass = bin.substring(7, 12);
      // String weightSign = bin.substring(12, 13);
      // String weightString = bin.substring(13);


      byte[] bytes = ByteBuffer.allocate(4).putInt(codon).array();
      // Combine the first two bytes into a 2 byte unsigned short variable named `edge`
      int edge = ((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF);
      // Extract the 16th bit of `edge` and store it in a boolean variable named `sType`
      boolean sType = (edge & (1 << 15)) != 0;
      // Extract bits 15-11 (5 bits) from `edge` and store in an unsigned short variable named `sNum`
      int sNum = (edge >> 10) & 0x1F;
      // Extract the 10th bit from `edge` and store in a boolean variable named `eType`
      boolean eType = (edge & (1 << 9)) != 0;
      // Extract bits 9-5 (5 bits) from `edge` and store in an unsigned short variable named `eNum`
      int eNum = (edge >> 4) & 0x1F;
      // Get sign of weight
      int floatSignBit = ((bytes[1] & 0x8) << 28); // get last (4th) bit and shift to pos 32
      // System.out.println("Sign: " + String.format("%32s", Integer.toBinaryString(floatSignBit)).replace(' ', '0'));

      // Get 4 bit exponent from last three bits of byte 1 and first bit from byte 2
      int floatExponent = (((bytes[1] << 1) & 0x7) | (bytes[2] >> 3)) & 0xF;
      int minExponent = 115;
      floatExponent += minExponent;
      // System.out.println("Exponent: " + (floatExponent));
      floatExponent = (floatExponent << 23);
      // System.out.println("Exponent: " + String.format("%32s", Integer.toBinaryString(floatExponent)).replace(' ', '0'));
      // Create a signed float in the following manner
      int floatBits = ((bytes[1] & 0x7) << 16); // get everything but sign and shift to end
      floatBits = floatBits | ((bytes[2] & 0xFF) << 8);
      floatBits = floatBits | (bytes[3] & 0xFF);
      floatBits = floatBits | floatSignBit;
      floatBits = floatBits | floatExponent;
      float weight = Float.intBitsToFloat(floatBits);
  
      // System.out.println("edge: " + String.format("%16s", Integer.toBinaryString(edge)).replace(' ', '0') + ", sType: " + sType + ", sNum: " + sNum + ", eType: " + eType + ", eNum: " + eNum);
      // System.out.println("weight: " + weight);
      if (sType == false) {
        start = individual.getInputNeurons()
            .get((((int) sNum) & 0xF) % InputNeuron.numInputNeurons);
      } else {
        start = individual.getHiddenNeurons()
            .get((((int) sNum) & 0xF) % HiddenNeuron.numHiddenNeurons);
      }

      if (eType == false) {
        finish = individual.getHiddenNeurons()
            .get((((int) eNum) & 0xF) % HiddenNeuron.numHiddenNeurons);
      } else {
        finish = individual.getOutputNeurons()
            .get((((int) eNum) & 0xF) % OutputNeuron.numOutputNeurons);
      }

      start.getToNeurons().add(new Connection(finish, weight, true));
      finish.getFromNeurons().add(new Connection(start, weight, false));
    }

    checkAndRemoveRedundantPaths();

    // System.out.println("Input:");
    // for (Neuron n : individual.getInputNeurons()) {
    // for (Connection con : n.getToNeurons()) {
    // System.out.println(n.getClass().getName() + con.toString());
    // }
    // for (Connection con : n.getFromNeurons()) {
    // System.out.println(n.getClass().getName() + con.toString());
    // }
    // }

    // System.out.println("Hidden:");
    // for (Neuron n : individual.getHiddenNeurons()) {
    // for (Connection con : n.getToNeurons()) {
    // System.out.println(n.getClass().getName() + con.toString());
    // }
    // for (Connection con : n.getFromNeurons()) {
    // System.out.println(n.getClass().getName() + con.toString());
    // }
    // }

    // System.out.println("####################");
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
      if (activationFunction(n.getIntakeValue())) {
        for (Connection con : n.getToNeurons()) {
          con.getNeuron().addToIntakeValue(calculateActivationValue(n.getIntakeValue()) * con.getWeight());

          if (con.getNeuron() instanceof HiddenNeuron) {
            ((HiddenNeuron) con.getNeuron())
                .addToMemory(calculateActivationValue(n.getIntakeValue()) * con.getWeight());
          }
        }
      }
    }

    for (OutputNeuron n : individual.getOutputNeurons()) {
      if (activationFunction(n.getIntakeValue())) {
        n.activate(individual);
      }
    }

    int tempX = individual.getCell().getX() + individual.stepMovementX;
    int tempY = individual.getCell().getY() + individual.stepMovementY;
    if (tempX >= 0 && tempX < individual.getMap().getBoard().length && tempY >= 0
        && tempY < individual.getMap().getBoard()[0].length)
      individual.getMap().getBoard()[individual.getCell().getX() + individual.stepMovementX][individual.getCell().getY()
          + individual.stepMovementY].setIndividual(individual);

    individual.stepMovementX = 0;
    individual.stepMovementY = 0;
  }

  public void clearIntakes() {
    for (HiddenNeuron n : individual.getHiddenNeurons()) {
      n.setIntakeValue(0);
    }

    for (OutputNeuron n : individual.getOutputNeurons()) {
      n.setIntakeValue(0);
    }
  }

  public boolean activationFunction(double x) {
    if (calculateActivationValue(x) < 0) {
      return false;
    } else {
      return true;
    }
  }

  public double calculateActivationValue(double val) {
    return (Math.exp(val) - Math.exp(-1 * val)) / (Math.exp(val) + Math.exp(-1 * val));
  }

  @Override
  public String toString() {
    return "";
  }
}
