/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

//import java.io.File;
//import java.io.Serializable;
//import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
//import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author crist
 */

public class CarteDeTelefon extends AbstractTableModel{
    public static List<Abonat> abonati = new LinkedList();

    @Override
    public int getRowCount() {
       return abonati.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int linie, int coloana) {
        Abonat abonat = abonati.get(linie);
        switch(coloana){
            case 0: return abonat.getNume();
            case 1: return abonat.getPrenume();
            case 2: return abonat.getCnp();
            case 3: return abonat.getTel();
            default: return "ERROR";
        }
    }
    
     public void adaugareAbonat(Abonat a){
         abonati.add(a);
         fireTableRowsInserted(abonati.size()-1, abonati.size()-1);
     }
     
     public void refreshCarte(){
         fireTableRowsInserted(abonati.size()-1, abonati.size()-1);
     }
     
     public String getColumnName(int col) {
         return new String[]{"Nume","Prenume","CNP","Telefon"}[col];
     }
     public String[] getColumnsName() {
         return new String[]{"Nume","Prenume","CNP","Telefon"};
     }
     
     public boolean avemAbonati(){
         if (abonati.size()>=1){
             return true;
         }
         return false;
     }
     
     @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 3){
            return false;
        }
        return true;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
      Abonat abonat = abonati.get(rowIndex);
      switch(columnIndex) {
        case 0:
        abonat.setNume((String)aValue);
        break;
        case 1: 
        abonat.setPrenume((String)aValue);
        break;
        case 2:
        abonat.setCnp((String)aValue);
        break;
      }
    fireTableDataChanged();
    }
    
    public void setAbonati(List<Abonat> a){
        this.abonati = a;
        refreshCarte();
    }
    public List<Abonat> getAbonati(){
        return abonati;
    }
}