package cellsociety.model.sugarscape;

import cellsociety.controller.grid.GridManager;
import cellsociety.model.rules.Rules;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NavigatingSugarScapeRules extends Rules {
  private final String FULL_SUGAR_COLOR = "orange";
  private final String LOW_SUGAR_COLOR  = "white";
  private final String AGENT_COLOR = "red";
  private final String EMPTY_COLOR = "black";
  private ArrayList<String> possibleTypes;
  private ArrayList<String> possibleColors;
  private String FULLPATCH = "fullpatch";
  private String LOWPATCH = "lowpatch";
  private String AGENT = "paper";
  private String EMPTY = "empty";
  private ArrayList<AgentState> agents;
  private ArrayList<PatchState> patches;
  private double THRESHHOLD;
  private Random random;

  public NavigatingSugarScapeRules(int numberOfAgents, int maximumSugar, int growBackSugar, int sugarMetabolism, int vision){
    initializeColorsAndTypes();
    initializePatches();
  }

  private void initializePatches() {
  }

  private void initializeAgents(int numberOfAgents) {

  }

  private void initializeColorsAndTypes() {
    possibleTypes = new ArrayList<>();
    possibleColors = new ArrayList<>();
    possibleTypes.add(EMPTY);
    possibleTypes.add(FULLPATCH);
    possibleTypes.add(LOWPATCH);
    possibleTypes.add(AGENT);
    possibleColors.add(EMPTY_COLOR);
    possibleColors.add(FULL_SUGAR_COLOR);
    possibleColors.add(LOW_SUGAR_COLOR);
    possibleColors.add(AGENT_COLOR);
  }
  /**
   * specifies the starting states of the cells according to the simulation rule
   *
   * @return type of cells
   */
  @Override
  public String getStartingPositionCellType() {
    return null;
  }

  /**
   * returns the possible types (e.g. agent x, agent y, empty)
   *
   * @return arraylist of possible types
   */
  @Override
  public ArrayList<String> getPossibleTypes() {
    return possibleTypes;
  }

  /**
   * Returns the possible colors for each type
   *
   * @return arraylist of colors
   */
  @Override
  public ArrayList<String> getPossibleColors() {
    return possibleColors;
  }

  @Override
  public void decideState(List<Integer> neighborsOfEachTypeAtCoordinate, List<int[][]> nextStates,
      int x, int y, GridManager gridManager) {

  }
}