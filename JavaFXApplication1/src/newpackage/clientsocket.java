/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;



public class clientsocket implements Runnable{
    int port;

    public clientsocket(int p) {
        port = p;
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        while (true) {            
            
        }
    }
    
}
