
package newpackage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import javafx.fxml.FXML;


public class controller {
    private Server s;
   
    @FXML
    void donepressed() throws IOException{
        ObjectOutputStream oos= new ObjectOutputStream(s.s.getOutputStream());
        oos.writeObject(s.list);
    }
}
