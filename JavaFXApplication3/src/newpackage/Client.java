/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import static newpackage.Server.s;


public class Client extends Application{
    static Socket s;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    static boolean moveflag= true; 
    public static Stage primaryStage;
    public LinkedList<String> list= new LinkedList<String>();
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button press= new Button("Done");
            this.primaryStage= primaryStage;
            oos= new ObjectOutputStream(s.getOutputStream());
            ois= new ObjectInputStream(s.getInputStream());
            info inf= (info) ois.readObject();
            oos.writeObject("GOT");
            boolean fmove= true;
            GridPane gpane= new GridPane();
            gpane.setGridLinesVisible(true);
            BorderPane root= new BorderPane(gpane);
            root.setTop(new Label("Game"));
            AnchorPane apn= FXMLLoader.load(getClass().getResource("Buttoncont_1.fxml"));
            root.setRight(apn);
            Label [][]glbls= new Label[15][15];
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){

                    glbls[i][j]=new Label();
                    glbls[i][j].setAlignment(Pos.CENTER);

                    glbls[i][j].setPrefSize(90, 60);
                    gpane.add(glbls[i][j], j, i);
                    if((i==0 && j==0) || (i==0 && j==7) || (i==0 && j==14) || (i==7 && j==0) || (i==7 && j==14) || (i==14 && j==0) || (i==14 && j==7) || (i==14 && j==14) ){
                        glbls[i][j].setTextFill(Color.BLUE);
                        glbls[i][j].setText("TW");
                    }
                    else if( ((i>0 && i<5) || (i>9 && i<14)) && (j==i || j== (14-i) ) ){
                        glbls[i][j].setTextFill(Color.DARKGREEN);
                        glbls[i][j].setText("DW");
                    }

                    else if( ( (i==1 || i==5 || i==9 || i==13)  && (j==5 || j== 9) )  || ( (i==5 || i==9) && (j==1 || j==13 ) ) ){
                        glbls[i][j].setTextFill(Color.CRIMSON);
                        glbls[i][j].setText("TL");
                    }
                    else if( (( i==2 || i==6 || i==8 || i==12) && ( j==2 || j==6 || j==8 || j==12 )) || ( (i==0 || i==7 || i==14) && (j==3 || j==11) ) || ( (j==0 || j==14) && (i==3 || i==11) ) ){
                        glbls[i][j].setTextFill(Color.SIENNA);
                        glbls[i][j].setText("DL");
                    }
                    else if( i==7 && j==7){
                        glbls[i][j].setTextFill(Color.CORNFLOWERBLUE);
                        glbls[i][j].setText("Start");
                    }
            }

        }
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        while (true) {     
            list = new LinkedList<String>((LinkedList<String>)ois.readObject());
            Label[] stock = new Label[7];
            for (int i = 0; i < 7; i++) {
                stock[i] = new Label(list.poll());
                
            }
            HBox st= new HBox(1, stock);
            root.setBottom(st);
            System.out.println(list.size());
            
            
            
            if(list.isEmpty())  break;
            
            
            
            
        }
        
        
    }
    public static void main(String[] args) throws UnknownHostException, IOException {
        s= new Socket(InetAddress.getLocalHost(), 1030);
        launch(args);
        s.close();
    }
}
