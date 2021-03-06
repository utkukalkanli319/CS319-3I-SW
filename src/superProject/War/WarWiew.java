package superProject.War;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import superProject.City.CityManager;
import superProject.GameMain.GameAreaView;
import superProject.GameProperties.Card;
import superProject.Player.Player;
import superProject.City.City;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.control.*;
import javafx.scene.layout.*;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Label;
import javafx.stage.Stage;
import superProject.Player.PlayerEngine;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import static javafx.stage.Screen.getPrimary;

public class WarWiew  /*implements Initializable */ extends Application {

    @FXML
    private Label leftResLabel;
    @FXML
    private Label rightResLabel;
    @FXML
    private Button xoxButton1;
    @FXML
    private Button xoxButton2;
    @FXML
    private Button returnGameButton;
    @FXML
    private Rectangle2D primaryScreenBounds;
    @FXML
    private  AnchorPane smallPane1;
    @FXML
    private  AnchorPane smallPane2;

    @FXML
    private Button button00;
    @FXML
    private Button button01;
    @FXML
    private Button button02;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button20;
    @FXML
    private Button button21;
    @FXML
    private Button button22;

    @FXML
    private Label resultLabel;

    @FXML
    private Button returnButton;

    @FXML
    private Label turnLabel;

    @FXML
    private ImageView leftNeighbourImageView, rightNeighbourImageView, yourCityImage1, yourCityImage2;

    private boolean isXOXwinner; //for only xox

    private PlayerEngine playerEngine;

    private ArrayList<Card> cardsOnHandImageViewTranferList;

    private int curAgeNo;

    private int whichXOX; //in case we have equality in both

    private CityManager cityManager;
    Image[] botCities;
    ImageView chosenCityImage;
    boolean isAgeFinished;


    @FXML
    private double prefHeightSmall;
    @FXML
    private double prefWidthSmall;

    private int leftWarWinner; //winner of the left side war
    private int rightWarWinner; //winner of the right side war (true means this current player is winner)

    private Player leftNeighbour;
    private Player rightNeighbour;
    private Player mainPlayer;

    @FXML
    Button[] buttons;

    private boolean isXOXover;

    public WarWiew(){
        //leftWarWinner = true;
        //rightWarWinner = true;
        buttons = new Button[9];
        isXOXover = true;
    }


    public ArrayList<Card> getCardsOnHandImageViewTranferList() {
        return cardsOnHandImageViewTranferList;
    }

    public void setCardsOnHandImageViewTranferList(ArrayList<Card> cardsOnHandImageViewTranferList) {
        this.cardsOnHandImageViewTranferList = cardsOnHandImageViewTranferList;
    }

    @Override
    public void start( Stage primaryStage) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../War/WarViewFX.fxml"));
            primaryStage.setTitle("War");

            primaryStage.setScene(new Scene(root));
            primaryStage.setFullScreen(true);

            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();
            primaryStage.show();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public WarWiew(Player mainPlayer, Player leftNeighbour, int leftWarWinner, Player rightNeighbour, int rightWarWinner){
        setLeftWarWinner(leftWarWinner);
        setRightWarWinner(rightWarWinner);
        setLeftNeighbour(leftNeighbour);
        setRightNeighbour(rightNeighbour);
        setMainPlayer(mainPlayer);
        buttons = new Button[9];
        isXOXover = true;
    }


    public PlayerEngine getPlayerEngine() {
        return playerEngine;
    }

    public void setPlayerEngine(PlayerEngine playerEngine) {
        this.playerEngine = playerEngine;
    }


    public void setLeftWarWinner(int leftWarWinner) {
        this.leftWarWinner = leftWarWinner;
    }

    public void setRightWarWinner(int rightWarWinner) {
        this.rightWarWinner = rightWarWinner;
    }

    public boolean isLeftWarWinner() {
        if( leftWarWinner > 0)
            return true;
        return false;
    }

    public boolean isRightWarWinner() {
        if(rightWarWinner > 0)
            return true;
        return false;
    }

    @FXML
    public void setLabels(){
        setLeftWarLabel();
        setRightWarLabel();
    }

    @FXML
    public void setCityImagesForWar(){
        String preCity = "@../../Images/images/wonders/";
        yourCityImage1.setImage(new Image(preCity + playerEngine.getHumanPlayer().getCity().getPhotoName()));
        yourCityImage2.setImage(new Image(preCity + playerEngine.getHumanPlayer().getCity().getPhotoName()));
        leftNeighbourImageView.setImage(new Image(preCity + playerEngine.getAllPlayers().get(playerEngine.getAllPlayers().size()-1)
                .getCity().getPhotoName()));
        rightNeighbourImageView.setImage(new Image(preCity + playerEngine.getAllPlayers().get(1).getCity().getPhotoName()));
    }

