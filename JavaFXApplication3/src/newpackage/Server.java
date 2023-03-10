/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;


public class Server extends Application{
    public static Stage primaryStage;
    static Socket s;
    ObjectInputStream ois;
    public LinkedList<String> list= new LinkedList<String>();
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.primaryStage= primaryStage;
        GridPane gpane= new GridPane();
        gpane.setGridLinesVisible(true);
        AnchorPane apn= FXMLLoader.load(getClass().getResource("Buttoncont_1_1.fxml"));
        ColumnConstraints col= new ColumnConstraints(90);
        RowConstraints row= new RowConstraints(70);
        gpane.getColumnConstraints().add(col);
        gpane.getRowConstraints().add(row);
        ObjectOutputStream oos= new ObjectOutputStream(s.getOutputStream());
        ois= new ObjectInputStream(s.getInputStream());
        info inf= new info();
        oos.writeObject(inf);
        System.out.println(ois.readObject());
        String []str= new String[100];
        int i;
        for(i=0;i<9;i++) str[i]="a";
        for(;i<11;i++)  str[i]="b";
        for(;i<13;i++)   str[i]="c";
        for(;i<17;i++)   str[i]="d";
        for(;i<29;i++)   str[i]="e";
        for(;i<31;i++)   str[i]="f";
        for(;i<34;i++)   str[i]="g";
        for(;i<36;i++)   str[i]="h";
        for(;i<45;i++)   str[i]="i";
        str[i++]="j";
        str[i++]="k";
        for(;i<51;i++)   str[i]="l";
        for(;i<53;i++)   str[i]="m";
        for(;i<59;i++)   str[i]="n";
        for(;i<67;i++)   str[i]="o";
        for(;i<69;i++)   str[i]="p";
        str[i++]="q";
        for(;i<76;i++)   str[i]="r";
        for(;i<80;i++)   str[i]="s";
        for(;i<86;i++)   str[i]="t";
        for(;i<90;i++)   str[i]="u";
        for(;i<92;i++)   str[i]="v";
        for(;i<94;i++)   str[i]="w";
        str[i++]="x";
        for(;i<97;i++)   str[i]="y";
        str[i++]="z";
        str[i++]="";
        str[i++]="";
        Random rand= new Random();
        int n;
        
        for(i=0;i<100;){
            n=rand.nextInt(100);
            if(str[n].equals("  ")==false){
                list.add(str[n]);
                str[n]="  ";
                i++;
            }
        }

        Label [][]glbls= new Label[15][15];
        for(i=0;i<15;i++){
            for(int j=0;j<15;j++){

                glbls[i][j]=new Label();
                glbls[i][j].setAlignment(Pos.CENTER);


                glbls[i][j].setPrefSize(90, 70);
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
        Button press= new Button("Done");
        
        BorderPane root= new BorderPane(gpane);
        root.setTop(new Label("Game"));
        root. setRight(apn);
        Scene scene = new Scene(root, 1300, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
        boolean fmove= true;                 
        while (true) {            
            Label[] stock = new Label[7];
            for (i = 0; i < 7; i++) {
                stock[i] = new Label(list.poll());
                stock[i].setAlignment(Pos.CENTER);
                stock[i].setPrefSize(90, 70);
            }
            HBox st= new HBox(1, stock);
            root.setBottom(st);
            System.out.println(list.size());
            if(fmove)   {
                oos.writeObject(list);
                fmove= false;
            }
                
            
            
            list = new LinkedList<String>((LinkedList<String>)ois.readObject());
            
            
            
            
            if(list.isEmpty())  break;
            
        }
            
        
        
    }
    
    
    public static void main(String[] args) throws IOException {
        
        ServerSocket ser= new ServerSocket(1030);
        s = ser.accept();
        
        launch(args);
        s.close();
    }
}
