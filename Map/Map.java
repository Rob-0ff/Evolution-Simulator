package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Individual.Individual;
import Neurons.hiddenNeurons.HiddenNeuron;
import Neurons.inputNeurons.InputNeuron;
import Neurons.outputNeurons.OutputNeuron;

public class Map {
  int xSize = 50;
  int ySize = 50;

  public static String[] individualRep = { "\u2193", "\u2199", "\u2190",
      "\u2196", "\u2191", "\u2197", "\u2192", "\u2198" };

  Cell[][] board = new Cell[xSize][ySize];

  ArrayList<Individual> individuals = new ArrayList<Individual>();
  JPanel panel = new Array2DPanel(board);
  JDialog dialog = new JDialog();
  InfoPanel infoPanel;

  public Map() {

    for (int i = 0; i < xSize; i++) {
      for (int j = 0; j < ySize; j++) {
        board[i][j] = new Cell(this, i, j);
      }
    }

    dialog.setTitle("2D Array Visualization");
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setBackground(new Color(255, 255, 255));
    dialog.add(panel);
    dialog.pack();
    dialog.setVisible(true);
  }

  public void printBoard() {
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[0].length; j++) {
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }

  public int getXSize() {
    return this.xSize;
  }

  public int getYSize() {
    return this.ySize;
  }

  public void addIndividual(Individual individual) {
    while (true) {
      int tempX = (int) Math.floor(Math.random() * xSize);
      int tempY = (int) Math.floor(Math.random() * ySize);

      if (board[tempX][tempY].setIndividual(individual)) {
        individuals.add(individual);
        break;
      }
    }

    if (individuals.size() == 1)
      ((Array2DPanel) panel).addFirst(individual);

    ((Array2DPanel) panel).updateArray();

  }

  public void visualiseNeuralNetwork() {
    dialog.setSize(new Dimension(1100, 800));
    infoPanel = new InfoPanel(this);
    dialog.add(infoPanel);
  }

  public Cell[][] getBoard() {
    return board;
  }

  public void clearIndividuals() {
    board = new Cell[xSize][ySize];

    for (int i = 0; i < xSize; i++) {
      for (int j = 0; j < ySize; j++) {
        board[i][j] = new Cell(this, i, j);
      }
    }

    individuals.clear();
    ((Array2DPanel) panel).clearArray(board);
  }

  public ArrayList<Individual> getIndividuals() {
    return this.individuals;
  }

  public Array2DPanel getPanel() {
    return ((Array2DPanel) panel);
  }

  public void updatePanel() {
    ((Array2DPanel) panel).updateArray();
    ((InfoPanel) infoPanel).updatePanel();
  }

  public boolean isValidCell(int x, int y) {
    return x >= 0 && x < xSize && y >= 0 && y < ySize;
  }
}

class InfoPanel extends JPanel {
  Map map;
  Individual ind;

  public InfoPanel(Map map) {
    this.map = map;
    ind = map.getIndividuals().get(0);
  }

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    int inputXStartingPos = 850;
    int inputYStartingPos = 0;
    int hiddenXStartingPos = 925;
    int hiddenYStartingPos = 0;
    int outputXStartingPos = 1000;
    int outputYStartingPos = 0;

    int neuronSize = 6;

    // Draw input neurons
    for (InputNeuron neuron : ind.getInputNeurons()) {
      g2d.fillOval(inputXStartingPos, inputYStartingPos, neuronSize, neuronSize);
      inputYStartingPos += 30;
    }

    for (HiddenNeuron neuron : ind.getHiddenNeurons()) {
      g2d.fillOval(hiddenXStartingPos, hiddenYStartingPos, neuronSize, neuronSize);
      hiddenYStartingPos += 30;
    }

    for (OutputNeuron neuron : ind.getOutputNeurons()) {
      if (neuron.getFiring()) {
        System.out.println("Firing");
        g.setColor(new Color(255, 0, 0));
      } else {
        g.setColor(new Color(0, 255, 0));
      }
      g2d.fillOval(outputXStartingPos, outputYStartingPos, neuronSize, neuronSize);
      outputYStartingPos += 30;
    }

    // // Draw connections
    // for (Connection connection : ind.getBrain().) {
    // g2d.drawLine(connection.from.x, connection.from.y, connection.to.x,
    // connection.to.y);
    // }
  }

  public void updatePanel() {
    repaint();
  }
}

class Array2DPanel extends JPanel {
  private Cell[][] array;
  private Individual first;

  public Array2DPanel(Cell[][] array) {
    this.array = array;
    setPreferredSize(new Dimension(700, 700));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawArray(g, array);
  }

  private void drawArray(Graphics g, Cell[][] array) {
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int rows = array.length;
    int cols = array[0].length;
    int cellWidth = (panelWidth / cols);
    int cellHeight = (panelHeight / rows);

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int x = j * cellWidth;
        int y = i * cellHeight;

        // Set default cell color
        g.setColor(Color.WHITE);

        if (array[i][j].getPheromones() != 0) {
          int intensity = (int) (255 * array[i][j].getPheromones());
          intensity = Math.min(255, Math.max(0, intensity));
          Color cellColor = new Color(0, 255, 0, intensity);

          g.setColor(cellColor);
        }

        g.fillRect(x, y, cellWidth, cellHeight); // Fill the cell with color
        g.setColor(Color.BLACK); // Set color for the border
        g.drawRect(x, y, cellWidth, cellHeight); // Draw cell border

        if (array[i][j].getIndividual() != null) {
          g.setColor(new Color(array[i][j].getIndividual().getRGB()[0],
              array[i][j].getIndividual().getRGB()[1],
              array[i][j].getIndividual().getRGB()[2]));
          g.setFont(g.getFont().deriveFont(Font.BOLD));
          g.drawString(Map.individualRep[array[i][j].getIndividual().getOrientation()],
              x + cellWidth / 2 - 4,
              y + cellHeight / 2 + 4);
        } else {
          g.drawString(" ", x + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
        }
      }
    }
  }

  public void clearArray(Cell[][] newArray) {
    array = newArray;
    updateArray();
  }

  public void updateArray() {
    repaint();
  }

  public void addFirst(Individual individual) {
    first = individual;
  }
}
