/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

import java.io.Serializable;

/**
 *
 * @author crist
 */
public class Abonat implements Serializable {
    private String nume;
    private String prenume;
    private String cnp;
    private NrTel tel;
    private static final int LENGTH = 13;

    public Abonat(String n, String p, String c, String t, String type) {
        validate(n, p, c, t, type);
        
        this.tel = new NrTel(t, type);
        this.nume = n;
        this.prenume = p;
        this.cnp = c;   
    }
    
    private boolean cnpValidator(String c){
        if (c.length()!= LENGTH){
            return false;
        }
        
        for (int i = 0; i < c.length(); i++) {
            char caracterCurent = c.charAt(i);
            if(!Character.isDigit(caracterCurent)){
                return false;
            }
        }
        
        for (int i = 0; i < CarteDeTelefon.abonati.size(); i++) {
            String existingCnp = CarteDeTelefon.abonati.get(i).getCnp();
            if (c.equals(existingCnp) && (this.cnp == null || !this.cnp.equals(c))){
                throw new CnpExistException(c);
            }
        }
        
        return true;
    } 
    
    public void validate(String n, String p, String c, String t, String type){
                
        if (n == null || n.isEmpty()) {
            throw new EmptyInput("Nume");
        }
        if (p == null || p.isEmpty()) {
            throw new EmptyInput("Prenumele");
        }
        if (c == null || c.isEmpty()) {
            throw new EmptyInput("CNP");
        }
        if (!n.matches("^[A-Za-z \\-]{2,}")) {
            throw new IllegalArgumentException("Numele nu pare de om");
        }
        if (!p.matches("^[A-Za-z \\-]{2,}")) {
            throw new IllegalArgumentException("Prenumele nu pare de om");
        }
        if (!cnpValidator(c)) {
           throw new CnpException(c);
        }
    }
    
    public String getNume(){
        return nume;
    }
    public String getPrenume(){
        return prenume;
    }
    public String getCnp(){
        return cnp;
    }
    public NrTel getTel(){
        return tel;
    }
    
    public void setNume(String s){
        if (s == null || s.isEmpty()) {
            throw new EmptyInput("Nume");
        }
        
        if (!s.matches("^[A-Za-z \\-]{2,}")) {
            throw new IllegalArgumentException("Numele nu pare de om");
        }
        
        this.nume = s;
    }
    
    public void setPrenume(String s){
        if (s == null || s.isEmpty()) {
            throw new EmptyInput("Prenume");
        }
        
        if (!s.matches("^[A-Za-z \\-]{2,}")) {
            throw new IllegalArgumentException("Prenumele nu pare de om");
        }
        this.prenume = s;
    }
    
    public void setCnp(String s){
        if (s == null || s.isEmpty()) {
            throw new EmptyInput("CNP");
        }
        
        if (!cnpValidator(s)) {
           throw new CnpException(s);
        }
        
        this.cnp = s;
    }
    
    public void setTel(NrTel t){
        this.tel = t;
    }
    
    @Override
    public String toString() {
        return nume + " " + prenume + ", cnp: " + cnp + ", tel: " + tel;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Abonat other = (Abonat)obj;
        String otherNume = other.getNume();
        String otherPrenume = other.getPrenume();
        String otherCnp = other.getCnp();
        NrTel otherTel = other.getTel();
        return this.nume.equals(otherNume) && this.prenume.equals(otherPrenume) && this.cnp.equals(otherCnp) && this.tel.equals(otherTel);
    }
    
    public void Edit(String n, String p, String c, String t, String type){
        validate(n, p, c, t, type);
        
        this.tel.editTel(t, type);
        this.nume = n;
        this.prenume = p;
        this.cnp = c; 
    }
}
