package Neurons;

public class Connection {
  Neuron neuron;

  boolean to;

  double weight;

  public Connection(Neuron neuron, double weight, boolean to) {
    this.neuron = neuron;
    this.weight = weight;
    this.to = to;
  }

  public double getWeight() {
    return weight;
  }

  public Neuron getNeuron() {
    return neuron;
  }

  @Override
  public String toString() {
    if (this.to)
      return " --> " + neuron.getClass().getName() + " : " + weight;
    else
      return " <-- " + neuron.getClass().getName() + " : " + weight;
  }
}
