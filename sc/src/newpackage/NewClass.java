
package newpackage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;

public class NewClass extends Application{
    @FXML
    Rectangle rec1,rec2;
    @FXML
    TextField pl1,pl2;
    
    static Rectangle []stock1= new Rectangle[7];
    Rectangle []stock2= new Rectangle[7];
    static Text []letter= new Text[7];
    int initx= 50;
    int []inity={200,265,330,395,460,525,590};    
    static String []temp= new String[7];
    Pane root;
    String player1,player2;
    public static Stage stage;
    boolean flag= true;
    Rectangle [][]board= new Rectangle[15][15];
    int cox;
    int coy= 15;
    public static LinkedList<String> list= new LinkedList<String>();

    static String [][]tempentry= new String[15][15];
    static String [][]entry= new String[15][15];
    static String [][]myentry= new String[15][15];
    boolean fmove= false;
    
    static ArrayList<String> dictionary= new ArrayList<>();
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
    
        System.out.println(temp.length);
        Button play= new Button("Play");
        play.setLayoutX(100);
        play.setLayoutY(600);
        InnerShadow is= new InnerShadow(BlurType.THREE_PASS_BOX, Color.rgb(33,82,180),10, 0.5, 0, 0);
        is.setHeight(46.65);
        is.setWidth(60.09);
        play.setPrefSize(100, 40);
        play.setFont(new Font("Arial Black", 16));
        play.setTextFill(Color.DARKBLUE);
        play.setEffect(is);
        File img= new File("scrabble.jpg");
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
        
        for(i=0;i<15;i++){
            for(int j=0;j<15;j++){
                entry[i][j]="$$";
                tempentry[i][j]="$$";
            }
        }
        
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
        for(i=0;i<7;i++)    {
            temp[i]= list.poll();
            letter[i]= new Text(temp[i]);
            Text t= letter[i];
            t.setFill(Color.DARKSLATEBLUE);
            t.setFont(new Font(50));
            t.setX(initx+20);
            t.setY(inity[i]+40);
            t.setBoundsType(TextBoundsType.VISUAL);
        }
        
