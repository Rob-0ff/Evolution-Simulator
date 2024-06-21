package Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import Individual.Individual;

public class Map {
  int xSize = 100;
  int ySize = 100;

  public char individualRep = 'o';

  char[][] board = new char[xSize][ySize];

  ArrayList<Individual> individuals = new ArrayList<Individual>();
  // Create the panel to visualize the array
  JPanel panel = new Array2DPanel(board);

  public Map() {

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        board[i][j] = ' ';
      }
    }

    JDialog dialog = new JDialog();
    dialog.setTitle("2D Array Visualization");
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setBackground(new Color(0, 0, 0));
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
    individuals.add(individual);

    board[individual.getXPosition()][individual.getYPosition()] = individualRep;

    individual.setMap(this);

    ((Array2DPanel) panel).updateArray();
  }

  public void update(int oldXPos, int oldYPos, Individual individual) {
    board[oldXPos][oldYPos] = ' ';

    board[individual.getXPosition()][individual.getYPosition()] = individualRep;

    ((Array2DPanel) panel).updateArray();
  }

  public char[][] getBoard() {
    return board;
  }

  public void clearIndividuals() {
    board = new char[xSize][ySize];
    individuals.clear();
    ((Array2DPanel) panel).clearArray(board);
  }

  public ArrayList<Individual> getIndividuals() {
    return this.individuals;
  }
}

class Array2DPanel extends JPanel {
  private char[][] array;

  public Array2DPanel(char[][] array) {
    this.array = array;
    setPreferredSize(new Dimension(800, 800)); // Set the size of the panel
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    drawArray(g, array);
  }

  private void drawArray(Graphics g, char[][] array) {
    int panelWidth = getWidth();
    int panelHeight = getHeight();
    int rows = array.length;
    int cols = array[0].length;
    int cellWidth = panelWidth / cols;
    int cellHeight = panelHeight / rows;

    // Draw the grid representing the array
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        int x = j * cellWidth;
        int y = i * cellHeight;

        // Draw the cell border
        g.setColor(Color.WHITE);
        g.drawRect(x, y, cellWidth, cellHeight);

        // Draw the character inside the cell
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(array[i][j]), x + cellWidth / 2 - 4, y + cellHeight / 2 + 4);
      }
    }
  }

  public void clearArray(char[][] newArray) {
    array = newArray;
    updateArray();
  }

  public void updateArray() {
    repaint(); // Repaint the panel to reflect the changes
  }
}