    @FXML
    public void setLeftWarLabel(){
        xoxButton1.setVisible(false);
        if( leftWarWinner > 0 )
            leftResLabel.setText("VICTORY");
        else if (leftWarWinner < 0)
            leftResLabel.setText("DEFEAT");
        else {
            xoxButton1.setVisible(true);
            leftResLabel.setText("EQUALITY");
        }
    }

    @FXML
    public void setRightWarLabel(){
        xoxButton2.setVisible(false);
        if( rightWarWinner > 0 )
            rightResLabel.setText("VICTORY");
        else if (rightWarWinner < 0)
            rightResLabel.setText("DEFEAT");
        else{
            xoxButton2.setVisible(true);
            rightResLabel.setText("EQUALITY");
        }
    }

    public Player getLeftNeighbour() {
        return playerEngine.getAllPlayers().get(playerEngine.getAllPlayers().size()-1);
    }

    public void setLeftNeighbour(Player leftNeighbour) {
        this.leftNeighbour = leftNeighbour;
    }

    public Player getRightNeighbour() {
     return playerEngine.getAllPlayers().get(1);
    }

    public void setRightNeighbour(Player rightNeighbour) {
        this.rightNeighbour = rightNeighbour;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    @FXML
    public void goToXOX_1(){
        Stage stage;
        Parent root;

        whichXOX = 1;

        try {
            //System.out.println("heyy");
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../War/XOX_FX.fxml"));

            root = loader.load();
            WarWiew secondController = loader.getController();
            secondController.setPlayerEngine(playerEngine);
            secondController.setCityManager(cityManager);
            secondController.setBotCities(botCities);
            secondController.setIsAgeFinished(isAgeFinished);
            secondController.setCurAgeNo(curAgeNo);
            secondController.setWhichXOX(1);
            secondController.setLeftWarWinner(0);
            secondController.setRightWarWinner(rightWarWinner);
            stage = (Stage) xoxButton1.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
           // stage.setFullScreen(true);
            stage.show();

        }
        catch (Exception e){
            System.out.println("aasd");

        }
    }

    @FXML
    public void goToXOX_2(){
        Stage stage;
        Parent root;

        whichXOX = 2;

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../War/XOX_FX.fxml"));
            root = loader.load();
            WarWiew secondController = loader.getController();
            secondController.setPlayerEngine(playerEngine);
            secondController.setCityManager(cityManager);
            secondController.setBotCities(botCities);
            secondController.setIsAgeFinished(isAgeFinished);
            secondController.setCurAgeNo(curAgeNo);
            secondController.setWhichXOX(2);
            secondController.setLeftWarWinner(leftWarWinner);
            secondController.setRightWarWinner(0);
            stage = (Stage) xoxButton2.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
           // stage.setFullScreen(true);
            stage.show();

        }
        catch (Exception e){

        }
    }

    private void createButtons(){
        isXOXover = false;
        buttons[0] = button00;
        buttons[1] = button01;
        buttons[2] = button02;
        buttons[3] = button10;
        buttons[4] = button11;
        buttons[5] = button12;
        buttons[6] = button20;
        buttons[7] = button21;
        buttons[8] = button22;
    }

    public void isGameFinished(){
        boolean areButtonsFull = true;
        boolean isCombo = false;
        for(int i = 0; i < 9; i++){
            if(!buttons[i].isDisabled())
                areButtonsFull = false;
        }

        Button[] combo = new Button[3];

        //horizontal
        for(int i = 0; i < 9; i = i + 3){
            if(!buttons[i].getText().equals("") && buttons[i].getText().equals(buttons[i+1].getText()) && buttons[i].getText().equals(buttons[i+2].getText())) {
                if (buttons[i].getText().equals("X"))
                    isXOXwinner = true;
                else
                    isXOXwinner = false;
                combo[0] = buttons[i];
                combo[1] = buttons[i+1];
                combo[2] = buttons[i+2];
                isXOXover = true;
                isCombo = true;
            }
        }

        if( !isXOXover) {

            //vertical
            for (int i = 0; i < 3; i++) {
                if (!buttons[i].getText().equals("") && buttons[i].getText().equals(buttons[i + 3].getText()) && buttons[i].getText().equals(buttons[i + 6].getText())) {
                    if (buttons[i].getText().equals("X"))
                        isXOXwinner = true;
                    else
                        isXOXwinner = false;
                    combo[0] = buttons[i];
                    combo[1] = buttons[i+3];
                    combo[2] = buttons[i+6];
                    isXOXover = true;
                    isCombo = true;
                }
            }

        }

        if(!isXOXover) {
            //diagonal
            if (!buttons[0].getText().equals("") && buttons[0].getText().equals(buttons[4].getText()) && buttons[0].getText().equals(buttons[8].getText())) {
                if (buttons[0].getText().equals("X"))
                    isXOXwinner = true;
                else
                    isXOXwinner = false;
                combo[0] = buttons[0];
                combo[1] = buttons[4];
                combo[2] = buttons[8];
                isXOXover = true;
                isCombo = true;
            }
            else if( !buttons[2].getText().equals("")&&  buttons[2].getText().equals(buttons[4].getText()) && buttons[4].getText().equals(buttons[6].getText()))
            {
                if (buttons[2].getText().equals("X"))
                    isXOXwinner = true;
                else
                    isXOXwinner = false;
                combo[0] = buttons[2];
                combo[1] = buttons[4];
                combo[2] = buttons[6];
                isXOXover = true;
                isCombo = true;
            }
        }



        if(isXOXwinner) {
            resultLabel.setText("YOU WON");
            /// burda coin artacak.
        }
        else {
            resultLabel.setText("YOU LOST");
        }

        if( areButtonsFull && !isCombo) {
            isXOXwinner = false; //that means player couldn't win against bot
            isXOXover = true;
            resultLabel.setVisible(true);
        }

        if( isXOXover ) {
            resultLabel.setVisible(true);
            turnLabel.setVisible(false);
            if( isCombo ) {
                playWinAnimation(combo);
            }
            returnButton.setDisable(false);
            for(int i = 0; i < 9; i ++){
                buttons[i].setDisable(true);
            }
        }


    }

