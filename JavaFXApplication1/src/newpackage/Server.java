
package newpackage;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

class sth extends Thread{
    Socket s;
    Hashtable table;
    Hashtable currentusers;
    DataInputStream dis;
    DataOutputStream dos;
    
    String username,password;
    public sth(Socket s,Hashtable table, Hashtable online) {
        this.s = s;
        try {
            dis= new DataInputStream(s.getInputStream());
            dos= new DataOutputStream(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(sth.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentusers= online;
        this.table= table;
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {                
                String str= dis.readUTF();
                System.out.println(str);
                if(str.equals("OLD")){
                    dos.writeUTF("USERNAME");
                    str= dis.readUTF();
                    System.out.println(str);
                    if(((table.isEmpty()) || (table.get(str) == null)) ) {
                        dos.writeUTF("Taken");
                    }
                    else{
                        username= str;
                        dos.writeUTF("Not taken");
                    }
                }
                else if(str.equals("NEW")){
                    dos.writeUTF("USERNAME");
                    str= dis.readUTF();
                    System.out.println(str);
                    if(((table.isEmpty()) || (table.get(str) == null))==false ) {
                        dos.writeUTF("Taken");
                    }
                    else{
                        username= str;
                        dos.writeUTF("Not taken");
                    }
                }
                else if(str.equals("NEWPASS")){
                    dos.writeUTF("PASSWORD");
                    str= dis.readUTF();
                    System.out.println(str);
                    password= str;
                    table.put(username, password);
                    currentusers.put(username, Server.port);
                    dos.writeUTF(Server.port.toString());
                    Server.port+=2;
                    
                    FileWriter f= new FileWriter("Users.txt", true);
                    FileWriter fp= new FileWriter("Passwords.txt", true);
                    username= username+ "\n";
                    password= password+ "\n";
                    f.write(username);
                    fp.write(password);
                    f.close();
                    fp.close();
                }
                else if(str.equals("OLDPASS")){
                    dos.writeUTF("PASSWORD");
                    str= dis.readUTF();
                    System.out.println(str);
                    System.out.println(username);
                    if(table.get(username).equals(str)){
                        dos.writeUTF("RIGHT");
                        currentusers.put(username, Server.port);
                        Server.port+=2;
                    }
                    else{
                        System.out.println("Wrong Password");
                        dos.writeUTF("WRONG");
                    }
                }
                else if(str.equals("PORT")){
                    dos.writeUTF(currentusers.get(username).toString());
                }
                else if(str.equals("LIST")){
                    Enumeration list= currentusers.keys();
                   
                    dos.writeInt(currentusers.size());
                    str= dis.readUTF();
                    if(str.equals("GOT")){
                        while (list.hasMoreElements()) {
                            Object nextElement = list.nextElement();
                            dos.writeUTF(nextElement.toString());
                            str= dis.readUTF();
                            System.out.println(str);
                        }
                    }
                }
                else if(str.equals("BYE")){
                    currentusers.remove(username);
                    break;
                }
                else if(str.equals("OTHER")){
                    dos.writeUTF("NAME");
                    str= dis.readUTF();
                    System.out.println(str);
                    if(currentusers.get(str)==null){
                        dos.writeUTF("taken");
                    }
                    else{
                        dos.writeUTF(currentusers.get(str).toString());
                    }
                }
            }
            s.close();
            
        } catch (IOException ex) {
            Logger.getLogger(sth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}

public class Server {
    static Integer port= 1031;
    public static void main(String[] args) throws IOException {
        ServerSocket server= new ServerSocket(1030);
        Hashtable table= new Hashtable();
        Hashtable online= new Hashtable();
        FileWriter f= new FileWriter("Users.txt", true);
        FileWriter fp= new FileWriter("Passwords.txt", true);
        f.close();
        fp.close();
        File f1= new File("Users.txt");
        File fp1= new File("Passwords.txt");
        System.out.println(f1.length());
        char []target= new char[(int)f1.length()];
        FileReader f2= new FileReader(f1);
        f2.read(target);
        String str= new String(target);
        FileReader fp2= new FileReader(fp1);
        fp2.read(target);
        String strp= new String(target);
        if (str.length()>0) {
            String[] info  = str.split("\n");
            String []pass = strp.split("\n");
            System.out.println(info.length);
                for (int i = 0; i < info.length; i++) {
                    table.put(info[i], pass[i]);
                }
            
        }
        System.out.println(table.size());
        f2.close();
        fp2.close();
        while (true) {            
            Socket client= server.accept();
            new sth(client,table,online);
        }
    }
}
