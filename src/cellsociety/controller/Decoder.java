package cellsociety.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

import com.sun.source.tree.EmptyStatementTree;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * This class handles XML file selection and initialization of parameters.
 *
 * @author Shaw Phillips
 */
public class Decoder {
  public static final String DATA_FILE_EXTENSION = "*.xml";
  public final static FileChooser FILE_CHOOSER = makeChooser(DATA_FILE_EXTENSION);
  public static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n <data game=\"game\">";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String DESC = "description";
  public static final String NUM_ROWS = "numRows";
  public static final String NUM_COLS = "numCols";
  public static final String MODEL = "model";
  public static final String TEMPLATE = "template";
  public static final String COORDINATES = "shapeCoords";
  public static final String FISH_SHARK_RATIO = "fishsharkratio";
  public static final String FISH_RATE = "fishreproduce";
  public static final String SHARK_RATE = "sharkreproduce";
  public static final String SHARK_LIVES = "sharklives";
  public static final String ENERGY = "energyfromeating";
  public static final String WATER_EMPTY_RATIO = "watertoemptyratio";
  public static final String BLOCK_RATIO = "blockratio";
  public static final String EMPTY_RATIO = "emptyratio";
  public static final String POPULATION_RATIO = "populationratio";
  public static final String SATISFACTION_THRESHOLD = "satisfactionthreshold";
  public static final String PROB_OF_CATCH = "probsOfCatch";
  public static final String TREE_RATIO = "treeratio";
  public static final String SEED = "randomseed";
  public static final String ROCK_RATIO = "rockratio";
  public static final String SCISSORS_RATIO = "scissorsratio";
  public static final String THRESHOLD = "threshhold";
  public static final List<String> MODEL_TYPES = List.of("spreadingoffire", "navigatingsugarscape", "segregationmodel", "percolation", "wator", "gameOfLife", "rockpaperscissors", "foragingants", "langton", "sugarscape");
  public static final String GOLDefaultShape = "25,25,25,26,25,27";
  public static final String FireDefaultShape = "21,20,20,21,21,21,22,21";
  public static final String BLOCK_COLOR = "blockcolor";
  public static final String EMPTY_COLOR = "emptycolor";
  public static final String WATER_COLOR = "watercolor";
  public static final String SHAPE = "shape";
  public static final String SIDES = "numberofsides";
  public static final String COLOR = "color";
  //             Sample Text Names               //
  public static final String RATIO1 = "ratio1";
  public static final String RATIO2 = "ratio2";
  public static final String RATIO3 = "ratio3";
  public static final String RATIO4 = "ratio4";
  public static final String INTEGER1 = "integer1";
  public static final String INTEGER2 = "integer2";
  public static final String INTEGER3 = "integer3";
  public static final String INTEGER4 = "integer4";
  public static final String STRING1 = "string1";
  public static final String STRING2 = "string2";

  private int radius;
  private String shape;
  private int numberOfSides;
  private ArrayList<String> coordinates;
  private String template;
  private String blockColor;
  private String emptyColor;
  private String waterColor;
  private String colorX;
  private String colorY;
  private String fireColor;
  private String treeColor;
  private String fishColor;
  private String sharkColor;
  private String rockColor;
  private String paperColor;
  private String scissorsColor;
  private String aliveColor;
  private String deadColor;
  private String nestColor;
  private String antColor;
  private String phermoneColor;
  private String foodColor;
  private String weakPhermoneColor;
  private int seed;
  private int fishRate;
  private int sharkRate;
  private int sharkLives;
  private int energy;
  private float fishSharkRatio;
  private float emptyRatio;
  private float waterToEmptyRatio;
  private float blockRatio;
  private float populationRatio;
  private float satisfactionThreshold;
  private float treeRatio;
  private float probsOfCatch;
  private float rockRatio;
  private float scissorsRatio;
  private int threshold;
  // Sample Ratios, Strings, and Integers //
  private float patchRatio;
  private float moveBias;
  private float ratio3;
  private float ratio4;
  private int phermoneAmount;
  private int numAgents;
  private int maxSugar;
  private int growBackSugar;
  private int metabolism;
  private int vision;
  private String fullSugarColor;
  private String lowSugarColor;
  private String agentColor;
  private String description;
  private String model;
  private String title;
  private String author;
  private int numRows;
  private int numColumns;
  private int numberOfAnts;