    //TODO problem with play win animation
    public void playWinAnimation(Button[] combo){
        Line line = new Line();
        line.setStartX(combo[0].getLayoutX());
        line.setStartY(combo[0].getLayoutY());
        line.setEndX(combo[0].getLayoutX());
        line.setEndY(combo[0].getLayoutY());

        //root.getChildren().add(line);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),
                new KeyValue(line.endXProperty(), combo[2].getLayoutX()),
                new KeyValue(line.endYProperty(), combo[2].getLayoutY())));
        timeline.play();
    }

    @FXML
    public void returnToWarView(){
        //TODO, after xox
        //set results for human player after xox
        System.out.println("left war winner(3): " + leftWarWinner);
        System.out.println("right war winner(3): " + rightWarWinner);
        if( isXOXwinner ){
            //equality only in right war
            if(whichXOX == 1){
                leftWarWinner = 1;
            } else if( whichXOX == 2){
                rightWarWinner = 1;
            }
        } else{
            if(whichXOX == 1){
                leftWarWinner = -1;
            } else if( whichXOX == 2){
                rightWarWinner = -1;
            }
        }
        System.out.println("left war winner: " + leftWarWinner);
        System.out.println("right war winner: " + rightWarWinner);
        //change labels as Victory or Defeat and set invisivle the xox button
        Stage stage;
        Parent root;

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../War/WarViewFX.fxml"));
            root = loader.load();
            WarWiew secondController = loader.getController();
            secondController.setCityManager(this.cityManager);
            secondController.setPlayerEngine(playerEngine);
            secondController.setLabels();
            secondController.setCityImagesForWar();
            secondController.setBotCities(botCities);
            secondController.setIsAgeFinished(isAgeFinished);
            secondController.setCurAgeNo(curAgeNo);
            secondController.setLeftWarWinner(leftWarWinner);
            secondController.setRightWarWinner(rightWarWinner);
            if( leftWarWinner != 0 && rightWarWinner != 0) {
                secondController.setResults(0, this.leftWarWinner);
                secondController.setResults(0, this.rightWarWinner);
            }
            secondController.setLabels();
            //this should only be called once
            stage = (Stage) returnButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
           // stage.setFullScreen(true);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    public void returnToGameView(ActionEvent event) throws Exception{

        Stage stage;
        Parent root;

        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("../GameMain/GameAreaViewFX.fxml"));

            root=loader.load();

            GameAreaView secondController = loader.getController();
            int numberOfPeople = playerEngine.getAllPlayers().size();
            secondController.setNumberPlayer(numberOfPeople);
            secondController.setRound( 7 - playerEngine.getHumanPlayer().getCards().size());
            secondController.setCityManager(cityManager);
            secondController.setPlayerEngine(playerEngine);
            secondController.setBotCityImages(botCities);
            if( isAgeFinished ) {
                secondController.setRound(0);
                secondController.setInitialView(curAgeNo + 1, numberOfPeople);
            }
            else
                secondController.setInitialView( curAgeNo, numberOfPeople);
            secondController.disableCities();
            secondController.getCoinLabel().setText(String.valueOf(playerEngine.getHumanPlayer().getCoin()));
            secondController.getWarLabel().setText(String.valueOf(playerEngine.getHumanPlayer().getWarPoints()));

            stage = (Stage) returnGameButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void drawX(MouseEvent e) throws Exception{
        Button sourceButton = (Button) e.getSource();
        if(buttons[0] == null || buttons[0].equals(button00))
            createButtons();
        try {
            sourceButton.setText("X");
            sourceButton.setDisable(true);
        }
        catch (Exception f){
            f.printStackTrace();
        }

        isXOXwinner = true;
        isGameFinished();

        if( !isXOXover)
            drawO();

    }

    @FXML
    public void drawO(){
        int i = (int) (Math.random() * 9);

        while (!buttons[i].getText().equals("")) {
            System.out.println("hhh");
            i = (int) (Math.random() * 9);

        }

        PauseTransition pauseTransition = new PauseTransition();
        pauseTransition.setDuration(Duration.millis(600));
        pauseTransition.play();
        buttons[i].setText("O");
        buttons[i].setDisable(true);
        isXOXwinner = false;
        isGameFinished();


    }

    //attack manager methods
    public void fight() {
        System.out.println("war points of human " + playerEngine.getHumanPlayer().getWarPoints());
        int numberOfPlayers = playerEngine.getAllPlayers().size();
        setLeftNeighbour(playerEngine.getAllPlayers().get(numberOfPlayers-1));
        setRightNeighbour(playerEngine.getAllPlayers().get(1));
        for( int i = 0; i < numberOfPlayers; i++){
            if (i == 0) {
                int compareLeft = Integer.compare( playerEngine.getHumanPlayer().getWarPoints(),
                        playerEngine.getAllPlayers().get(numberOfPlayers-1).getWarPoints());
                int compareRight  = Integer.compare( playerEngine.getHumanPlayer().getWarPoints(),
                        playerEngine.getAllPlayers().get(1).getWarPoints());
                leftWarWinner = compareLeft;
                rightWarWinner = compareRight;
            } else if( i == numberOfPlayers - 1) {
                int compareLeft = Integer.compare( playerEngine.getAllPlayers().get(i).getWarPoints(),
                        playerEngine.getAllPlayers().get(i-1).getWarPoints());
                int compareRight  = Integer.compare( playerEngine.getAllPlayers().get(i).getWarPoints(),
                        playerEngine.getAllPlayers().get(0).getWarPoints());
                int leftXOXwinner;
                if(compareLeft == 0){
                    leftXOXwinner = (int) (Math.random());
                    if( leftXOXwinner == 0 )
                        compareLeft = -1;
                    else
                        compareLeft = 1;
                }
                int rightXOXwinner;
                if(compareRight == 0){
                    rightXOXwinner = (int) (Math.random());
                    if( rightXOXwinner == 0 )
                        compareRight = -1;
                    else
                        compareRight = 1;
                }
                setResults(i, compareLeft);
                setResults(i, compareRight);
            } else { //playerId is between edges
                int compareLeft = Integer.compare( playerEngine.getAllPlayers().get(i).getWarPoints(),
                        playerEngine.getAllPlayers().get(i-1).getWarPoints());
                int compareRight  = Integer.compare( playerEngine.getAllPlayers().get(i).getWarPoints(),
                        playerEngine.getAllPlayers().get(i+1).getWarPoints());
                //if equality, random winners
                int leftXOXwinner;
                if(compareLeft == 0){
                    leftXOXwinner = (int) (Math.random());
                    if( leftXOXwinner == 0 )
                        compareLeft = -1;
                    else
                        compareLeft = 1;
                }
                int rightXOXwinner;
                if(compareRight == 0){
                    rightXOXwinner = (int) (Math.random());
                    if( rightXOXwinner == 0 )
                        compareRight = -1;
                    else
                        compareRight = 1;
                }
                setResults(i, compareLeft);
                setResults(i, compareRight);
            }
        }

    }

    public void setResults(int playerId, int compareResult){
        int victoryPoints;
        int defeatPoints = 1;
        if(curAgeNo == 1){
            victoryPoints = 1;
        } else if(curAgeNo == 2){
            victoryPoints = 3;
        } else{
            victoryPoints = 5;
        }

        if( compareResult > 0) {
            if( playerEngine == null )
                System.out.println("null player engine");
            playerEngine.getAllPlayers().get(playerId).setScore(playerEngine.getAllPlayers().get(playerId).getScore() + victoryPoints);
        } else {
            playerEngine.getAllPlayers().get(playerId).setScore(playerEngine.getAllPlayers().get(playerId).getScore() - defeatPoints);
        }
    }

    public void setCurAgeNo(int curAgeNo) {
        this.curAgeNo = curAgeNo;
    }

    public void setWhichXOX(int whichXOX) {
        this.whichXOX = whichXOX;
    }

    public void setCityManager( CityManager cityManager){
        this.cityManager = cityManager;
    }

    public void setBotCities(Image[] botCities){
        this.botCities = botCities;
    }

    public void setIsAgeFinished(boolean isAgeFinished){
        this.isAgeFinished = isAgeFinished;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//public class LabelController
