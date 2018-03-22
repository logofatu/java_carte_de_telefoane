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
class CnpExistException extends IllegalArgumentException {
    private final String cnpExist;

    public CnpExistException(String c) {
        this.cnpExist = c;
    }
    
    public String getCnpExist(){
        return cnpExist;
    }
    
    public String getMessage(){
        return "Exista inregistrat deja un abonat cu cnp-ul: " + cnpExist;
    }
}