  private static FileChooser makeChooser(String extension) {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(System.getProperty("user.dir")));
    result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extension));
    return result;
  }
  /**
   *  Open window to choose XML file, initialize parser, parse universal values, and determine the model
   */
  public void readValuesFromXMLFile() {
    File dataFile = FILE_CHOOSER.showOpenDialog(null);
    XMLParser parser = new XMLParser("game");
    Map<String, String> attributes = parser.getAttribute(dataFile);
    try{
      description = attributes.get(DESC);
      title = attributes.get(TITLE);
      author = attributes.get(AUTHOR);
      numRows = Integer.parseInt(attributes.get(NUM_ROWS));
      numColumns = Integer.parseInt(attributes.get(NUM_COLS));
      model = attributes.get(MODEL);
      shape = attributes.get("shape");
      numberOfSides = Integer.parseInt(attributes.get(SIDES));
    }
    catch(Exception e){
      Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Parameter(s)");
      alert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> alert.close());
    }
    if(model == null || !MODEL_TYPES.contains(model)){
      Alert modelAlert = new Alert(Alert.AlertType.ERROR, "Invalid Model");
      modelAlert.showAndWait()
              .filter(response -> response == ButtonType.OK)
              .ifPresent(response -> modelAlert.close());
    }
    switch (model) {
      case "gameOfLife" -> initializeGOL(attributes);
      case "wator" -> initializeWaTor(attributes);
      case "segregationmodel" -> initializeSegregation(attributes);
      case "spreadingoffire" -> initializeFire(attributes);
      case "percolation" -> initializePercolation(attributes);
      case "rockpaperscissors" -> initializeRPS(attributes);
      case "foragingants" -> initializeForagingAnts(attributes);
      case "langton" -> initializeLangton(attributes);
      case "sugarscape" -> initializeSugarScape(attributes);
    }
  }
  public void saveConfig(List<String> stateOfSimulation) throws FileNotFoundException {
    Map<String, String> savedConfig = new HashMap<>();

    FileChooser chooser = new FileChooser();
    chooser.setTitle("Choose Directory");
    chooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("text file ", DATA_FILE_EXTENSION));
    chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
    File file = chooser.showSaveDialog(null);
    createConfigFile(file, savedConfig);
  }
  private void createConfigFile(File file, Map<String, String> attributes) throws FileNotFoundException {
    PrintWriter writer = new PrintWriter(file);
    writer.println(HEAD);
    for(String s : attributes.keySet()){
      if(attributes.get(s) == null) continue;
      writer.print("<" + s + ">");
      writer.print(attributes.get(s));
      writer.println("</" + s + ">");
    }
    writer.println("</data>");
    writer.close();
  }
  private void initializeGOL(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    template = attributes.get(TEMPLATE);
    aliveColor = attributes.get("alivecolor").equals("") ? "black" : attributes.get("alivecolor");
    deadColor = attributes.get("deadcolor").equals("") ? "lightgrey" : attributes.get("deadcolor");
  }
  private void initializeWaTor(Map<String, String> attributes){
    fishSharkRatio = Float.parseFloat(attributes.getOrDefault(FISH_SHARK_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "0"));
    fishRate = Integer.parseInt(attributes.getOrDefault(FISH_RATE, "5"));
    sharkRate = Integer.parseInt(attributes.getOrDefault(SHARK_RATE, "5"));
    sharkLives = Integer.parseInt(attributes.getOrDefault(SHARK_LIVES, "3"));
    energy = Integer.parseInt(attributes.getOrDefault(ENERGY, "4"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    fishColor = attributes.get("fishcolor").equals("") ? "green" : attributes.get("fishcolor");
    sharkColor = attributes.get("sharkcolor").equals("") ? "blue" : attributes.get("sharkcolor");
  }
  private void initializePercolation(Map<String, String> attributes){
    waterToEmptyRatio = Float.parseFloat(attributes.getOrDefault(WATER_EMPTY_RATIO, ".01"));
    blockRatio = Float.parseFloat(attributes.getOrDefault(BLOCK_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    template = attributes.getOrDefault(TEMPLATE, "random_one");
    blockColor = attributes.get(BLOCK_COLOR).equals("") ? "black" : attributes.get(BLOCK_COLOR);
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    waterColor = attributes.get(WATER_COLOR).equals("") ? "blue" : attributes.get(WATER_COLOR);
  }
  private void initializeSegregation(Map<String, String> attributes){
    populationRatio = Float.parseFloat(attributes.getOrDefault(POPULATION_RATIO, "0.5"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    satisfactionThreshold = Float.parseFloat(attributes.getOrDefault(SATISFACTION_THRESHOLD, "0.6"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    template = attributes.getOrDefault(TEMPLATE, "random_one");
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "lightgrey" : attributes.get(EMPTY_COLOR);
    colorX = attributes.get("agentxcolor").equals("") ? "red" : attributes.get("agentxcolor");
    colorY = attributes.get("agentycolor").equals("") ? "blue" : attributes.get("agentycolor");
  }
  private void initializeFire(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, FireDefaultShape).split("[,]", 0)));
    template = attributes.getOrDefault(TEMPLATE, "basicfire");
    probsOfCatch = Float.parseFloat(attributes.getOrDefault(PROB_OF_CATCH, "0.3"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0.1"));
    treeRatio = Float.parseFloat(attributes.getOrDefault(TREE_RATIO, "0.8"));
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "yellow" : attributes.get(EMPTY_COLOR);
    treeColor = attributes.get("treecolor").equals("") ? "green" : attributes.get("treecolor");
    fireColor = attributes.get("firecolor").equals("") ? "red" : attributes.get("firecolor");
  }
  private void initializeRPS(Map<String, String> attributes){
    rockRatio = Float.parseFloat(attributes.getOrDefault(ROCK_RATIO, "0.33"));
    scissorsRatio = Float.parseFloat(attributes.getOrDefault(SCISSORS_RATIO, "0.5"));
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "100"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0"));
    threshold = Integer.parseInt(attributes.getOrDefault(THRESHOLD, "3"));
//    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
//    paperColor = attributes.get("papercolor").equals("") ? "blue" : attributes.get("papercolor");
//    rockColor = attributes.get("rockcolor").equals("") ? "red" : attributes.get("rockcolor");
//    scissorsColor = attributes.get("scissorscolor").equals("") ? "lightgrey" : attributes.get("scissorscolor");
  }
  private void initializeForagingAnts(Map<String, String> attributes){
    coordinates = new ArrayList<>(Arrays.asList(attributes.getOrDefault(COORDINATES, GOLDefaultShape).split("[,]", 0)));
    moveBias = Float.parseFloat(attributes.getOrDefault("movebias", "0.96"));
    phermoneAmount = Integer.parseInt(attributes.getOrDefault("phermoneamount", "30"));
    radius = Integer.parseInt(attributes.getOrDefault("radius", "5"));
    numberOfAnts = Integer.parseInt(attributes.getOrDefault("numberofants", "50"));
    numberOfSides = Integer.parseInt(attributes.getOrDefault("numberofsides", "4"));
    nestColor = attributes.get("nestcolor").equals("") ? "green" : attributes.get("nestcolor");
    antColor = attributes.get("antcolor").equals("") ? "red" : attributes.get("antcolor");
    phermoneColor = attributes.get("phermonecolor").equals("") ? "blue" : attributes.get("phermonecolor");
    foodColor = attributes.get("foodcolor").equals("") ? "lightgrey" : attributes.get("foodcolor");
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
    weakPhermoneColor = attributes.get("weakphermonecolor").equals("") ? "skyblue" : attributes.get("weakphermonecolor");
  }
  private void initializeLangton(Map<String, String> attributes){
    //ratio1 = Float.parseFloat(attributes.getOrDefault("ratio1", "defaultratio1");
    //ratio2 = Float.parseFloat(attributes.getOrDefault("ratio2", "defaultratio2);
    //integer1 = Integer.parseInt(attributes.getOrDefault("integer1", "defaultinteger1");
    //integer2 = Integer.parseInt(attributes.getOrDefault("integer2", :defaultinteger2");
    //string1 = attributes.get("string1");
    //string2 = attributes.get("string2");
  }
  private void initializeSugarScape(Map<String, String> attributes){
    seed = Integer.parseInt(attributes.getOrDefault(SEED, "10"));
    emptyRatio = Float.parseFloat(attributes.getOrDefault(EMPTY_RATIO, "0"));
    patchRatio = Float.parseFloat(attributes.getOrDefault("patchratio", "0.7"));
    numAgents = Integer.parseInt(attributes.getOrDefault("numberofagents", "30"));
    maxSugar = Integer.parseInt(attributes.getOrDefault("maxsugar", "5"));
    growBackSugar = Integer.parseInt(attributes.getOrDefault("growbacksugar", "1"));
    metabolism = Integer.parseInt(attributes.getOrDefault("metabolism", "2"));
    vision = Integer.parseInt(attributes.getOrDefault("vision", "2"));
    fullSugarColor = attributes.get("fullsugarcolor").equals("") ? "orange" : attributes.get("fullsugarcolor");
    lowSugarColor = attributes.get("lowsugarcolor").equals("") ? "white" : attributes.get("lowsugarcolor");
    agentColor = attributes.get("agentcolor").equals("") ? "red" : attributes.get("agentcolor");
    emptyColor = attributes.get(EMPTY_COLOR).equals("") ? "black" : attributes.get(EMPTY_COLOR);
  }
  public ArrayList<String> getCoordinates(){ return coordinates;}
  public int getSeed() {return seed;}
  public float getBlockRatio(){return blockRatio;}
  public float getWaterToEmptyRatio(){return waterToEmptyRatio;}
  public float getSatisfactionThreshold(){return satisfactionThreshold;}
  public float getEmptyRatio(){return emptyRatio;}
  public float getPopulationRatio(){return populationRatio;}
  public float getProbsOfCatch(){return probsOfCatch;}
  public float getTreeRatio(){return treeRatio;}
  public int getEnergy(){return energy;}
  public int getFishRate(){return fishRate;}
  public int getSharkLives(){return sharkLives;}
  public float getFishSharkRatio(){return fishSharkRatio;}
  public float getRockRatio(){return rockRatio;}
  public float getScissorsRatio(){return scissorsRatio;}
  public int getThreshold(){return threshold;}
  public String getBlockColor(){return blockColor;}
  public String getEmptyColor(){return emptyColor;}
  public String getWaterColor(){return waterColor;}
  public String getColorX(){return colorX;}
  public String getColorY(){return colorY;}
  public String getFireColor(){return fireColor;}
  public String getTreeColor(){return treeColor;}
  public String getFishColor(){return fishColor;}
  public String getSharkColor(){return sharkColor;}
  public String getRockColor(){return rockColor;}
  public String getScissorsColor(){return scissorsColor;}
  public String getPaperColor(){return paperColor;}
  public String getAliveColor(){return aliveColor;}
  public String getDeadColor(){return deadColor;}
  public String getNestColor(){return nestColor;}
  public String getAntColor(){return antColor;}
  public String getPhermoneColor(){return phermoneColor;}
  public String getWeakPhermoneColor(){return weakPhermoneColor;}
  public String getFoodColor(){return foodColor;}
  public int getNumberOfSides(){return numberOfSides;}
  public String getShape(){return shape;}
  //            generic getters           //
  public float getPatchRatio(){return patchRatio;}
  //public float getRatio2(){return ratio2;}
  //public float getRatio3(){return ratio3;}
  //public float getRatio4(){return ratio4;}
  public int getRadius(){return radius;}
  public int getNumberOfAnts(){return numberOfAnts;}
  public int getNumAgents(){return numAgents;}
  public int getMaxSugar(){return maxSugar;}
  public int getMetabolism(){return metabolism;}
  public int getVision(){return vision;}
  public int getGrowBackSugar(){return growBackSugar;}
  public int getPhermoneAmount(){return phermoneAmount;}
  public float getMoveBias(){return moveBias;}
  public String getFullSugarColor(){return fullSugarColor;}
  public String getLowSugarColor(){return lowSugarColor;}
  public String getAgentColor(){return agentColor;}

  /**
   * @return model name
   */
  public String getModel() {
    return model;
  }
  /**
   * @return title
   */
  public String getTitle() {
    return title;
  }
  /**
   * @return author
   */
  public String getAuthor() {
    return author;
  }
  /**
   * @return number of rows
   */
  public int getRows() {
    return numRows;
  }
  /**
   * @return number of columns
   */
  public int getCols() {
    return numColumns;
  }
  /**
   * @return description
   */
  public String getMyDesc() {
    return description;
  }
}
