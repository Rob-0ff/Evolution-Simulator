package Map;

import java.util.ArrayList;

import Individual.Individual;

public class Map {
  String[][] board = new String[10][10];

  ArrayList<Individual> individuals = new ArrayList<Individual>();

  public Map() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        board[i][j] = "[   ]";
      }
    }
  }

  public void printBoard() {
    for (int i = 0; i < this.board.length; i++) {
      for (int j = 0; j < this.board[0].length; j++) {
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }

  public void addIndividual(Individual individual) {
    individuals.add(individual);

    board[individual.getXPosition()][individual.getYPosition()] = "[ I ]";
  }
}
