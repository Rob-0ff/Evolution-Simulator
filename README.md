# Evolution-Simulator

A program that simulates evolution of simple single celled organisms represented by neural networks that attempt to survive their current environment. This is the first instance of this project and will be extended to include 3D visuals in the future. This version is coded in Java 22.0.1 and uses simple ascii characters as its visualisation.

This project is inspired by the Youtuber davidrandallmiller in their video [here](https://www.youtube.com/watch?v=N3tRFayqVtk)

# Architecture

## DNA

Each individual (or "single cell organism") consists of a string of DNA coded in Hexadecimal values. This DNA is broken down into
codons of 8 Hex values each. These codons represent the connections between different neurons within the individuals "brain".

## "Brain"

The "brain" of an individual is made up of a Recurrent Neural Network (RNN). This RNN is encoded in the DNA of each individual. The codons
determine a single connection between two neurons in the individuals brain. Each codon specifies the type (input, hidden, or output) and index of both the source and target neurons, as well as the weight of the connection. The first 16 bits determine the source and target neuron types and their respective indices, while the remaining bits encode the weight as a floating-point value. The decoded information is used to establish directed connections between neurons, with the source neuron connecting to the target neuron via a weighted edge, creating the network's structure.

Currently the activation functions used in all connections is the Hyperbolic tangent (tanh) function:

$$
f(x) = \frac{e^x - e^{-x}}{e^x + e^{-x}}
$$

## Current Neurons

The list of neurons currently created is not exhaustive. However, the addition of new input and output neurons is a relatively straight forward task.
The list of hidden neurons is not shown as hidden neurons do not have specific tasks. Instead they are generic neurons that can be created by the DNA
and placed within the network to be used by any connection.

### Input neurons

- Age
- Health
- Orientation
- X position
- Y poistion
- Pheromone density
- Forward pheromone gradient
- Right pheromone gradient
- Left pheromone gradient

### Output neurons

- Move forward
- Move backward
- Move left
- Move right
- Move North
- Move South
- Move East
- Move West
- Turn left
- Turn right
- Drop pheromones

# Initial population

Here is a video showing the initial population once created.

https://github.com/user-attachments/assets/5c7348c5-6fcc-4ac1-8a75-ce68efe272d7



