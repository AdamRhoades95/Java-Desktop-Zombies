/*import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import java.io.FileInputStream;
import java.sql.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;




public class Main extends Application {
	Stage stage;
	Pane root;
	Rectangle background;
	private List<GObjects> bullets = new ArrayList<GObjects>();
	private List<GObjects> enemies = new ArrayList<GObjects>();
	private List<GObjects> collectibles = new ArrayList<GObjects>();
	private List<GObjects> buildings = new ArrayList<GObjects>();
	private GObjects player;
	private int dir = 1;
	Random rand = new Random();
	static Random rand1 = new Random();
	private int num1;
	private int num2;
	private int points = 0;
	private double foodP = 101;
	private double waterP = 101;
	private int bulletBag = 24;
	private Label score;
	private Label water;
	private Label food;
	private Label bulletCounter;
	private Label time;
	private double countTime = 0;
	Button playAgian, quit, leaderboard, submit;
	private double collectorSpeed = 0.01;
	private double bulletCrates = 0.01;
	private int hunger = 0; 
	private boolean buildBuilding = true;
	private boolean buildTop = true;
	private boolean movement = true;
	private boolean gameOver = true;
	private int screen = 2;
	private String initials;
	TextField init;
	private int gameMode = 0;
	private Parent createContent() throws InterruptedException {
		
		root = new Pane();
		root.setPrefSize(600, 600);
		
		player = new Player();
		player.setV(new Point2D(1,0));
		addGObjects(player,300,300);
		//placeBuildings(); can not do out of handle(long now)
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(gameOver&&screen==-1) {
					if (buildTop) {
						buildTop = false;
						initializeTop();
						placeBuildings();
						validBuildings();
					}
					countTime+=.5;
					dontMove();
					onUpdate();
					setLabel();
					hungerUpdate();
					fixObjectLayer();//leave at the end
				}
				else if(screen==0) {
					if(movement) {
						createGameOverContent();
						movement = false;
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						gameOver = true;
						screen = -1;
						Reset();
					}
					if(quit.isPressed()) {
						movement = true;
						screen = 2;
						createMainMenu();
					}
					if(submit.isPressed()){
						table.getItems().clear();
						table.getColumns().clear();
						movement = true;
						submitScore();
						
					}
				}
				else if(screen == 2) {
					if(movement) {
						root.getChildren().clear();
						movement = false;
						createMainMenu();
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						movement = true;
						screen = 3;
					}
					if(leaderboard.isPressed()){
						root.getChildren().clear();
						table.getItems().clear();
						table.getColumns().clear();
						movement = true;
						screen = 4;
					}
				}
				else if(screen == 3) {
					if(movement) {
						movement = false;
						createGameMode();
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						gameOver = true;
						//movement = true;
						gameMode = 2;
						screen = -1;
						Reset();
						
					}
					if(quit.isPressed()) {
						gameOver = true;
						//movement = true;
						screen = -1;
						gameMode = 1;
						root.getChildren().clear();
						Reset();
					}
					
				}
				else if(screen == 4){
					if(movement){
						movement = false;
						createLeaderboard();
					}
					if(quit.isPressed()){
						gameOver = true;
						screen = 2;
						root.getChildren().clear();
						createMainMenu();
					}
				}
			}
		};
		timer.start();
		return root;
	}
	public class HighScore{
		
		private String player;
		private int days;
		private int kills;
		
		
		public HighScore( String player, int days, int kills) {
			// TODO Auto-generated constructor stub
		}
		
		
		public String getPlayer() {
			return player;
		}
		public void setPlayer(String player) {
			this.player = player;
		}
		
		public int getDays() {
			return days;
		}
		public void setDays(int days) {
			this.days = days;
		}
		public int getKills() {
			return kills;
		}
		public void setKills(int kills) {
			this.kills = kills;
		}
		
		
		
	}
	private ObservableList<ObservableList> data = FXCollections.observableArrayList(
			);
	private TableView table = new TableView();
	private void createLeaderboard(){
		root.getChildren().clear();
		table.getItems().clear();
		try{
			
			String classs = "com.mysql.jdbc.Driver";
			
			String url = "jdbc:mysql://sql9.freemysqlhosting.net/sql9323163";
			String un = "sql9323163";
			String pw = "RN61lGnq5B";
			
			Connection connR = DriverManager.getConnection(url,un,pw);
			String query = "SELECT * FROM Leaderboard ORDER BY Days_Survived DESC, Kills DESC LIMIT 10";
			Statement st = connR.createStatement();
			ResultSet rs = st.executeQuery(query);
			
		    for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
 
                table.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
		   
			while(rs.next()){
				
				int place = rs.getInt("Place");
				String player = rs.getString("Player");
				int days = rs.getInt("Days_Survived");
				int kills = rs.getInt("Kills");
			    HighScore hs = new HighScore(player, days, kills);
			    ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                    
                }
                System.out.println("Row [1] added " + row);
                data.add(row);
			    table.setItems(data);
				//table.getItems().addAll(hs);
				System.out.format("%s, %s, %s, %s\n", place, player, days, kills);
			}
			
			
		    table.setEditable(false);

		    table.setPrefWidth(root.getWidth());
		    table.setPrefHeight(root.getHeight()/2);
			quit = new Button();
			playAgian = new Button();
			playAgian.setTranslateX(root.getWidth()/2-55);
			playAgian.setTranslateY(350);
			playAgian.setFont(new Font("Arial", 20));
			playAgian.setText("Level Select");
			quit.setTranslateX(root.getWidth()/2-50);
			quit.setTranslateY(400);
			quit.setFont(new Font("Arial", 20));
			quit.setText("Main Menu");
			root.getChildren().addAll(table, quit);
			st.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		
	}
	private void createGameMode() {
		root.getChildren().clear();
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-90);
		playAgian.setTranslateY(((int)root.getHeight())-60);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Urban");
		quit = new Button();
		quit.setTranslateX(((int)root.getWidth()/2)+10);
		quit.setTranslateY(((int)root.getHeight())-60);
		quit.setFont(new Font("Arial", 20));
		quit.setText("Rural");
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
    	br.setLayoutX(0);
    	br.setLayoutY(0);
    	br.setPrefSize(root.getWidth(), root.getHeight());
    
    Image image = new Image("background.png");
    	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT)));
	root.getChildren().addAll(background, br,playAgian,quit);
	}
	private void submitScore(){
		try{
				
				String classs = "com.mysql.jdbc.Driver";
				
				String url = "jdbc:mysql://sql9.freemysqlhosting.net/sql9323163";
				String un = "sql9323163";
				String pw = "RN61lGnq5B";
				
				Connection connR = DriverManager.getConnection(url,un,pw);
				String query = "INSERT INTO `Leaderboard` (Player,Days_Survived,Kills) VALUES ('"+initials+"', '"+countTime+"', '"+points+"')";
				Statement st = connR.createStatement();
				initials = init.getText();
				st.executeUpdate(query);
				screen = 4;
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
	}
	private void createGameOverContent() {
		root.getChildren().clear();		
		init = new TextField();
		init.setTranslateX(root.getWidth()/2-100);
		init.setTranslateY(410);
		init.setPromptText("Enter Initials");
		submit = new Button();
		submit.setFont(new Font("Arial", 20));
		submit.setTranslateY(400);
		submit.setTranslateX(root.getWidth()/2+50);
		submit.setText("Submit Score");
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-130);
		playAgian.setTranslateY(((int)root.getHeight())-50);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Play Agian");
		
		quit = new Button();
		quit.setTranslateX(((int)root.getWidth()/2)+10);
		quit.setTranslateY(((int)root.getHeight())-50);
		quit.setFont(new Font("Arial", 20));
		quit.setText("Back");
		
		if(countTime<24) {
			//hrs.
			time.setText("survived: "+String.valueOf((int)countTime)+" hrs.");
		}
		else if(countTime>24) {
			//days
			time.setText("survived: "+String.valueOf((int)countTime/24)+" days");
		}
		time.setTextFill(Color.RED);
		time.setText(time.getText()+"  kills: "+ points);
		time.setTranslateX(((int)root.getWidth()/2)-130);
		time.setTranslateY(((int)root.getHeight()-80));
		
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
        	br.setLayoutX(0);
        	br.setLayoutY(0);
        	br.setPrefSize(root.getWidth(), root.getHeight());
        
        Image image = new Image("background.png");
        	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
		root.getChildren().addAll(background, br,init,submit,time,playAgian,quit);
	}
	
	private void createMainMenu() {
		root.getChildren().clear();
		playAgian = new Button();
		leaderboard = new Button();
		leaderboard.setTranslateX(root.getWidth()/2-70);
		leaderboard.setTranslateY(((int)root.getHeight())-50);
		leaderboard.setFont(new Font("Arial", 20));
		leaderboard.setText("Leaderboard");
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-30);
		playAgian.setTranslateY(((int)root.getHeight())-100);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Play");
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
    	br.setLayoutX(0);
    	br.setLayoutY(0);
    	br.setPrefSize(root.getWidth(), root.getHeight());
    
    Image image = new Image("background.png");
    	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        BackgroundSize.DEFAULT)));
	root.getChildren().addAll(background, br,playAgian, leaderboard);
	}
	private void dontMove() {
		for(GObjects building: buildings) {
			if(player.iscollide(building)) {
				if((int)player.getX()> (int)building.getX()) {
					movement =false;
					dir = 0;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateX(player.getX()+2);
				}
				if(player.getY()> building.getY()) {
					movement = false;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateY(player.getY()+2);
				}
				if((int)player.getX()< (int)building.getX()) {
					movement =false;
					dir = 0;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateX(player.getX()-2);
				}
				if(player.getY()< building.getY()) {
					movement =false;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateY(player.getY()-2);
				}
			}	
		}
		for(GObjects building: buildings) {
			for(GObjects enemy: enemies) {
				if(enemy.iscollide(building)) {
					if((int)enemy.getX()> (int)building.getX()) {
						enemy.getObject().setTranslateX(enemy.getX()+2);
					}
					if(enemy.getY()> building.getY()) {
						enemy.getObject().setTranslateY(enemy.getY()+2);
					}
					if((int)enemy.getX()< (int)building.getX()) {
						enemy.getObject().setTranslateX(enemy.getX()-2);
					}
					if(enemy.getY()< building.getY()) {
						enemy.getObject().setTranslateY(enemy.getY()-2);
					}
				}	
			}
		}
		for(GObjects bullet: bullets) {
			for(GObjects building: buildings) {
				if(bullet.iscollide(building)) {
					bullet.setAlive(false);
					root.getChildren().removeAll(bullet.getObject());
				}
			}
		}
		if((int)player.getX()> root.getWidth()-20) {
			movement =false;
			dir = 0;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateX(player.getX()-2);
		}
		else if((int)player.getX()< 0) {
			movement =false;
			dir = 0;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateX(player.getX()+2);
		}
		if(player.getY()> root.getHeight()-20) {
			movement =false;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateY(player.getY()-2);
		}
		else if(player.getY()< 60) {
			movement =false;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateY(player.getY()+2);
		}
	}
	private void fixObjectLayer() {
		root.getChildren().clear();
		for(GObjects collectible: collectibles) {
			root.getChildren().addAll(collectible.getObject());
		}
		for(GObjects enemy: enemies) {
			root.getChildren().addAll(enemy.getObject());
		}
		for(GObjects bullet: bullets) {
			root.getChildren().addAll(bullet.getObject());
		}
		root.getChildren().addAll(player.getObject());
		for(GObjects building: buildings) {
			root.getChildren().addAll(building.getObject());
		}
		root.getChildren().addAll(background, time, score, water, food, bulletCounter);
	}
	private void validBuildings() {
		for(GObjects building: buildings) {
			if (building.iscollide(player)){
				building.getObject().setTranslateX(player.getX()+20);
			}
		}
	}
	private void placeBuildings() {
		if (buildBuilding) {
			
			newBuildings();
			newBuildings();
			newBuildings();
			newBuildings();
			newBuildings();
			if(gameMode == 2){
				newBuildings();
				newBuildings();
				newBuildings();
				newBuildings();
				newBuildings();
			}
			buildBuilding = false;
		}
	}
	private void newBuildings() {
		num1 = rand.nextInt((int) root.getWidth());
		num2 = rand.nextInt((int) root.getHeight());
		
		int x = rand.nextInt(80);
		int y = rand.nextInt(80);
		x+=10;
		y+=10;
		addBuilding(new Building(x, y),num1,num2);
	}
	private void setLabel() {
		score.setText("Kills: "+String.valueOf(points));
		water.setText("Water: "+String.valueOf((int)waterP));
		food.setText("Food: "+String.valueOf((int)foodP));
		bulletCounter.setText("Ammo: "+String.valueOf(bulletBag));
		setTime();
	}
	private void setTime() {
		if(countTime<24) {
			//hrs.
			time.setText("survived: "+String.valueOf((int)countTime)+" hrs.");
		}
		else if(countTime>24) {
			//days
			time.setText("survived: "+String.valueOf((int)countTime/24)+" days");
		}
	}
	private void hungerUpdate() {
		hunger++;
		if(hunger==3) {
			hunger = 0;
			foodP -= 0.03;
			waterP -= 0.03;
		}
		else {
			waterP -= 0.03;
		}
		if(waterP<=0||foodP<0) {
			gameOver = false;
			screen = 0;
			movement = true;
		}
	}
	private void initializeTop() {
		score = new Label();
		score.setFont(new Font("Arial", 20));
		score.setWrapText(true);
		score.setTranslateX(10);
		score.setTranslateY(5);
		points = 0;
		score.setText(String.valueOf(points));
		food = new Label();
		food.setFont(new Font("Arial", 20));
		food.setWrapText(true);
		food.setTranslateX(500);
		food.setTranslateY(30);
		food.setTextAlignment(TextAlignment.RIGHT);
		foodP = 101;
		food.setText(String.valueOf((int) foodP));
		water = new Label();
		water.setFont(new Font("Arial", 20));
		water.setWrapText(true);
		water.setTranslateX(390);
		water.setTranslateY(30);
		waterP = 101;
		water.setText(String.valueOf((int) waterP));
		bulletCounter = new Label();
		bulletCounter.setFont(new Font("Arial", 20));
		bulletCounter.setWrapText(true);
		bulletCounter.setTranslateX(450);
		bulletCounter.setTranslateY(5);
		bulletBag = 12;
		bulletCounter.setText(String.valueOf(bulletBag));
		time = new Label();
		time.setFont(new Font("Arial", 20));
		time.setWrapText(true);
		time.setTranslateX(10);
		time.setTranslateY(30);
		countTime = 0;
		setTime();
		background = new Rectangle((int)root.getWidth(),60,Color.LIGHTGREY);
		background.setTranslateX(0);
		root.getChildren().addAll(background, time, score, water, food, bulletCounter);
	}
	private void addBullet(GObjects bullet, double x, double y) {
        bullets.add(bullet);
        addGObjects(bullet, x, y);
    }
    private void addEnemy(GObjects enemy, double x, double y) {
    	enemy.setV(new Point2D(0,0));
    	enemies.add(enemy);
        addGObjects(enemy, x, y);
    }
    private void addCollectible(GObjects item, double x, double y) {
    	collectibles.add(item);
        addGObjects(item, x, y);
    }
    private void addBuilding(GObjects item, double x, double y) {
    	buildings.add(item);
        addGObjects(item, x, y+30);
    }
	private void addGObjects(GObjects object, double x, double y) {
		object.getObject().setTranslateX(x);
		object.getObject().setTranslateY(y);
		root.getChildren().add(object.getObject());
	}
	private void onUpdate() {
		for(GObjects bullet: bullets) {
			for(GObjects enemy: enemies) {
				if(bullet.iscollide(enemy)) {
					bullet.setAlive(false);
					enemy.setAlive(false);
					points++;
					root.getChildren().removeAll(bullet.getObject(),enemy.getObject());
				}
			}
		}
		bullets.removeIf(GObjects::isDead);
		enemies.removeIf(GObjects::isDead);
		bullets.forEach(GObjects::update);
		enemies.forEach(GObjects::update);
		
		for(GObjects collectible: collectibles) {
			if(collectible.iscollide(player)) {
				collectible.setAlive(false);
				if (collectible.getTag()==1) {
					waterP+=50;
					if (waterP> 100) {
						waterP=100;
					}
				}
				else if (collectible.getTag()==2) {
					bulletBag += 12;
				}
				else {
					foodP+=30;
					if (foodP>100) {
						foodP=100;
					}
				}
				root.getChildren().removeAll(collectible.getObject());
			}
		}
		collectibles.removeIf(GObjects::isDead);
		
		player.update();
		
		for(GObjects enemy: enemies) 
		{
			if(enemy.getX()> player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()> player.getX()&&enemy.getY()< player.getY()){
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,enemy.getV().getY()+.4));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()< player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,enemy.getV().getY()+.4));
			}
			else if(enemy.getX()> player.getX()&&enemy.getY()== player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,0));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()== player.getY()){
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,0));
			}
			else if(enemy.getX()== player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(0,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()== player.getX()&&enemy.getY()< player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(0,enemy.getV().getY()+.4));
			}
			if(enemy.iscollide(player)) {
				player.setAlive(false);
				//enemy.setAlive(false);
			}
		}
		if(player.isDead()) {
			gameOver = false;
			screen = 0;
			movement = true;
			

		}
		if (Math.random() < 0.02) {
			num1 = rand.nextInt(4);
			if(num1 == 0) {
				num2 = rand.nextInt((int) root.getHeight());
				addEnemy(new Enemy(), -20,num2);
			}
			else if(num1 == 1) {
				num2 = rand.nextInt((int) root.getHeight());
				addEnemy(new Enemy(), root.getWidth()+20,num2);
			}
			else if(num1 == 2) {
				num2 = rand.nextInt((int) root.getWidth());
				addEnemy(new Enemy(),num2, -20);
			}
			else if(num1 == 3) {
				num2 = rand.nextInt((int) root.getWidth());
				addEnemy(new Enemy(),num2, root.getHeight()+30);
			}
		}
		if (Math.random() < collectorSpeed) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(),num1, num2);
			if(collectorSpeed >= 0.001 ) {
				collectorSpeed = collectorSpeed - 0.0004;
			}
		}
		if (Math.random() < collectorSpeed) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(1),num1, num2);
			if(collectorSpeed >= 0.001 ) {
				collectorSpeed = collectorSpeed - 0.0004;
			}
		}
		if (Math.random() < bulletCrates) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(1.1),num1, num2);
			if(bulletCrates > 0 ) {
				bulletCrates = bulletCrates - 0.0004;
			}
		}
		
		
		
	}
	private void Reset() {
		if(gameMode==1) {
			root.getChildren().clear();
			enemies.clear();
			bullets.clear();
			collectibles.clear();
			buildings.clear();
			addGObjects(player,300,300);
			player.setAlive(true);
			initializeTop();
			collectorSpeed = 0.01;
			bulletCrates = .01;
			buildTop = true;
			buildBuilding = true;
			dontMove();
		}
		if(gameMode==2) {
			root.getChildren().clear();
			enemies.clear();
			bullets.clear();
			collectibles.clear();
			buildings.clear();
			addGObjects(player,300,300);
			player.setAlive(true);
			initializeTop();
			collectorSpeed = 0.02;
			bulletCrates = .02;
			buildTop = true;
			buildBuilding = true;
			dontMove();
		}
	
	}
	private static class Player extends GObjects {
		Player() {
			super(new Rectangle(20, 20, Color.BLUE));
		}
	}
	private static class Collectible extends GObjects {
		Collectible() {
			super(new Rectangle(10, 10, Color.GREEN));
			setTag(0);
		}
		Collectible(int x) {
			super(new Rectangle(10, 10, Color.CYAN));
			setTag(1);
		}
		Collectible(double x) {
			super(new Rectangle(10, 10, Color.CHOCOLATE));
			setTag(2);
		}
	}
	private static class Building extends GObjects {
		Building(int x, int y) {
			super(new Rectangle(x, y, Color.BLACK));
		}
	}
	private static class Enemy extends GObjects {
		Enemy() {
			super(new Circle(10, 10,10, Color.RED));
		}
	}
	private static class Bullet extends GObjects {
        Bullet() {
            super(new Circle(5, 5, 5, Color.BLACK));   
        }
    }
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
            	player.directLeft();
            	
            	if(movement) {
            		if( dir ==1) {
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
            		}
            		else if(dir >1) {
                		player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
                	}
            	}
            	movement = true;
            }
            if (e.getCode() == KeyCode.DOWN) {
            	dir = 0;
                player.setV(new Point2D(0,0));
                movement = true;
            }
            else if (e.getCode() == KeyCode.UP) {
            	if(dir >=1) {
            		
            		if(movement) {
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
            			dir++;
            		}
            	}
            	else {
            		if(movement) {
            			dir = 1;
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
            		}
            	}
            	movement = true;
            }
            else if (e.getCode() == KeyCode.RIGHT) {
                player.directRight();
                if(movement) {
                	if( dir ==1) {
        				player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
        			}
                	else if(dir >1) {
                		player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
            		}
                }
                movement = true;
            } 
            else if (e.getCode() == KeyCode.F) {
                if(bulletBag>0) {
                	Bullet bullet = new Bullet();
                	bullet.setV(player.getV().normalize().multiply(10));
                	if( dir ==0) {
        				player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
        				bullet.setV(player.getV().normalize().multiply(10));
        				player.setV(new Point2D(0,0));
        			}
                	addBullet(bullet, player.getObject().getTranslateX(), player.getObject().getTranslateY());
                	bulletBag--;
                }
                movement = true;
            }
        });
        stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
	 * 
	 * Stage stage;
	Pane root;
	Rectangle background;
	private List<GObjects> bullets = new ArrayList<>();
	private List<GObjects> enemies = new ArrayList<>();
	private List<GObjects> collectibles = new ArrayList<>();
	private List<GObjects> buildings = new ArrayList<>();
	private GObjects player;
	private int dir = 1;
	Random rand = new Random();
	static Random rand1 = new Random();
	private int num1;
	private int num2;
	private int points = 0;
	private double foodP = 101;
	private double waterP = 101;
	private int bulletBag = 24;
	private Label score;
	private Label water;
	private Label food;
	private Label bulletCounter;
	private Label time;
	private double countTime = 0;
	private Button playAgian, quit;
	private double collectorSpeed = 0.01;
	private double bulletCrates = 0.01;
	private int hunger = 0; 
	private boolean buildBuilding = true;
	private boolean buildTop = true;
	private boolean movement = true;
	private boolean gameOver = true;
	private int screen = 2;
	private int gameMode = 0;
	
	private Parent createContent() throws InterruptedException {
		root = new Pane();
		root.setPrefSize(600, 600);
		
		player = new Player();
		player.setV(new Point2D(1,0));
		addGObjects(player,300,300);
		//placeBuildings(); can not do out of handle(long now)
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(gameOver&&screen==-1) {
					if (buildTop) {
						buildTop = false;
						initializeTop();
						placeBuildings();
						
						validBuildings();
					}
					countTime+=.5;
					dontMove();
					onUpdate();
					setLabel();
					hungerUpdate();
					fixObjectLayer();//leave at the end
				}
				else if(screen==0) {
					if(movement) {
						createGameOverContent();
						movement = false;
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						gameOver = true;
						screen = -1;
						Reset();
					}
					if(quit.isPressed()) {
						movement = true;
						screen = 2;
						createMainMenu();
					}
				}
				if(screen == 2) {
					if(movement) {
						root.getChildren().clear();
						movement = false;
						createMainMenu();
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						movement = true;
						screen = 3;
					}
				}
				
				else if(screen == 3) {
					if(movement) {
						movement = false;
						createGameMode();
					}
					if(playAgian.isPressed()) {
						root.getChildren().clear();
						gameOver = true;
						gameMode = 2;
						//movement = true;
						screen = -1;
						Reset();
						
					}
					if(quit.isPressed()) {
						gameOver = true;
						//movement = true;
						screen = -1;
						gameMode = 1;
						root.getChildren().clear();
						Reset();
					}
				}
			}
		};
		timer.start();
		return root;
	}

	private void createGameMode() {
		root.getChildren().clear();
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-90);
		playAgian.setTranslateY(((int)root.getHeight())-60);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Urban");
		quit = new Button();
		quit.setTranslateX(((int)root.getWidth()/2)+10);
		quit.setTranslateY(((int)root.getHeight())-60);
		quit.setFont(new Font("Arial", 20));
		quit.setText("Rural");
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
        	br.setLayoutX(0);
        	br.setLayoutY(0);
        	br.setPrefSize(root.getWidth(), root.getHeight());
        
        Image image = new Image("background.png");
        	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
		root.getChildren().addAll(background, br,playAgian,quit);
	}
	private void createGameOverContent() {
		root.getChildren().clear();
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-130);
		playAgian.setTranslateY(((int)root.getHeight())-50);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Play Agian");
		
		quit = new Button();
		quit.setTranslateX(((int)root.getWidth()/2)+10);
		quit.setTranslateY(((int)root.getHeight())-50);
		quit.setFont(new Font("Arial", 20));
		quit.setText("Back");
		
		if(countTime<24) {
			//hrs.
			time.setText("survived: "+String.valueOf((int)countTime)+" hrs.");
		}
		else if(countTime>24) {
			//days
			time.setText("survived: "+String.valueOf((int)countTime/24)+" days");
		}
		time.setTextFill(Color.RED);
		time.setText(time.getText()+"  kills: "+ points);
		time.setTranslateX(((int)root.getWidth()/2)-130);
		time.setTranslateY(((int)root.getHeight()-80));
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
        	br.setLayoutX(0);
        	br.setLayoutY(0);
        	br.setPrefSize(root.getWidth(), root.getHeight());
        
        Image image = new Image("background.png");
        	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
		root.getChildren().addAll(background, br, time, playAgian,quit);
	}
	private void createMainMenu() {
		root.getChildren().clear();
		playAgian = new Button();
		playAgian.setTranslateX(((int)root.getWidth()/2)-30);
		playAgian.setTranslateY(((int)root.getHeight())-50);
		playAgian.setFont(new Font("Arial", 20));
		playAgian.setText("Play");
		background = new Rectangle(root.getWidth(), root.getHeight(), Color.BLACK);
		BorderPane br =new BorderPane();
        	br.setLayoutX(0);
        	br.setLayoutY(0);
        	br.setPrefSize(root.getWidth(), root.getHeight());
        
        Image image = new Image("background.png");
        	br.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT)));
		root.getChildren().addAll(background, br,playAgian);
	}
	private void dontMove() {
		for(GObjects building: buildings) {
			if(player.iscollide(building)) {
				if((int)player.getX()> (int)building.getX()) {
					movement =false;
					dir = 0;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateX(player.getX()+2);
				}
				if(player.getY()> building.getY()) {
					movement = false;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateY(player.getY()+2);
				}
				if((int)player.getX()< (int)building.getX()) {
					movement =false;
					dir = 0;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateX(player.getX()-2);
				}
				if(player.getY()< building.getY()) {
					movement =false;
					player.setV(new Point2D(0,0));
					player.getObject().setTranslateY(player.getY()-2);
				}
			}	
		}
		for(GObjects building: buildings) {
			for(GObjects enemy: enemies) {
				if(enemy.iscollide(building)) {
					if((int)enemy.getX()> (int)building.getX()) {
						enemy.getObject().setTranslateX(enemy.getX()+2);
					}
					if(enemy.getY()> building.getY()) {
						enemy.getObject().setTranslateY(enemy.getY()+2);
					}
					if((int)enemy.getX()< (int)building.getX()) {
						enemy.getObject().setTranslateX(enemy.getX()-2);
					}
					if(enemy.getY()< building.getY()) {
						enemy.getObject().setTranslateY(enemy.getY()-2);
					}
				}	
			}
		}
		for(GObjects bullet: bullets) {
			for(GObjects building: buildings) {
				if(bullet.iscollide(building)) {
					bullet.setAlive(false);
					root.getChildren().removeAll(bullet.getObject());
				}
			}
		}
		if((int)player.getX()> root.getWidth()-20) {
			movement =false;
			dir = 0;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateX(player.getX()-2);
		}
		else if((int)player.getX()< 0) {
			movement =false;
			dir = 0;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateX(player.getX()+2);
		}
		if(player.getY()> root.getHeight()-20) {
			movement =false;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateY(player.getY()-2);
		}
		else if(player.getY()< 60) {
			movement =false;
			player.setV(new Point2D(0,0));
			player.getObject().setTranslateY(player.getY()+2);
		}
	}
	private void fixObjectLayer() {
		root.getChildren().clear();
		for(GObjects collectible: collectibles) {
			root.getChildren().addAll(collectible.getObject());
		}
		for(GObjects enemy: enemies) {
			root.getChildren().addAll(enemy.getObject());
		}
		for(GObjects bullet: bullets) {
			root.getChildren().addAll(bullet.getObject());
		}
		root.getChildren().addAll(player.getObject());
		for(GObjects building: buildings) {
			root.getChildren().addAll(building.getObject());
		}
		root.getChildren().addAll(background, time, score, water, food, bulletCounter);
	}
	private void validBuildings() {
		for(GObjects building: buildings) {
			if (building.iscollide(player)){
				building.getObject().setTranslateX(player.getX()+20);
			}
		}
	}
	private void placeBuildings() {
		if (buildBuilding) {
			
			newBuildings();
			newBuildings();
			newBuildings();
			newBuildings();
			newBuildings();
			if(gameMode == 2){
				newBuildings();
				newBuildings();
				newBuildings();
				newBuildings();
				newBuildings();
			}
			buildBuilding = false;
		}
	}
	private void newBuildings() {
		num1 = rand.nextInt((int) root.getWidth());
		num2 = rand.nextInt((int) root.getHeight());
		
		int x = rand.nextInt(80);
		int y = rand.nextInt(80);
		x+=10;
		y+=10;
		addBuilding(new Building(x, y),num1,num2);
	}
	private void setLabel() {
		score.setText("Kills: "+String.valueOf(points));
		water.setText("Water: "+String.valueOf((int)waterP));
		food.setText("Food: "+String.valueOf((int)foodP));
		bulletCounter.setText("Ammo: "+String.valueOf(bulletBag));
		setTime();
	}
	private void setTime() {
		if(countTime<24) {
			//hrs.
			time.setText("survived: "+String.valueOf((int)countTime)+" hrs.");
		}
		else if(countTime>24) {
			//days
			time.setText("survived: "+String.valueOf((int)countTime/24)+" days");
		}
	}
	private void hungerUpdate() {
		hunger++;
		if(hunger==3) {
			hunger = 0;
			foodP -= 0.03;
			waterP -= 0.03;
		}
		else {
			waterP -= 0.03;
		}
		if(waterP<=0||foodP<0) {
			gameOver = false;
			screen = 0;
			movement = true;
		}
	}
	private void initializeTop() {
		score = new Label();
		score.setFont(new Font("Arial", 20));
		score.setWrapText(true);
		score.setTranslateX(10);
		score.setTranslateY(5);
		points = 0;
		score.setText(String.valueOf(points));
		food = new Label();
		food.setFont(new Font("Arial", 20));
		food.setWrapText(true);
		food.setTranslateX(500);
		food.setTranslateY(30);
		food.setTextAlignment(TextAlignment.RIGHT);
		foodP = 101;
		food.setText(String.valueOf((int) foodP));
		water = new Label();
		water.setFont(new Font("Arial", 20));
		water.setWrapText(true);
		water.setTranslateX(390);
		water.setTranslateY(30);
		waterP = 101;
		water.setText(String.valueOf((int) waterP));
		bulletCounter = new Label();
		bulletCounter.setFont(new Font("Arial", 20));
		bulletCounter.setWrapText(true);
		bulletCounter.setTranslateX(450);
		bulletCounter.setTranslateY(5);
		bulletBag = 12;
		bulletCounter.setText(String.valueOf(bulletBag));
		time = new Label();
		time.setFont(new Font("Arial", 20));
		time.setWrapText(true);
		time.setTranslateX(10);
		time.setTranslateY(30);
		countTime = 0;
		setTime();
		background = new Rectangle((int)root.getWidth(),60,Color.LIGHTGREY);
		background.setTranslateX(0);
		root.getChildren().addAll(background, time, score, water, food, bulletCounter);
	}
	private void addBullet(GObjects bullet, double x, double y) {
        bullets.add(bullet);
        addGObjects(bullet, x, y);
    }
    private void addEnemy(GObjects enemy, double x, double y) {
    	enemy.setV(new Point2D(0,0));
    	enemies.add(enemy);
        addGObjects(enemy, x, y);
    }
    private void addCollectible(GObjects item, double x, double y) {
    	collectibles.add(item);
        addGObjects(item, x, y);
    }
    private void addBuilding(GObjects item, double x, double y) {
    	buildings.add(item);
        addGObjects(item, x, y+30);
    }
	private void addGObjects(GObjects object, double x, double y) {
		object.getObject().setTranslateX(x);
		object.getObject().setTranslateY(y);
		root.getChildren().add(object.getObject());
	}
	private void onUpdate() {
		for(GObjects bullet: bullets) {
			for(GObjects enemy: enemies) {
				if(bullet.iscollide(enemy)) {
					bullet.setAlive(false);
					enemy.setAlive(false);
					points++;
					root.getChildren().removeAll(bullet.getObject(),enemy.getObject());
				}
			}
		}
		bullets.removeIf(GObjects::isDead);
		enemies.removeIf(GObjects::isDead);
		bullets.forEach(GObjects::update);
		enemies.forEach(GObjects::update);
		
		for(GObjects collectible: collectibles) {
			if(collectible.iscollide(player)) {
				collectible.setAlive(false);
				if (collectible.getTag()==1) {
					waterP+=50;
					if (waterP> 100) {
						waterP=100;
					}
				}
				else if (collectible.getTag()==2) {
					bulletBag += 12;
				}
				else {
					foodP+=30;
					if (foodP>100) {
						foodP=100;
					}
				}
				root.getChildren().removeAll(collectible.getObject());
			}
		}
		collectibles.removeIf(GObjects::isDead);
		
		player.update();
		
		for(GObjects enemy: enemies) 
		{
			if(enemy.getX()> player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()> player.getX()&&enemy.getY()< player.getY()){
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,enemy.getV().getY()+.4));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()< player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,enemy.getV().getY()+.4));
			}
			else if(enemy.getX()> player.getX()&&enemy.getY()== player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()-.4,0));
			}
			else if(enemy.getX()< player.getX()&&enemy.getY()== player.getY()){
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(enemy.getV().getX()+.4,0));
			}
			else if(enemy.getX()== player.getX()&&enemy.getY()> player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(0,enemy.getV().getY()-.4));
			}
			else if(enemy.getX()== player.getX()&&enemy.getY()< player.getY()) {
				enemy.setV(new Point2D(0,0));
				enemy.setV(new Point2D(0,enemy.getV().getY()+.4));
			}
			if(enemy.iscollide(player)) {
				player.setAlive(false);
				//enemy.setAlive(false);
			}
		}
		if(player.isDead()) {
			gameOver = false;
			screen = 0;
			movement = true;
			

		}
		if (Math.random() < 0.02) {
			num1 = rand.nextInt(4);
			if(num1 == 0) {
				num2 = rand.nextInt((int) root.getHeight());
				addEnemy(new Enemy(), -20,num2);
			}
			else if(num1 == 1) {
				num2 = rand.nextInt((int) root.getHeight());
				addEnemy(new Enemy(), root.getWidth()+20,num2);
			}
			else if(num1 == 2) {
				num2 = rand.nextInt((int) root.getWidth());
				addEnemy(new Enemy(),num2, -20);
			}
			else if(num1 == 3) {
				num2 = rand.nextInt((int) root.getWidth());
				addEnemy(new Enemy(),num2, root.getHeight()+30);
			}
		}
		if (Math.random() < collectorSpeed) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(),num1, num2);
			if(collectorSpeed >= 0.001 ) {
				collectorSpeed = collectorSpeed - 0.0004;
			}
		}
		if (Math.random() < collectorSpeed) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(1),num1, num2);
			if(collectorSpeed >= 0.001 ) {
				collectorSpeed = collectorSpeed - 0.0004;
			}
		}
		if (Math.random() < bulletCrates) {
			num1 = rand.nextInt((int) root.getWidth());
			num2 = rand.nextInt((int) root.getHeight());
			addCollectible(new Collectible(1.1),num1, num2);
			if(bulletCrates > 0 ) {
				bulletCrates = bulletCrates - 0.0004;
			}
		}
		
		
		
	}
	private void Reset() {
		if(gameMode==1) {
			root.getChildren().clear();
			enemies.clear();
			bullets.clear();
			collectibles.clear();
			buildings.clear();
			addGObjects(player,300,300);
			player.setAlive(true);
			initializeTop();
			collectorSpeed = 0.01;
			bulletCrates = .01;
			buildTop = true;
			buildBuilding = true;
			dontMove();
		}
		if(gameMode==2) {
			root.getChildren().clear();
			enemies.clear();
			bullets.clear();
			collectibles.clear();
			buildings.clear();
			addGObjects(player,300,300);
			player.setAlive(true);
			initializeTop();
			collectorSpeed = 0.02;
			bulletCrates = .02;
			buildTop = true;
			buildBuilding = true;
			dontMove();
		}
	}
	private static class Player extends GObjects {
		Player() {
			super(new Rectangle(20, 20, Color.BLUE));
		}
	}
	private static class Collectible extends GObjects {
		Collectible() {
			super(new Rectangle(10, 10, Color.GREEN));
			setTag(0);
		}
		Collectible(int x) {
			super(new Rectangle(10, 10, Color.CYAN));
			setTag(1);
		}
		Collectible(double x) {
			super(new Rectangle(10, 10, Color.CHOCOLATE));
			setTag(2);
		}
	}
	private static class Building extends GObjects {
		Building(int x, int y) {
			super(new Rectangle(x, y, Color.BLACK));
		}
	}
	private static class Enemy extends GObjects {
		Enemy() {
			super(new Circle(10, 10,10, Color.RED));
		}
	}
	private static class Bullet extends GObjects {
        Bullet() {
            super(new Circle(5, 5, 5, Color.BLACK));   
        }
    }
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
            	player.directLeft();
            	
            	if(movement) {
            		if( dir ==1) {
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
            		}
            		else if(dir >1) {
                		player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
                	}
            	}
            	movement = true;
            }
            if (e.getCode() == KeyCode.DOWN) {
            	dir = 0;
                player.setV(new Point2D(0,0));
                movement = true;
            }
            else if (e.getCode() == KeyCode.UP) {
            	if(dir >=1) {
            		
            		if(movement) {
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
            			dir++;
            		}
            	}
            	else {
            		if(movement) {
            			dir = 1;
            			player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
            		}
            	}
            	movement = true;
            }
            else if (e.getCode() == KeyCode.RIGHT) {
                player.directRight();
                if(movement) {
                	if( dir ==1) {
        				player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
        			}
                	else if(dir >1) {
                		player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect()))+Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))+Math.sin(Math.toRadians(player.getDirect()))));
            		}
                }
                movement = true;
            } 
            else if (e.getCode() == KeyCode.F) {
                if(bulletBag>0) {
                	Bullet bullet = new Bullet();
                	bullet.setV(player.getV().normalize().multiply(10));
                	if( dir ==0) {
        				player.setV(new Point2D(Math.cos(Math.toRadians(player.getDirect())), Math.sin(Math.toRadians(player.getDirect()))));
        				bullet.setV(player.getV().normalize().multiply(10));
        				player.setV(new Point2D(0,0));
        			}
                	addBullet(bullet, player.getObject().getTranslateX(), player.getObject().getTranslateY());
                	bulletBag--;
                }
                movement = true;
            }
        });
        stage.show();
       
        
	}
	public static void main(String[] args) {
		launch(args);
	}
}
*/

