package Individual;

public class Individual {
  String DNA = "";
  Brain brain;
  int xPosition = 0;
  int yPosition = 0;

  double health = 100;

  static String[] hexLetters = new String[] { "A", "B", "C", "D", "E", "F" };
  static String[] hexDigits = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

  static int brainSize = 3;

  public Individual() {
    for (int i = 0; i < 8 * brainSize; i++) {
      if (Math.random() < 0.5)
        DNA += hexDigits[(int) (Math.random() * 10)];
      else {
        DNA += hexLetters[(int) (Math.random() * 6)];
      }
    }

    brain = new Brain(DNA);
  }

  public String getDNA() {
    return DNA;
  }

  public int getXPosition() {
    return xPosition;
  }

  public int getYPosition() {
    return yPosition;
  }

  public void setXPosition(int xPosition) {
    this.xPosition = xPosition;
  }

  public void setYPosition(int yPosition) {
    this.yPosition = yPosition;
  }

  public double getHealth() {
    return health;
  }
}
