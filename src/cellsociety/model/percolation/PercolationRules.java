package cellsociety.model.percolation;

import cellsociety.model.cell.State;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;

/**
 *
 */
public class PercolationRules extends Rules {

  /**
   * Default constructor
   */
  public PercolationRules() {
  }


  /**
   * judges the state of each cell using the rule of the specific model class
   *
   * @param statesOfAllCells starting states of all cells
   * @return updated states of all cells
   */
  @Override
  public State[][] judgeStateOfEachCell(State[][] statesOfAllCells) {
    return new State[0][];
  }

  /**
   * specifices the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  @Override
  public String getStartingPositionCellType() {
    return null;
  }

  @Override
  public ArrayList<String> getPossibleTypes() {
    return null;
  }

  @Override
  public ArrayList<String> getPossibleColors() {
    return null;
  }

}