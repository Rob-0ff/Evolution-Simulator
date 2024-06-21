package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Individual.Individual;

public class Map {
  int xSize = 75;
  int ySize = 75;

  public static String individualRep = "\u26AB";

  Cell[][] board = new Cell[xSize][ySize];

  ArrayList<Individual> individuals = new ArrayList<Individual>();
  // Create the panel to visualize the array
  JPanel panel = new Array2DPanel(board);

  public Map() {

    for (int i = 0; i < xSize; i++) {
      for (int j = 0; j < ySize; j++) {
        board[i][j] = new Cell(this, i, j);
      }
    }

    JDialog dialog = new JDialog();
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

    ((Array2DPanel) panel).updateArray();

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
  }
}

class Array2DPanel extends JPanel {
  private Cell[][] array;

  public Array2DPanel(Cell[][] array) {
    this.array = array;
    setPreferredSize(new Dimension(700, 700)); // Set the size of the panel
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

    // Draw the grid representing the array
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int x = j * cellWidth;
        int y = i * cellHeight;

        // Draw the cell border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);

        if (array[i][j].getIndividual() != null) {
          g.setColor(new Color(array[i][j].getIndividual().getRGB()[0], array[i][j].getIndividual().getRGB()[1],
              array[i][j].getIndividual().getRGB()[1]));
          g.drawString(Map.individualRep, x + cellWidth / 2 - 4,
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
    repaint(); // Repaint the panel to reflect the changes
  }
}
