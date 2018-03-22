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
class EmptyInput extends IllegalArgumentException {
    private final String campGol;

    public EmptyInput(String t) {
        campGol = t;
    }

    public String getCampGol() {
        return campGol;
    }
    
    public String getMessage(){
        return "Campul " + campGol + " trebuie completat!";
    }
}
