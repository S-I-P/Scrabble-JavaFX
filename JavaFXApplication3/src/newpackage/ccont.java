/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newpackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.fxml.FXML;

/**
 *
 * @author WINDOWS
 */
public class ccont {
    private Client s;
    
    @FXML
    void donepressed() throws IOException{
        ObjectOutputStream oos= new ObjectOutputStream(s.s.getOutputStream());
        oos.writeObject(s.list);
    }
}
