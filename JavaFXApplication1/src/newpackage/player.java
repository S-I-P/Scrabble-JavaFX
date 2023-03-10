package newpackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;


public class player {
    boolean finishflag= false;
    boolean pl2f, fmove2= false;
    static Rectangle []stock1= new Rectangle[7];
    
    static Text []letter= new Text[7];
    int initx= 10;
    int []inity={200,265,330,395,460,525,590};    
    static String []temp= new String[7];
    Pane root;
    public static String othplayername;
    public static Stage stage;
    boolean flag= true;
    Rectangle [][]board= new Rectangle[15][15];
    int cox;
    int coy= 2;
    LinkedList<String> list= new LinkedList<String>();

    String [][]tempentry= new String[15][15];
    String [][]entry= new String[15][15];
    String [][]myentry= new String[15][15];
    boolean fmove;
    
    static ArrayList<String> dictionary= Main.dictionary;

    public player( String name, boolean value) {
        fmove= !value;
        pl2f= !value;
        othplayername= name;
        stage= new Stage();
        stage.setTitle("You vs. "+name);
        
        if (value) {
            String[] str = new String[98];
            int i;
            for (i = 0; i < 9; i++) {
                str[i] = "a";
            }
            for (; i < 11; i++) {
                str[i] = "b";
            }
            for (; i < 13; i++) {
                str[i] = "c";
            }
            for (; i < 17; i++) {
                str[i] = "d";
            }
            for (; i < 29; i++) {
                str[i] = "e";
            }
            for (; i < 31; i++) {
                str[i] = "f";
            }
            for (; i < 34; i++) {
                str[i] = "g";
            }
            for (; i < 36; i++) {
                str[i] = "h";
            }
            for (; i < 45; i++) {
                str[i] = "i";
            }
            str[i++] = "j";
            str[i++] = "k";
            for (; i < 51; i++) {
                str[i] = "l";
            }
            for (; i < 53; i++) {
                str[i] = "m";
            }
            for (; i < 59; i++) {
                str[i] = "n";
            }
            for (; i < 67; i++) {
                str[i] = "o";
            }
            for (; i < 69; i++) {
                str[i] = "p";
            }
            str[i++] = "q";
            for (; i < 76; i++) {
                str[i] = "r";
            }
            for (; i < 80; i++) {
                str[i] = "s";
            }
            for (; i < 86; i++) {
                str[i] = "t";
            }
            for (; i < 90; i++) {
                str[i] = "u";
            }
            for (; i < 92; i++) {
                str[i] = "v";
            }
            for (; i < 94; i++) {
                str[i] = "w";
            }
            str[i++] = "x";
            for (; i < 97; i++) {
                str[i] = "y";
            }
            str[i++] = "z";
            
            Random rand = new Random();
            int n;
            
            for (i = 0; i < 98;) {
                n = rand.nextInt(98);
                if (str[n].equals("  ") == false) {
                    list.add(str[n]);
                    str[n] = "  ";
                    i++;
                }
            }
            for(i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    entry[i][j]="$$";
                    tempentry[i][j]="$$";
                    myentry[i][j]="$$";
                }
            }
            StrokeType st= StrokeType.INSIDE;
        root= new Pane();
        
        for(i=0;i<7;i++)    {
            temp[i]= list.poll();
            letter[i]= new Text(temp[i]);
            Text t= letter[i];
            t.setFill(Color.DARKSLATEBLUE);
            t.setFont(new Font(40));
            t.setX(initx+20);
            t.setY(inity[i]+30);
            t.setBoundsType(TextBoundsType.VISUAL);
        }
        
        for(i=0;i<15;i++){
            cox= 200;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 60, 50);
                board[i][j].setFill(Color.LIGHTBLUE);
                board[i][j].setStrokeType(st);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                root.getChildren().add(board[i][j]);
                
                
                cox+=65;
            }
            coy+= 55;
        }
        
        for(i=0;i<7;i++){
            stock1[i]= createrectangle(initx, inity[i], 56, 46, letter[i]);
            stock1[i].setFill(Color.CORNSILK);
            stock1[i].setStrokeType(st);
            stock1[i].setStroke(Color.DARKGRAY);
            stock1[i].setStrokeWidth(3);
            root.getChildren().addAll(stock1[i],letter[i]);
        }
        
        Button done= new Button("Done");
        
        done.setLayoutX(30);
        done.setLayoutY(10);
        done.setOnAction(e-> {
                try {
                    donepressed();
                } catch (IOException ex) {
                    Logger.getLogger(player.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        root.getChildren().add(done);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        }
        
        else{
            root= new Pane();
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    entry[i][j]="$$";
                    tempentry[i][j]="$$";
                    myentry[i][j]="$$";
                }
            }
            for(int i=0;i<15;i++){
                cox= 200;
                for(int j=0;j<15;j++){
                    board[i][j]= new Rectangle(cox, coy, 60, 50);
                    board[i][j].setFill(Color.LIGHTBLUE);
                    board[i][j].setStrokeType(StrokeType.INSIDE);
                    board[i][j].setStroke(Color.DARKGRAY);
                    board[i][j].setStrokeWidth(3);
                    root.getChildren().add(board[i][j]);
                    cox+=65;
                }
                coy+= 55;
            }
            
            Scene scene = new Scene(root);
        
            stage.setScene(scene);
            stage.show();
            
        }
    }
    
    Rectangle createrectangle(double x, double y, double  width, double  height, Text t){
        
        
        double px=x , py= y;
        Rectangle rect= new Rectangle(x, y, width, height);
        
        rect.setCursor(Cursor.HAND);
        rect.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                double dragX = me.getX();
                double dragY = me.getY();
               
                double newXPosition = dragX;
                double newYPosition = dragY;
             
                rect.setX(newXPosition);
                rect.setY(newYPosition);
                t.setX(newXPosition+20);
                t.setY(newYPosition+30);
            }
        });
        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                boolean temp= false;
                for(int i=0;i<15;i++){
                    for(int j=0;j<15;j++){
                        if(rect.getX()>= board[i][j].getX() && (rect.getX()+ 56)<= (board[i][j].getX()+60) && rect.getY()>= board[i][j].getY() && (rect.getY()+ 46)<= (board[i][j].getY()+50)  && entry[i][j].equals("$$")){
                            rect.setX(board[i][j].getX()+2);
                            rect.setY(board[i][j].getY()+2);
                            t.setX(board[i][j].getX()+2+20);
                            t.setY(board[i][j].getY()+2+30);
                            
                            temp= true;
                        }             
                    }
                    if(temp)    break;
                }
                if(temp== false){
                    rect.setX(px);
                    rect.setY(py);
                    t.setX(px+20);
                    t.setY(py+30);
                }
            }
        });
        return rect;
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
    
    void wrongwords(String wwww){
        Stage warning= new Stage();
        BorderPane pn= new BorderPane();
        Label lbl= new Label("Invalid Word: "+wwww);
        lbl.setTextAlignment(TextAlignment.CENTER);
        lbl.setTextFill(Color.RED);
        pn.setCenter(lbl);
        Scene scn= new Scene(pn,300,200);
        warning.setScene(scn);
        warning.show();
    }
    
    void donepressed() throws SocketException, IOException{
        boolean passflag= true;
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(entry[i][j].equals("$$")==false)     passflag= false;
            }
        }
        
        if(passflag){
            fmove= false;
            System.out.println("PASS FOUND");
        }
        for(int rec=0;rec<7;rec++){
            Text t= letter[rec];
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    if(t.getX()==(board[i][j].getX()+2+20) && t.getY()== (board[i][j].getY()+2+30) ){
                        tempentry[i][j]= t.getText();
                        
                    }            
                }
                    
            }
        }
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                System.out.print(tempentry[i][j]+"  ");
            }
            System.out.println("");
        }
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
        
        
        if(rem==0){
            root.getChildren().clear();
            root = new Pane();
            for(int i=0;i<temp.length;i++)    {
                letter[i]= new Text(temp[i]);
                Text t= letter[i];
                t.setFill(Color.DARKSLATEBLUE);
                t.setFont(new Font(40));
                t.setX(initx+20);
                t.setY(inity[i]+30);
                t.setBoundsType(TextBoundsType.VISUAL);
            }
        coy=2;
        for(int i=0;i<15;i++){
            cox= 200;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 60, 50);
                
                board[i][j].setStrokeType(StrokeType.INSIDE);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                
                cox+=65;
                if(entry[i][j].equals("$$")==false){
                    Text t= new Text(entry[i][j]);
                    t.setFill(Color.DARKGREEN);
                    t.setFont(new Font(40));
                    t.setX(board[i][j].getX()+20);
                    t.setY(board[i][j].getY()+30);
                    t.setBoundsType(TextBoundsType.VISUAL);
                    root.getChildren().add(t);
                }
                else{
                    board[i][j].setFill(Color.LIGHTBLUE);
                    root.getChildren().add(board[i][j]);
                }
                
            }
            coy+= 55;
            
        }
            for(int i=0;i<7;i++){
                stock1[i]= createrectangle(initx, inity[i], 56, 46, letter[i]);
                stock1[i].setFill(Color.CORNSILK);
                stock1[i].setStrokeType(StrokeType.INSIDE);
                stock1[i].setStroke(Color.DARKGRAY);
                stock1[i].setStrokeWidth(3);
                root.getChildren().addAll(stock1[i],letter[i]);
            }
        
            String send= "MOVE\n" + othplayername + "\n" + Controller.username+ "\n" +"ENTRY\n";
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    send= send+ entry[i][j] + "\n";
                }
            }
            send= send+ "LIST\n";
            while(list.peek()!=null){
                send= send+ list.poll()+ "\n";
            }


            DatagramPacket p;
            DatagramSocket ds;
            byte []buf= send.getBytes();
            p= new DatagramPacket(buf, buf.length, Main.address, 1029);
            ds= new DatagramSocket();
            ds.send(p);
            ds.close();


            Scene scene= new Scene(root);
            stage.setScene(scene);
            return;
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
        
        if(fmove==false){
            if(f1==false && f2==false) {
                for(int i=0;i<15;i++){
                    for(int j=0;j<15;j++){
                        tempentry[i][j]= "$$";
                    }
                }
                
                warningstage();
                return;
            }
            else if(dictionary.contains(check)== false){
                for(int i=0;i<15;i++){
                    for(int j=0;j<15;j++){
                        tempentry[i][j]= "$$";
                    }
                }
                wrongwords(check);
                return;
            }
            else{
                fmove= true;
                
            }
        }
        else {
            if (f1 == false && f2 == false) {
                warningstage();
                for(int iiii=0;iiii<15;iiii++){
                    for(int jjjj=0;jjjj<15;jjjj++){
                        tempentry[iiii][jjjj]= "$$";
                    }
                }
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
                        for(int iiii=0;iiii<15;iiii++){
                            for(int jjjj=0;jjjj<15;jjjj++){
                                tempentry[iiii][jjjj]= "$$";
                            }
                        }
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
                                wrongwords(vs);
                                for(int iii=0;iii<15;iii++){
                                    for(int jjj=0;jjj<15;jjj++){
                                        tempentry[iii][jjj]= "$$";
                                    }
                                }
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
                                wrongwords(hs);
                                for(int iiii=0;iiii<15;iiii++){
                                    for(int jjjj=0;jjjj<15;jjjj++){
                                        tempentry[iiii][jjjj]= "$$";
                                    }
                                }
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
                        for(int iiii=0;iiii<15;iiii++){
                            for(int jjjj=0;jjjj<15;jjjj++){
                                tempentry[iiii][jjjj]= "$$";
                            }
                        }
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
                            wrongwords(vs);
                            for(int iiii=0;iiii<15;iiii++){
                                for(int jjjj=0;jjjj<15;jjjj++){
                                    tempentry[iiii][jjjj]= "$$";
                                }
                            }
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
                                    wrongwords(hs);
                                    for(int iiii=0;iiii<15;iiii++){
                                        for(int jjjj=0;jjjj<15;jjjj++){
                                            tempentry[iiii][jjjj]= "$$";
                                        }
                                    }  
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
                        for(int iiii=0;iiii<15;iiii++){
                            for(int jjjj=0;jjjj<15;jjjj++){
                                tempentry[iiii][jjjj]= "$$";
                            }
                        }
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
                            wrongwords(vs);
                            for(int iiii=0;iiii<15;iiii++){
                                for(int jjjj=0;jjjj<15;jjjj++){
                                    tempentry[iiii][jjjj]= "$$";
                                }
                            }
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
                                    wrongwords(hs);
                                    
                                    for(int iiii=0;iiii<15;iiii++){
                                        for(int jjjj=0;jjjj<15;jjjj++){
                                            tempentry[iiii][jjjj]= "$$";
                                        }
                                    }
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        root.getChildren().clear();
        root = new Pane();
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false){
                    entry[i][j]= tempentry[i][j];
                    myentry[i][j]= tempentry[i][j];
                    
                }
            }
        }
        
        
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                if(tempentry[i][j].equals("$$")==false){
                    for(int iiiii=0;iiiii<7;iiiii++){
                        if(temp[iiiii].equals(tempentry[i][j])){
                            if (list.isEmpty()==false) {
                                temp[iiiii] = list.poll();
                            }
                            else{
                                temp[iiiii]= "$";
                            }
                        }
                    }

                }
            }
        }
        
        
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                tempentry[i][j]="$$";
            }
        
        }
        
        for(int i=0;i<temp.length;i++)    {
            if (temp[i].equals("$")==false) {
                letter[i] = new Text(temp[i]);
                Text t = letter[i];
                t.setFill(Color.DARKSLATEBLUE);
                t.setFont(new Font(40));
                t.setX(initx + 20);
                t.setY(inity[i] + 30);
                t.setBoundsType(TextBoundsType.VISUAL);
            }
        }
        coy=2;
        for(int i=0;i<15;i++){
            cox= 200;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 60, 50);
                
                board[i][j].setStrokeType(StrokeType.INSIDE);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                
                cox+=65;
                if(entry[i][j].equals("$$")==false){
                    Text t= new Text(entry[i][j]);
                    t.setFill(Color.DARKGREEN);
                    t.setFont(new Font(40));
                    t.setX(board[i][j].getX()+20);
                    t.setY(board[i][j].getY()+30);
                    t.setBoundsType(TextBoundsType.VISUAL);
                    root.getChildren().add(t);
                }
                else{
                    board[i][j].setFill(Color.LIGHTBLUE);
                    root.getChildren().add(board[i][j]);
                }
                
            }
            coy+= 55;
            
        }
        for(int i=0;i<7;i++){
            stock1[i]= createrectangle(initx, inity[i], 56, 46, letter[i]);
            stock1[i].setFill(Color.CORNSILK);
            stock1[i].setStrokeType(StrokeType.INSIDE);
            stock1[i].setStroke(Color.DARKGRAY);
            stock1[i].setStrokeWidth(3);
            root.getChildren().addAll(stock1[i],letter[i]);
        }
        
        String send= "MOVE\n" + othplayername + "\n" + Controller.username+ "\n" +"ENTRY\n";
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                send= send+ entry[i][j] + "\n";
            }
        }
        send= send+ "LIST\n";
        while(list.peek()!=null){
            send= send+ list.poll()+ "\n";
        }
        
        
        
        DatagramPacket p;
        DatagramSocket ds;
        byte []buf= send.getBytes();
        p= new DatagramPacket(buf, buf.length, Main.address, 1029);
        ds= new DatagramSocket();
        ds.send(p);
        ds.close();
        
        
        Scene scene= new Scene(root);
        stage.setScene(scene);
        
        finishflag= true;
        for(int i=0;i<temp.length;i++){
            if(temp[i].equals("$")==false)  finishflag= false;
        }
        
        if(finishflag){
            stage.close();
            int score=0;
            for(int a=0;a<15;a++){
                for(int b=0;b<15;b++){
                    if(myentry[a][b].equals("$$")==false)   score++;
                }
            }
            Label scl= new Label("Your Score is "+ score);
            Pane newpane = new Pane(scl);
            Scene scene11= new Scene(newpane,300,200);
            Stage ns= new Stage();
            ns.setScene(scene11);
            ns.show();
            DatagramPacket p1;
            DatagramSocket ds1;
            send= "OVER\n" + othplayername + "\n" + Controller.username + "\n" + score;
            byte []buf1= send.getBytes();
            p1= new DatagramPacket(buf1, buf1.length, Main.address, 1029);
            ds1= new DatagramSocket();
            ds1.send(p1);
            ds1.close();
        }
    }
    
    public void update(String updt){
        System.out.println(updt);
        list.clear();
        String []up= updt.split("\n");
        int k=0;
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                entry[i][j]= up[k];
                k++;
            }
        }
        k++;
        for(int i=k;i<up.length;i++){
            list.add(up[i]);
        }
        System.out.println("LISTSIZE "+ list.size());
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                System.out.print(entry[i][j]+" ");
            }
            System.out.println("");
        }
        
        root.getChildren().clear();
        
        root= new Pane();
        
        if(pl2f){
            if(!fmove2){
                for(int i=0;i<7;i++)    {
                    temp[i]= list.poll();
                    letter[i]= new Text(temp[i]);
                    Text t= letter[i];
                    t.setFill(Color.DARKSLATEBLUE);
                    t.setFont(new Font(40));
                    t.setX(initx+20);
                    t.setY(inity[i]+30);
                    t.setBoundsType(TextBoundsType.VISUAL);
                }
                fmove2= true;
            }
        }
        
        for(int i=0;i<7;i++)    {
            if (temp[i].equals("$")==false) {
                letter[i] = new Text(temp[i]);
                Text t = letter[i];
                t.setFill(Color.DARKSLATEBLUE);
                t.setFont(new Font(40));
                t.setX(initx + 20);
                t.setY(inity[i] + 30);
                t.setBoundsType(TextBoundsType.VISUAL);
            }
        }
        
        coy=2;
        for(int i=0;i<15;i++){
            cox= 200;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 60, 50);
                
                board[i][j].setStrokeType(StrokeType.INSIDE);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                
                cox+=65;
                if(entry[i][j].equals("$$")==false){
                    Text t= new Text(entry[i][j]);
                    t.setFill(Color.DARKGREEN);
                    t.setFont(new Font(40));
                    t.setX(board[i][j].getX()+20);
                    t.setY(board[i][j].getY()+30);
                    t.setBoundsType(TextBoundsType.VISUAL);
                    root.getChildren().add(t);
                }
                else{
                    board[i][j].setFill(Color.LIGHTBLUE);
                    root.getChildren().add(board[i][j]);
                }
                
            }
            coy+= 55;
            
        }
        
        
        
        StrokeType st= StrokeType.INSIDE;
        
        for(int i=0;i<7;i++){
            stock1[i]= createrectangle(initx, inity[i], 56, 46, letter[i]);
            stock1[i].setFill(Color.CORNSILK);
            stock1[i].setStrokeType(st);
            stock1[i].setStroke(Color.DARKGRAY);
            stock1[i].setStrokeWidth(3);
            root.getChildren().addAll(stock1[i],letter[i]);
        }
        
        
        
        Button done= new Button("Done");
        
        done.setLayoutX(30);
        done.setLayoutY(10);
        done.setOnAction(e-> {
                try {
                    donepressed();
                } catch (IOException ex) {
                    Logger.getLogger(player.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        root.getChildren().add(done);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void over(String otscore){
        Label scl= new Label();
        
        int score=0;
        for(int a=0;a<15;a++){
            for(int b=0;b<15;b++){
                    if(myentry[a][b].equals("$$")==false)   score++;
                }
        }
        int otherscore= Integer.parseInt(otscore);
        if(score>otherscore)   {
            scl.setText(othplayername +"'s Score is "+ otscore+"\nYour score is "+score+ "\nYou win");
        }
        else{
            scl.setText(othplayername +"'s Score is "+ otscore+"\nYour score is "+score+  "\nYou lose");
        }
        stage.close();
        Pane newpane = new Pane(scl);
        Scene scene11= new Scene(newpane,300,200);
        Stage ns= new Stage();
        ns.setScene(scene11);
        ns.show();
    }
    
}
