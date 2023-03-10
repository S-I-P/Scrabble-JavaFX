
package newpackage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.*;
import javafx.scene.control.ComboBox;
import javafx.scene.text.TextAlignment;

public class cl extends Application{
    public static Socket s;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    GridPane gpane= new GridPane();
    
    GridPane gpane2= new GridPane();
    BorderPane root= new BorderPane(gpane);
    BorderPane root2= new BorderPane(gpane2);
    Scene scene2;
    Scene scene;
    Stage primaryStage;
    String st= new String();
    ComboBox<String> t;
    LinkedList<String> list = new LinkedList<String>();
    
    ArrayList <String> temp= new ArrayList<>();
    ArrayList <String> temp1= new ArrayList<>();
    String [][]entry= new String[15][15];
    ComboBox [][]cbx= new ComboBox[15][15];
    Label [][]glbls= new Label[15][15];
    Label [][]glbls2= new Label[15][15];
    String [][]tempentry = new String[15][15];
    
    ArrayList<String> dictionary= new ArrayList<>();
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        File f= new File("dictionary.txt");
        
        FileReader fr= new FileReader(f);
        char []target= new char[(int)f.length()];
        String trgt;
        fr.read(target);
        trgt= new String(target);
        String []dict= trgt.split("\n");
        
        for(int k=0;k<dict.length;k++){
            dictionary.add(dict[k]);
        }
        
        int i;

        this.primaryStage = primaryStage;
        primaryStage.setTitle("Client");
        for(i=0;i<15;i++){
            for(int j=0;j<15;j++){
                
                tempentry[i][j]="$$";
            }
        }
        
        root.setPadding(new Insets(10, 10, 10, 10));
        
                        
       
        GridPane gpn=new GridPane();
        
        for(i=0;i<6;i++){
            gpn.add(new Label(),0,i);
        }
        gpane.setHgap(0.5);
        gpane.setVgap(0.5);
        gpn.add(new Label(),1,0);
        gpn.add(new Label(),1,1);
        gpn.add(new Label(),1,3);
        gpn.add(new Label(),1,4);
        Button btn= new Button("Done");
        gpn.add(btn, 1, 2);
        Button rbtn= new Button("Retry");
        gpn.add(rbtn, 1, 5);
        root.setRight(gpn);
        
        btn.setOnAction(e-> {
            try {
                doneclicked();
            } catch (IOException ex) {
                Logger.getLogger(ser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(cl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        rbtn.setOnAction(e-> retclicked());
        gpane2.setGridLinesVisible(true);
        for(i=0;i<15;i++){
            for(int j=0;j<15;j++){
                glbls2[i][j]= new Label(" ");
                glbls2[i][j].setAlignment(Pos.CENTER);
                glbls2[i][j].setPrefSize(90, 70);
                glbls2[i][j].setTextAlignment(TextAlignment.CENTER);
                glbls2[i][j].setTextFill(Color.DARKCYAN);
                gpane2.add(glbls2[i][j], j, i);
            }
        }
        scene = new Scene(root, 1300, 800);
        scene2= new Scene(root2, 1300, 800);
        primaryStage.setScene(scene2);
        primaryStage.show();
        
        setitems();
        
    }
    
    void setitems() throws IOException, ClassNotFoundException{
        oos= new ObjectOutputStream(s.getOutputStream());
        ois= new ObjectInputStream(s.getInputStream());
        if(ois.readObject().equals("Done"))  oos.writeObject("List");
        list= new LinkedList<String>((LinkedList<String>) ois.readObject());
        oos.writeObject("Entry");
        entry= (String[][]) ois.readObject();
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(entry[i][j].equals("$$")== false){
                    glbls2[i][j].setText(entry[i][j]);
                    Label lbl = new Label(entry[i][j]);
                    lbl.setAlignment(Pos.CENTER);
                    lbl.setTextAlignment(TextAlignment.CENTER);
                    lbl.setTextFill(Color.DARKCYAN);
                    gpane.add(lbl, j, i);
                }
            }
        }
        for(int i=0;i<7;i++){
            temp.add(list.poll());
        }
        int i;
        for(i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if (entry[i][j].equals("$$")) {
                    cbx[i][j] = new ComboBox();
                    cbx[i][j].getItems().addAll(temp);
                    cbx[i][j].setPrefSize(90, 70);
                    
                    cbx[i][j].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent ae) {
                            int ii = 0, jj = 0;
                            t = (ComboBox<String>) ae.getSource();
                            if (t.getValue() != null) {
                                st = t.getValue();
                                temp.remove(st);
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (cbx[k][l].equals(t)) {
                                                ii = k;
                                                jj = l;
                                                break;
                                            }
                                        }
                                        
                                    }
                                    if (ii != 0) {
                                        break;
                                    }
                                }
                                tempentry[ii][jj] = st;
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (k != ii || l != jj) {
                                                
                                                cbx[k][l].getItems().remove(st);
                                                
                                            }
                                        }
                                        
                                    }
                                    
                                }
                                
