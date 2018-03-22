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
class TelExistException extends IllegalArgumentException {
    private final String telExist;

    public TelExistException(String t) {
        this.telExist = t;
    }
    
    public String getTelExist(){
        return telExist;
    }
    
    public String getMessage(){
        return "Exista inregistrat deja un abonat cu numarul de telefon: " + telExist;
    }
}