        Image image = new Image(img.toURI().toURL().toString());
        ImageView view= new ImageView();
        view.setImage(image);
        view.setPreserveRatio(true);
        view.setLayoutX(300);
        view.setLayoutY(300);
        play.setOnAction(e-> {
            try {
                enterplayername();
            } catch (IOException ex) {
                Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stage= primaryStage;
        StackPane root1= new StackPane();
        root1.getChildren().addAll(view,play);
        
        Scene scene1 = new Scene(root1,1000,800,Color.ALICEBLUE);
        stage.setScene(scene1);
        primaryStage.show();
    }
    
    void showboard(){
        
        StrokeType st= StrokeType.INSIDE;
        root= new Pane();
        for(int i=0;i<15;i++){
            cox= 380;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 70, 60);
                board[i][j].setFill(Color.LIGHTBLUE);
                board[i][j].setStrokeType(st);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                root.getChildren().add(board[i][j]);
                
                
                cox+=75;
            }
            coy+= 65;
        }
        
        for(int i=0;i<7;i++){
            stock1[i]= createrectangle(initx, inity[i], 66, 56, letter[i]);
            stock1[i].setFill(Color.CORNSILK);
            stock1[i].setStrokeType(st);
            stock1[i].setStroke(Color.DARKGRAY);
            stock1[i].setStrokeWidth(3);
            root.getChildren().addAll(stock1[i],letter[i]);
        }
        
        Button done= new Button("Done");
        
        done.setLayoutX(1700);
        done.setLayoutY(400);
        done.setOnAction(e-> donepressed());
        root.getChildren().add(done);
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
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
                t.setY(newYPosition+40);
            }
        });
        rect.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                boolean temp= false;
                for(int i=0;i<15;i++){
                    for(int j=0;j<15;j++){
                        if(rect.getX()>= board[i][j].getX() && (rect.getX()+ 66)<= (board[i][j].getX()+70) && rect.getY()>= board[i][j].getY() && (rect.getY()+ 56)<= (board[i][j].getY()+60)  && entry[i][j].equals("$$")){
                            rect.setX(board[i][j].getX()+2);
                            rect.setY(board[i][j].getY()+2);
                            t.setX(board[i][j].getX()+2+20);
                            t.setY(board[i][j].getY()+2+40);
                            
                            temp= true;
                        }             
                    }
                    if(temp)    break;
                }
                if(temp== false){
                    rect.setX(px);
                    rect.setY(py);
                    t.setX(px+20);
                    t.setY(py+40);
                }
            }
        });
        return rect;
    }
    
    /*
    void enterplayername(){
        Pane root= new Pane();
        TextField pl1,pl2;
        Text plt1,plt2;
        
        Rectangle rec1= new Rectangle(100, 400-70, 670, 60);
        Rectangle rec2= new Rectangle(100, 400+10, 670, 60);
        plt1= new Text(rec1.getX()+20, rec1.getY()+ 45, "Enter Name of Player 1 :");
        plt1.setFont(new Font("Copperplate Gothic Bold", 45));
        plt2= new Text(rec2.getX()+20, rec2.getY()+ 45, "Enter Name of Player 2 :");
        plt2.setFont(new Font("Copperplate Gothic Bold", 45));
        rec1.setStrokeType(StrokeType.INSIDE);
        rec2.setStrokeType(StrokeType.INSIDE);
        rec1.setStroke(Color.HONEYDEW);
        rec2.setStroke(Color.HONEYDEW);
        rec1.setFill(Color.ALICEBLUE);
        rec2.setFill(Color.ALICEBLUE);
        rec1.setStrokeWidth(3);
        rec2.setStrokeWidth(3);
        rec1.setStroke(Color.DARKSLATEGREY);
        rec2.setStroke(Color.DARKSLATEGREY);
        pl1= new TextField();
        pl2= new TextField();
        pl1.setPrefSize(300, 60);
        pl2.setPrefSize(300, 60);
        
        root.getChildren().addAll(rec1,rec2,plt1,plt2);
        Scene scene = new Scene(root,1500,800,Color.LIGHTSTEELBLUE);
        stage.setScene(scene);
    }
    */
    
    AnchorPane root1= new AnchorPane();
    
    void enterplayername() throws IOException{
        root1= FXMLLoader.load(getClass().getResource("playernm.fxml"));
        Scene scene = new Scene(root1);
        stage.setScene(scene);
    }
    
    @FXML
    void getnames(){
        player1= pl1.getText();
        player2= pl2.getText();
        System.out.println(player1+"\n"+player2);
        showboard();
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
    
    void donepressed(){
        
        for(int rec=0;rec<7;rec++){
            Text t= letter[rec];
            for(int i=0;i<15;i++){
                for(int j=0;j<15;j++){
                    if(t.getX()==(board[i][j].getX()+2+20) && t.getY()== (board[i][j].getY()+2+40) ){
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
            letter[i]= new Text(temp[i]);
            Text t= letter[i];
            t.setFill(Color.DARKSLATEBLUE);
            t.setFont(new Font(50));
            t.setX(initx+20);
            t.setY(inity[i]+40);
            t.setBoundsType(TextBoundsType.VISUAL);
        }
        coy=15;
        for(int i=0;i<15;i++){
            cox= 380;
            for(int j=0;j<15;j++){
                board[i][j]= new Rectangle(cox, coy, 70, 60);
                
                board[i][j].setStrokeType(StrokeType.INSIDE);
                board[i][j].setStroke(Color.DARKGRAY);
                board[i][j].setStrokeWidth(3);
                
                cox+=75;
                if(entry[i][j].equals("$$")==false){
                    Text t= new Text(entry[i][j]);
                    t.setFill(Color.DARKGREEN);
                    t.setFont(new Font(50));
                    t.setX(board[i][j].getX()+24);
                    t.setY(board[i][j].getY()+44);
                    t.setBoundsType(TextBoundsType.VISUAL);
                    root.getChildren().add(t);
                }
                else{
                    board[i][j].setFill(Color.LIGHTBLUE);
                    root.getChildren().add(board[i][j]);
                }
                
            }
            coy+= 65;
            
        }
        for(int i=0;i<7;i++){
            stock1[i]= createrectangle(initx, inity[i], 66, 56, letter[i]);
            stock1[i].setFill(Color.CORNSILK);
            stock1[i].setStrokeType(StrokeType.INSIDE);
            stock1[i].setStroke(Color.DARKGRAY);
            stock1[i].setStrokeWidth(3);
            root.getChildren().addAll(stock1[i],letter[i]);
        }
        
        Button done= new Button("Done");
        
        done.setLayoutX(1700);
        done.setLayoutY(400);
        done.setOnAction(e-> donepressed());
        root.getChildren().add(done);
        
        Scene scene= new Scene(root);
        stage.setScene(scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