                                gpane.getChildren().remove(cbx[ii][jj]);
                                glbls[ii][jj] = new Label(st);
                                glbls[ii][jj].setPrefSize(90, 70);
                                
                                gpane.add(glbls[ii][jj], jj, ii);
                            }
                        }
                        
                    });
                    gpane.add(cbx[i][j], j, i);
                }

            }
        }
        primaryStage.setScene(scene);
    }

    public void doneclicked() throws IOException, ClassNotFoundException{
        int rem=0;
        int []rowind;
        int []colind;
        String check= new String();
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false)     {
                    rem++;
                }
            }
        }
        
        
        rowind= new int[rem];
        colind= new int[rem];
        
        int k=0;
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false)     {
                    check+= tempentry[i][j];
                    rowind[k]=i;
                    colind[k]=j;
                    k++;
                    
                }
            }
        }
        for(int i=0;i<rem;i++){
            
                System.out.println(rowind[i]+" "+colind[i]);
            
            
        }
        
        
        boolean f1= true,f2= true;
        for(int i=0;i<rem-1;i++){
            for(int j= i+1;j<rem;j++){
                if(rowind[i]!=rowind[j])   f1=false;
            }
            
        }
        
        for(int i=0;i<rem-1;i++){
            for(int j= i+1;j<rem;j++){
                if(colind[i]!=colind[j])   f2=false;
            }
            
        }
        
        
            if (f1 == false && f2 == false) {
                warningstage();
                return;
            } 
            
            else if (f1 || f2) {
                
                if(f1 && f2){
                    boolean flg= true;
                    int i= rowind[0];
                    int j= colind[0];
                    System.out.println(i+" "+j);
                    if( (i!=0) && (entry[i-1][j].equals("$$")==false) ){
                        flg= false;
                    }
                    if( (i!=14) && (entry[i+1][j].equals("$$")==false) ){
                        flg= false;
                    }
                    if( (j!=0) && (entry[i][j-1].equals("$$")==false) ){
                        flg= false;
                    }
                    if( (j!=14) && (entry[i][j+1].equals("$$")==false) ){
                        flg= false;
                    }
                    if(flg){
                        warningstage();
                        return;
                    }
                    else{
                        int l=i;
                        int in,fn;
                        while((l-1)>=0 && ( entry[l-1][j].equals("$$")==false )){
                            l--;
                        }
                        in= l;
                        l= i;
                        while((l+1)<=14 && ( entry[l+1][j].equals("$$")==false )){
                            l++;
                        }
                        fn=l;
                        String vs= new String();
                        if(fn!= in){
                            for(l=in;l<=fn;l++){
                                if(entry[l][j].equals("$$")==false){
                                    vs+= entry[l][j];
                                }
                                else{
                                    vs+= tempentry[l][j];
                                }
                            }
                            System.out.println(vs+  " f1 && f2");
                            if(dictionary.contains(vs)==false){
                                wrongwords();
                                return;
                            }
                        }
                        
                        l= j;
                        while((l-1)>=0 && ( entry[i][l-1].equals("$$")==false )){
                            l--;
                        }
                        in= l;
                        l= j;
                        while((l+1)>=0 && ( entry[i][l+1].equals("$$")==false )){
                            l++;
                        }
                        fn= l;
                        String hs= new String();
                        if(fn!= in){
                            for(l=in;l<=fn;l++){
                                if(entry[i][l].equals("$$")==false){
                                    hs+= entry[i][l];
                                }
                                else{
                                    hs+= tempentry[i][l];
                                }
                            }
                            System.out.println(hs+ " f1&&f2");
                            if(dictionary.contains(hs)==false){
                                wrongwords();
                                return;
                            }
                        }
                        
                    }
                }
                
                else if (f2) {
                    boolean flg= true;
                    for (int i : rowind) {
                        if (i != 0) {
                            if (entry[i - 1][colind[0]].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (i != 14) {
                            if (entry[i + 1][colind[0]].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (colind[0] != 0) {
                            if (entry[i][colind[0] - 1].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (colind[0] != 14) {
                            if (entry[i][colind[0] + 1].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                    }
                    if(flg){
                        warningstage();
                        return;
                    }
                    else{
                        int init,fin;
                        int p= rowind[0];
                        while( ((p-1)>=0) &&  (entry[p-1][colind[0]].equals("$$")== false) ){
                            p--;
                        }
                        init= p;
                        p= rowind[rem-1];
                        while ( ((p+1)<=14) &&  (entry[p+1][colind[0]].equals("$$")== false) ) {                            
                            p++;
                        }
                        fin=p;
                        String vs= new String();
                        for(p=init;p<rowind[0];p++){
                            vs+= entry[p][colind[0]];
                        }
                        for(p=rowind[0];p<rowind[rem-1]+1;p++){
                            if(entry[p][colind[0]].equals("$$")==false){
                                vs+= entry[p][colind[0]];
                            }
                            else{
                               vs+= tempentry[p][colind[0]];
                            }
                        }
                        
                        for(p=rowind[rem-1]+1;p<=fin;p++){
                            vs+= entry[p][colind[0]];
                        }
                        System.out.println(vs+ " f2");
                        if(dictionary.contains(vs)==false){
                            wrongwords();
                            return;
                        }
                        else{
                            for(int l: rowind){
                                int m,in,fn;
                                m= colind[0];
                                while ( (m-1)>=0 && (entry[l][m-1].equals("$$")==false) ) {
                                    m--;
                                    
                                }
                                in= m;
                                m=colind[0];
                                while ( ((m+1)<=14) && (entry[l][m+1].equals("$$")==false) ) {
                                    m++;
                                    
                                }
                                fn= m;
                                if(in==fn)  continue;
                                String hs= new String();
                                for(m=in;m<=fn;m++){
                                    if(entry[l][m].equals("$$")==false){
                                        hs+= entry[l][m];
                                    }
                                    else{
                                        hs+= tempentry[l][m];
                                    }
                                }
                                System.out.println(hs+" f2");
                                if(dictionary.contains(hs)==false){
                                    wrongwords();
                                    return;
                                }
                            }
                        }
                    }
                }
                else if(f1){
                    boolean flg= true;
                    for (int i: colind) {
                        if (i != 0) {
                            if (entry[rowind[0]][i-1].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (i != 14) {
                            if (entry[rowind[0]][i+1].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (rowind[0] != 0) {
                            if (entry[rowind[0]-1][i].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                        if (rowind[0] != 14) {
                            if (entry[rowind[0]+1][i].equals("$$") == false) {
                                flg= false;
                                break;
                            }
                        }
                    }
                    if(flg){
                        warningstage();
                        return;
                    }
                    
                    else{
                        int init,fin;
                        int p= colind[0];
                        while( ((p-1)>=0) &&  (entry[rowind[0]][p-1].equals("$$")== false) ){
                            p--;
                        }
                        init= p;
                        p= colind[rem-1];
                        while ( ((p+1)<=14) &&  (entry[rowind[0]][p+1].equals("$$")== false) ) {                            
                            p++;
                        }
                        fin=p;
                        String vs= new String();
                        for(p=init;p<colind[0];p++){
                            vs+= entry[rowind[0]][p];
                        }
                        for(p=colind[0];p<colind[rem-1]+1;p++){
                            if(entry[rowind[0]][p].equals("$$")==false){
                                vs+= entry[rowind[0]][p];
                            }
                            else{
                               vs+= tempentry[rowind[0]][p];
                            }
                        }
                        
                        for(p=colind[rem-1]+1;p<=fin;p++){
                            vs+= entry[rowind[0]][p];
                        }
                        System.out.println(vs+" f1");
                        if(dictionary.contains(vs)==false){
                            wrongwords();
                            return;
                        }
                        else{
                            for(int l: colind){
                                int m,in,fn;
                                m= rowind[0];
                                while ( (m-1)>=0 && (entry[m-1][l].equals("$$")==false) ) {
                                    m--;
                                    
                                }
                                in= m;
                                m=rowind[0];
                                while ( ((m+1)<=14) && (entry[m+1][l].equals("$$")==false) ) {
                                    m++;
                                    
                                }
                                fn= m;
                                if(in==fn)  continue;
                                String hs= new String();
                                for(m=in;m<=fn;m++){
                                    if(entry[m][l].equals("$$")==false){
                                        hs+= entry[m][l];
                                    }
                                    else{
                                        hs+= tempentry[m][l];
                                    }
                                }
                                System.out.println(hs+" f1");
                                if(dictionary.contains(hs)==false){
                                    wrongwords();
                                    return;
                                }
                            }
                        }
                    }
                }
            
        }
        
            
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false){
                    gpane.getChildren().remove(glbls[i][j]);
                    entry[i][j]= tempentry[i][j];
                    glbls2[i][j].setText(entry[i][j]);
                    Label lbl = new Label(entry[i][j]);
                    lbl.setAlignment(Pos.CENTER);
                    lbl.setTextAlignment(TextAlignment.CENTER);
                    lbl.setTextFill(Color.DARKCYAN);
                    gpane.add(lbl, j, i);
                }
                else if(tempentry[i][j].equals("$$") && entry[i][j].equals("$$")){
                    gpane.getChildren().remove(cbx[i][j]);
                }
            }     
            
        }    
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                tempentry[i][j]="$$";
            }
        }
        
        for(int i=0;i<rem;i++){
            temp.add(list.poll());
        }
        
        oos= new ObjectOutputStream(s.getOutputStream());
        ois= new ObjectInputStream(s.getInputStream());
        oos.writeObject("Done");
        if(ois.readObject().equals("List"))  oos.writeObject(list);
        if(ois.readObject().equals("Entry"))    oos.writeObject(entry);
        
        primaryStage.setScene(scene2);
        if(ois.readObject().equals("Done"))  oos.writeObject("List");
        list= new LinkedList<String>((LinkedList<String>) ois.readObject());
        oos.writeObject("Entry");
        entry= (String[][]) ois.readObject();
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if (entry[i][j].equals("$$")) {
                    cbx[i][j] = new ComboBox();
                    cbx[i][j].getItems().addAll(temp);
                    cbx[i][j].setPrefSize(90, 70);
                    
                    cbx[i][j].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent ae) {
                            int ii = 0, jj = 0;
                            t = (ComboBox<String>) ae.getSource();
                            if (t.getValue() != null) {
                                st = t.getValue();
                                temp.remove(st);
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (cbx[k][l].equals(t)) {
                                                ii = k;
                                                jj = l;
                                                break;
                                            }
                                        }
                                        
                                    }
                                    if (ii != 0) {
                                        break;
                                    }
                                }
                                tempentry[ii][jj] = st;
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (k != ii || l != jj) {
                                                
                                                cbx[k][l].getItems().remove(st);
                                                
                                            }
                                        }
                                        
                                    }
                                    
                                }
                                
                                gpane.getChildren().remove(cbx[ii][jj]);
                                glbls[ii][jj] = new Label(st);
                                glbls[ii][jj].setPrefSize(90, 70);
                                
                                gpane.add(glbls[ii][jj], jj, ii);
                            }
                        }
                        
                    });
                    gpane.add(cbx[i][j], j, i);
                }
            }
        }    
            
        primaryStage.setScene(scene);
    }
    
    
    public void retclicked(){
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false){
                    temp.add(tempentry[i][j]);
                    gpane.getChildren().remove(glbls[i][j]);
                }
                
            }
        }
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                
                if (entry[i][j].equals("$$")) {
                    cbx[i][j] = new ComboBox();
                    cbx[i][j].getItems().addAll(temp);
                    cbx[i][j].setPrefSize(90, 70);
                    
                    cbx[i][j].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent ae) {
                            int ii = 0, jj = 0;
                            t = (ComboBox<String>) ae.getSource();
                            if (t.getValue() != null) {
                                st = t.getValue();
                                temp.remove(st);
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (cbx[k][l].equals(t)) {
                                                ii = k;
                                                jj = l;
                                                break;
                                            }
                                        }
                                        
                                    }
                                    if (ii != 0) {
                                        break;
                                    }
                                }
                                tempentry[ii][jj] = st;
                                for (int k = 0; k < 15; k++) {
                                    for (int l = 0; l < 15; l++) {
                                        if (entry[k][l].equals("$$")) {
                                            if (k != ii || l != jj) {
                                                
                                                cbx[k][l].getItems().remove(st);
                                                
                                            }
                                        }
                                        
                                    }
                                    
                                }
                                
                                gpane.getChildren().remove(cbx[ii][jj]);
                                glbls[ii][jj] = new Label(st);
                                glbls[ii][jj].setPrefSize(90, 70);
                                
                                gpane.add(glbls[ii][jj], jj, ii);
                            }
                        }
                        
                    });
                    gpane.add(cbx[i][j], j, i);
                }
            }
        }
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false){
                    tempentry[i][j]= "$$";
                    
                }
            }
        }
    }
    
    void warningstage(){
        Stage warning= new Stage();
        BorderPane pn= new BorderPane();
        Label lbl= new Label("Invalid Move");
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setTextFill(Color.RED);
        pn.setCenter(lbl);
        Scene scn= new Scene(pn,300,200);
        warning.setScene(scn);
        warning.show();
    }
    
    void wrongwords(){
        Stage warning= new Stage();
        BorderPane pn= new BorderPane();
        Label lbl= new Label("Invalid Word");
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setTextFill(Color.RED);
        pn.setCenter(lbl);
        Scene scn= new Scene(pn,300,200);
        warning.setScene(scn);
        warning.show();
    }
        
    
    public static void main(String[] args) throws UnknownHostException, IOException {
        s= new Socket(InetAddress.getLocalHost(), 1029);
        launch(args);
    }
    
}
