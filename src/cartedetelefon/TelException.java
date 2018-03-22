/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

/**
 *
 * @author crist
 */
class TelException extends IllegalArgumentException {
    private final String telInvalid;
    private final String type;

    public TelException(String t, String type) {
        telInvalid = t;
        this.type = type;
    }

    public String getTelInvalid() {
        return telInvalid;
    }
    
    public String getType(){
        return type;
    }
    
    public String getMessage(){
        return "Numarul " + telInvalid + " este un numar de " + type + " invalid!";
    }
}
