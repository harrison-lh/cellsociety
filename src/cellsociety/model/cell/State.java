package cellsociety.model.cell;


import javafx.scene.paint.Paint;

/**
 * Purpose: stores all information about the state of a cell
 * Assumptions:
 *
 * @author Ji Yun Hyo
 */
public class State {

  private final int xCoord;
  private final int yCoord;
  public String type;
  public Paint color;
  public int numberOfMoves;
  public int energy;
  private int ENERGY = 10;

  /**
   * Basic constructor
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type the type of the player
   */
  public State(int xCoord, int yCoord, String type) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    numberOfMoves = 0;
    this.energy = ENERGY;
  }

  /**
   * Constructor with more variations other than number of moves
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type the type of the player (e.g. agent x, agent y)
   * @param colorString color of the state
   * @param numberOfMoves the current, cumulative number of moves
   */
  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.numberOfMoves = numberOfMoves;
    this.energy = ENERGY;
    color = Paint.valueOf(colorString);
  }

  /**
   * Constructor with more variations other than number of moves
   * @param xCoord xCoord associated with the state
   * @param yCoord yCoord associated with the state
   * @param type the type of the player (e.g. agent x, agent y)
   * @param colorString color of the state
   * @param numberOfMoves the current, cumulative number of moves
   * @param energy the current energy
   */
  public State(int xCoord, int yCoord, String type, String colorString, int numberOfMoves, int energy) {
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.type = type;
    this.energy = energy;
    this.numberOfMoves = numberOfMoves;
    color = Paint.valueOf(colorString);
  }

  /**
   * Sets the color of the state. Used whenever the type is updated.
   * @param colorName
   */
  public void setColor(String colorName) {
    color = Paint.valueOf(colorName);
  }

  /**
   * @return xCoord associated with the state
   */
  public int getxCoord() {
    return xCoord;
  }

  /**
   * @return yCoord associated with the sates
   */
  public int getyCoord() {
    return yCoord;
  }

}