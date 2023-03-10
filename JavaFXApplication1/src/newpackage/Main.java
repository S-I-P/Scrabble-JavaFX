package newpackage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class Main extends Application {
    public static Socket client;
    public static Stage primaryStage;
    @FXML
    public static BorderPane rootpane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Scrabble");
        blank();
        showPicture();
        
    }

    public void blank() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Blank.fxml"));
        rootpane = loader.load();
        Scene scene = new Scene(rootpane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showPicture() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("firstpage.fxml"));
        Pane root= loader.load();
        rootpane.setCenter(root);
    }
    

    public static void showLoginPage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("loginpage.fxml"));
        Pane root = loader.load();
        rootpane.setCenter(root);
        
    }
    
    public static void wrongusernm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("WrongUname.fxml"));
        AnchorPane root = loader.load();
        Stage newstage= new Stage();
        Scene scene= new Scene(root);
        newstage.setScene(scene);
        newstage.show();
    }
    
    public static void wrongpassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("WrongPassword.fxml"));
        AnchorPane root = loader.load();
        Stage newstage= new Stage();
        Scene scene= new Scene(root);
        newstage.setScene(scene);
        newstage.show();
    }
    
    public static void takenusernm() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("TakenUname.fxml"));
        AnchorPane root = loader.load();
        Stage newstage= new Stage();
        Scene scene= new Scene(root);
        newstage.setScene(scene);
        newstage.show();
    }
    
    
    public static void createnew() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("newloginpage.fxml"));
        Pane root = loader.load();
        rootpane.setCenter(root);
    }
    
    public static void newpassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("passwordnew.fxml"));
        AnchorPane root = loader.load();
        rootpane.setCenter(root);
    }
    
    public static void oldpassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("passwordoldaccount.fxml"));
        AnchorPane root = loader.load();
        rootpane.setCenter(root);
    }
    
    public static void incomingreqest(String st) {
        Stage stage= new Stage();
            GridPane gpane= new GridPane();
            gpane.setAlignment(Pos.CENTER);
            Label label= new Label(st+" wants to play with you.\nDo you want to play?");
            label.setTextFill(Color.CADETBLUE);
            Button ybtn= new Button("Yes");
            Button nbtn= new Button("No");
            gpane.add(label, 0, 0);
            gpane.add(ybtn, 0, 1);
            gpane.add(nbtn, 0, 2);
            Scene scene= new Scene(gpane,500, 400);
            stage.setScene(scene);
            stage.show();
    }
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        client= new Socket(InetAddress.getLocalHost(), 1030);
        launch(args);
        DataOutputStream dos= new DataOutputStream(client.getOutputStream());
        dos.writeUTF("BYE");
        client.close();
    }
}
