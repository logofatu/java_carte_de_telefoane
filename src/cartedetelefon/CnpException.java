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
public class CnpException extends IllegalArgumentException {
    private final String cnpInvalid;

    public CnpException(String c) {
        cnpInvalid = c;
    }

    public String getCnpInvalid() {
        return cnpInvalid;
    }

}
