package Map;

import Individual.Individual;

public class Cell {
  int xCoord;
  int yCoord;

  double lightLevels;
  double foodLevels;
  Map map;

  Individual currentIndividual;

  public Cell(Map map, int xCoord, int yCoord) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;

    lightLevels = Math.random();
    foodLevels = Math.random();

    this.map = map;
  }

  synchronized public boolean setIndividual(Individual individual) {
    if (this.currentIndividual == null) {
      if (individual.getCell() != null)
        individual.getCell().removeIndividual();
      this.currentIndividual = individual;
      individual.setCell(this);
      map.getPanel().updateArray();
      return true;
    }

    return false;
  }

  synchronized public void removeIndividual() {
    this.currentIndividual = null;
  }

  public Individual getIndividual() {
    return this.currentIndividual;
  }

  public int getX() {
    return this.xCoord;
  }

  public int getY() {
    return this.yCoord;
  }
}
