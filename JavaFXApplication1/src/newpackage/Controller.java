/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Observable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author user
 */
public class Controller {
    int othport;
    boolean flag= true;
    boolean passflag= true;
    boolean onflag= true;
    String username,password;
    Integer port;
    private Main main;
    @FXML
    private ListView<String> list;
    DataOutputStream dos;
    DataInputStream dis;
    String fromserver;
    @FXML
    private TextField usertext;
    @FXML
    private PasswordField passtext;
    @FXML
    private TextField otheruser;
    public Controller() {
        this.list = new ListView<>();
    }
    
    @FXML
    void playbuttonpressed() throws IOException{
        
        
        main.showLoginPage();
    }
    
    
    
    @FXML
    void loginpressed() throws IOException{
        if (flag) {
            
            dis = new DataInputStream(main.client.getInputStream());
            dos = new DataOutputStream(main.client.getOutputStream());
            flag= false;
        }
        System.out.println(flag);
        dos.writeUTF("OLD");
        username= usertext.getText();
        System.out.println(username);
        
        String str= dis.readUTF();
        if(str.equals("USERNAME")){
            dos.writeUTF(username);
        }
        String msg= dis.readUTF();
        System.out.println(msg);
        if(msg.equals("Taken")){
            System.out.println("Invalid Name; try again");
            main.wrongusernm();
            usertext.clear();
            playbuttonpressed();
        }
        
        else{
            oldusernamesubmitted();
        }
        
    }
    
    @FXML
    void oldusernamesubmitted() throws IOException{
        main.oldpassword();
    }
    
    @FXML
    void verifypassword() throws IOException{
        password= passtext.getText();
        System.out.println(password);
        if(passflag){
            dis = new DataInputStream(main.client.getInputStream());
            dos = new DataOutputStream(main.client.getOutputStream());
            passflag= false;
        }
        dos.writeUTF("OLDPASS");
        String msg= dis.readUTF();
        System.out.println(msg);
        if(msg.equals("PASSWORD")){
            dos.writeUTF(password);
            
        }
        msg= dis.readUTF();
        System.out.println(msg);
        if(msg.equals("WRONG")){
            passtext.clear();
            main.wrongpassword();
            oldusernamesubmitted();
        }
        else{
            dos.writeUTF("PORT");
            msg= dis.readUTF();
            port= Integer.parseInt(msg);
            System.out.println(port);
           
            onlineplayers();
        }
    }
    
    @FXML
    void createnewaccountpressed() throws IOException{
        main.createnew();
    }
    
    @FXML
    void createnewsubmitpressed() throws IOException{
        if (flag) {
            
            dis = new DataInputStream(main.client.getInputStream());
            dos = new DataOutputStream(main.client.getOutputStream());
            flag= false;
        }
        System.out.println(flag);
        dos.writeUTF("NEW");
        username= usertext.getText();
        System.out.println(username);
        String str= dis.readUTF();
        if(str.equals("USERNAME")){
            dos.writeUTF(username);
        }
        String msg= dis.readUTF();
        System.out.println(msg);
        if(msg.equals("Taken")){
            System.out.println("Invalid Name; try again");
            main.takenusernm();
            usertext.clear();
            createnewaccountpressed();
        }
        else{
            
            newusernamesubmitted();
        }
    }
    
    @FXML
    void newusernamesubmitted() throws IOException{
        main.newpassword();
    }
    
    @FXML
    void getnewpassword() throws IOException{
        password= passtext.getText();
        System.out.println(password);
        if(passflag){
            dis = new DataInputStream(main.client.getInputStream());
            dos = new DataOutputStream(main.client.getOutputStream());
            passflag= false;
        }
        if(password.length()==0)  {
            passtext.clear();
            main.wrongpassword();
            newusernamesubmitted();
        }
        else{
            dos.writeUTF("NEWPASS");
            String msg= dis.readUTF();
            System.out.println(msg);
            if(msg.equals("PASSWORD")){
                dos.writeUTF(password);
            }
            port= Integer.parseInt(dis.readUTF());
            System.out.println(port);
            
            onlineplayers();
        }
    }
    
    @FXML
    void onlineplayers() throws IOException{
        if(onflag){
            dis= new DataInputStream(main.client.getInputStream());
            dos= new DataOutputStream(main.client.getOutputStream());
            onflag= false;
        }
        dos.writeUTF("LIST");
        int total= dis.readInt();
        System.out.println(total);
        String []users= new String[total];
        dos.writeUTF("GOT");
        for(int i=0;i<total;i++){
            users[i]= dis.readUTF();
            dos.writeUTF("READ");
        }
        for(int i=0;i<total;i++){
            System.out.println(users[i]);
        }
        ArrayList<String> arr= new ArrayList<>();
        for(int i=0;i<total;i++){
            arr.add(users[i]);
        }
        /*FXMLLoader loader= new FXMLLoader();
        loader.setLocation(Controller.class.getResource("onlineusers.fxml"));
        AnchorPane pane= loader.load();
        System.out.println(arr.size());*/
        ObservableList<String> data= FXCollections.observableArrayList(arr);
        System.out.println(data.size());
        
        list= new  ListView<>(data);
        
        SplitPane pane=new SplitPane();
        Scene scene= new Scene(pane,1000,800,Color.CYAN);
        pane.setOrientation(Orientation.HORIZONTAL);
        pane.prefWidthProperty().bind(scene.widthProperty());
        pane.prefHeightProperty().bind(scene.heightProperty());
        
        AnchorPane apane= FXMLLoader.load(getClass().getResource("onlineusers.fxml"));
        pane.getItems().add(0, list);
        pane.getItems().add(1, apane);
        main.primaryStage.setScene(scene);
        main.primaryStage.show();
    }
    
    boolean oflag= true;
    
    @FXML
    void playrequest() throws IOException{
        if(oflag){
            dis= new DataInputStream(main.client.getInputStream());
            dos= new DataOutputStream(main.client.getOutputStream());
            oflag= false;
        }
        String nm= otheruser.getText();
        dos.writeUTF("OTHER");
        String s= dis.readUTF();
        System.out.println(s);
        if(s.equals("NAME")){
            dos.writeUTF(nm);
        }
        s=dis.readUTF();
        System.out.println(s);
        if(s.equals("taken")){
            otheruser.clear();
            main.wrongusernm();
        }
        else{
            othport= Integer.parseInt(s);
            otheruser.clear();
            System.out.println(othport);
            
        }
    }
    
}
