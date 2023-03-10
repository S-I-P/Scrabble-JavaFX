
package newpackage;

import java.io.Serializable;


public class info implements Serializable{
    String s[][]= new String[15][15];
    int [][]wm= new int [15][15];
    int [][]lm= new int [15][15];
    info(){
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
               
                if((i==0 && j==0) || (i==0 && j==7) || (i==0 && j==14) || (i==7 && j==0) || (i==7 && j==14) || (i==14 && j==0) || (i==14 && j==7) || (i==14 && j==14) ){
                   lm[i][j]=1;
                   wm[i][j]=3;
                }
                else if( ((i>0 && i<5) || (i>9 && i<14)) && (j==i || j== (14-i) ) ){
                   lm[i][j]=1;
                   wm[i][j]=2;
                }
               
                else if( ( (i==1 || i==5 || i==9 || i==13)  && (j==5 || j== 9) )  || ( (i==5 || i==9) && (j==1 || j==13 ) ) ){
                   lm[i][j]=3;
                   wm[i][j]=1;
                }
                else if( (( i==2 || i==6 || i==8 || i==12) && ( j==2 || j==6 || j==8 || j==12 )) || ( (i==0 || i==7 || i==14) && (j==3 || j==11) ) || ( (j==0 || j==14) && (i==3 || i==11) ) ){
                   lm[i][j]=2;
                   wm[i][j]=1;
                }
                else{
                   lm[i][j]=1;
                   wm[i][j]=1;
                }
            }
        }
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                s[i][j]= "";
            }
        }
    }
    
}
