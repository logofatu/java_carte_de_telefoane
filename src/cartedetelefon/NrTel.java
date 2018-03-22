/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author crist
 */
public class NrTel {
    private String numar;
    private String type;
    
    public NrTel(String n, String t){
        editTel(n, t);
    }
    
    public void editTel(String n, String t){
        if (t == null || t.isEmpty()) {
            throw new EmptyInput("TIP NUMAR DE TELEFON");
        }
        
        if (n == null || n.isEmpty()) {
            throw new EmptyInput("Telefon");
        }
                
        Matcher matcher;
       
        if (t.equals("fix")) {
            Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{2}");
            matcher = pattern.matcher(n);
        }else{
            Pattern pattern = Pattern.compile("[0-9]{4}\\.?[0-9]{3}\\.?[0-9]{3}$");
            matcher = pattern.matcher(n);
        }
        
        if (!matcher.matches()) {
           throw new TelException(n, t);
        }
  
//        If it needs to be unique tel numbers, need to uncomment:
//        for (int i = 0; i < CarteDeTelefon.abonati.size(); i++) {
//            String existingNumar = CarteDeTelefon.abonati.get(i).getTel().getNumar();
//            if (n.equals(existingNumar) && (this.numar == null || !this.numar.equals(n))){
//                throw new TelExistException(n);
//            }
//        }
        
        this.type = t;
        this.numar = n;
    }
    
    public String getNumar(){
        return numar;
    }
    
    public String getType(){
        return type;
    } 
    
    public void setNumar(String n){
        if (n == null || n.isEmpty()) {
            throw new EmptyInput("Telefon");
        }
        
        Matcher matcher;
       
        if (type.equals("fix")) {
            Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{2}");
            matcher = pattern.matcher(n);
        }else{
            Pattern pattern = Pattern.compile("[0-9]{4}\\.?[0-9]{3}\\.?[0-9]{3}$");
            matcher = pattern.matcher(n);
        }
        
        if (!matcher.matches()) {
           throw new TelException(n, type);
        }
        
        this.numar = n;
    }
    
    public void setType(String t){
        if (t == null || t.isEmpty()) {
            throw new EmptyInput("TIP NUMAR DE TELEFON");
        }
        
        Matcher matcher;
       
        if (t.equals("fix")) {
            Pattern pattern = Pattern.compile("\\d{3}-\\d{7}");
            matcher = pattern.matcher(numar);
        }else{
            Pattern pattern = Pattern.compile("[0-9]{4}\\.?[0-9]{3}\\.?[0-9]{3}$");
            matcher = pattern.matcher(numar);
        }
        
        if (!matcher.matches()) {
           throw new TelException(numar, t);
        }
        
        this.type = t;
    }
    
    public String toString(){
        return(numar + " - " + type);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final NrTel other = (NrTel)obj;
        String otherNumar = other.getNumar();
        String otherType = other.getType();
        
        return this.numar.equals(otherNumar) && this.type.equals(otherType);
    }
}
