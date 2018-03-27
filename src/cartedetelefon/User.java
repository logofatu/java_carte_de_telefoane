/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

import java.io.File;
import java.io.Serializable;
//import java.util.LinkedList;
//import java.util.List;

/**
 *
 * @author crist
 */
public class User implements Serializable {
    private static final String ACTIVATION = "12345";
    private boolean registered = false;
//    private static List<Abonat> abonati = new LinkedList();
    private File pathToSave;

    public void setRgisterd(boolean b){
        registered = b;
    }
        
    public boolean isRegistered(){
//            check on hard disck if user was registered earlier + registered = true/false
        return registered;
    }
    
    public void setPathToSave(File f){
        pathToSave = f;
    }
    
    public File getPathToSave(){
        return pathToSave;
    }

    public void register(String s){
        if (s.equals(ACTIVATION)) {
//            save on hard disck that user is successfull registered, after that set registered on true
            registered = true;
        }else{
            throw new IllegalArgumentException("Codul de activare este incorect!");
        }
    }
}
