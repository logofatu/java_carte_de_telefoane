/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartedetelefon;

import static cartedetelefon.CarteDeTelefon.abonati;
import static java.awt.Desktop.Action.EDIT;
import static java.awt.Event.DELETE;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import reclame.Reclame;

/**
 *
 * @author crist
 */
public class Aplicatie extends javax.swing.JFrame {
    /**
     * Creates new form Aplicatie
     */
    private final int NRRECLAME = 5;
    private Reclame r = new Reclame();
    private List<Reclame> listaReclame = new ArrayList<>();
    private CarteDeTelefon model = new CarteDeTelefon();
    private int confirmEdit;
    private String toolTipMobil = "Phone Number must be in the form XXXX.XXX.XXX";
    private String labelMobil = "<html>Telefon <br> XXXX.XXX.XXX</html>";
    private String toolTipFix = "Phone Number must be in the form XXX-XX-XX";
    private String labelFix = "<html>Telefon <br> XXX-XX-XX</html>";
    private Abonat editAbonat;
    private static final String ACTIVATION = "12345";

//    private String labels[] = model.getColumnsName();
    
    public Aplicatie() {
        initComponents();
        mainPane.setVisible(false);
        jLabel15.setVisible(false);
        jButton12.setVisible(false);
        tAbonati.setModel(model);
        enableOrDisableButtons();
        dAbout.setLocationRelativeTo(null);
        dOrder.setLocationRelativeTo(null);
        dFilter.setLocationRelativeTo(null);
       
        jComboBox1.setModel(new DefaultComboBoxModel(model.getColumnsName()));
        jComboBox3.setModel(new DefaultComboBoxModel(model.getColumnsName()));
        jComboBox5.setModel(new DefaultComboBoxModel(model.getColumnsName()));
        jComboBox7.setModel(new DefaultComboBoxModel(model.getColumnsName()));
        jComboBox9.setModel(new DefaultComboBoxModel(model.getColumnsName()));
        
        jComboBox2.setModel(new DefaultComboBoxModel(SortOrder.values()));
        jComboBox4.setModel(new DefaultComboBoxModel(SortOrder.values()));
        jComboBox6.setModel(new DefaultComboBoxModel(SortOrder.values()));
        jComboBox8.setModel(new DefaultComboBoxModel(SortOrder.values()));
        
        textDespre.setEditable(false);
        dModifica.setLocationRelativeTo(null);
        tAbonati.setAutoCreateRowSorter(true);
        tAbonati.setRowSelectionAllowed(true);
        tAbonati.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        tAbonati.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    editeazaAbonatiiSelectati();
                }
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    stergeAbonatiiSelectati();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

//        tAbonati.getModel().addTableModelListener(new TableModelListener() {
//            @Override
//            public void tableChanged(TableModelEvent tme) {
//                System.out.println("A intrat in lia 102");
//            }
//        });
        

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if(e instanceof CnpException) {
                    JOptionPane.showMessageDialog(mainPane, "CNP invalid!","Eroare", JOptionPane.ERROR_MESSAGE);
                } else if(e instanceof IllegalArgumentException) {
                    JOptionPane.showMessageDialog(mainPane,e.getMessage(),"Eroare", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
//for Edit on double-click:        
//        tAbonati.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent mouseEvent) {
//                JTable table =(JTable) mouseEvent.getSource();
//                Point point = mouseEvent.getPoint();
//                int row = table.rowAtPoint(point);
//                int col = table.columnAtPoint(point);
//                if (mouseEvent.getClickCount() == 2) {
//                    editeazaAbonatiiSelectati();
//                }
//            }
//        });
        
        afisareReclame();
        telType.add(mobil);
        telType.add(fix);
        editTelType.add(editMobil);
        editTelType.add(editFix);
        
       Timer splashScreen = new Timer();
        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                splash.setVisible(false);
                mainPane.setVisible(true);
            }
        };
        splashScreen.schedule(task, 2000);
        
    }

    private void afisareReclame(){
        for (int i = 1; i <= NRRECLAME; i++) {
            Reclame r = new Reclame();
            listaReclame.add(r);
            rZone.add(r);
        }

        for(Reclame r:listaReclame){
            new Thread(r).start();
        }
    }
    
    private void enableOrDisableButtons(){
        if (model.avemAbonati()) {
            jButton1.setEnabled(true);
            jButton3.setEnabled(true);
            jButton4.setEnabled(true);
            jButton5.setEnabled(true);
            jMenuItem5.setEnabled(true);
            jMenuItem6.setEnabled(true);
            jMenuItem7.setEnabled(true);
            jMenuItem8.setEnabled(true);
        }else{
            jButton1.setEnabled(false);
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jMenuItem5.setEnabled(false);
            jMenuItem6.setEnabled(false);
            jMenuItem7.setEnabled(false);
            jMenuItem8.setEnabled(false);
        }
    }
    
    private void orderBy(){
        dOrder.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dAbout = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        textDespre = new javax.swing.JTextArea();
        dAdauga = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        addNume = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        addPrenume = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        addCnp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        addTelefon = new javax.swing.JTextField();
        mobil = new javax.swing.JRadioButton();
        fix = new javax.swing.JRadioButton();
        jButton7 = new javax.swing.JButton();
        telType = new javax.swing.ButtonGroup();
        dModifica = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        editNume = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        editPrenume = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        editCnp = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        editTelefon = new javax.swing.JTextField();
        editMobil = new javax.swing.JRadioButton();
        editFix = new javax.swing.JRadioButton();
        jButton8 = new javax.swing.JButton();
        editTelType = new javax.swing.ButtonGroup();
        dOrder = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jComboBox6 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jComboBox7 = new javax.swing.JComboBox<>();
        jComboBox8 = new javax.swing.JComboBox<>();
        dFilter = new javax.swing.JDialog();
        filterText = new javax.swing.JTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        splash = new javax.swing.JLabel();
        mainPane = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tAbonati = new javax.swing.JTable();
        rZone = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        mAbonati = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        mHelp = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        about = new javax.swing.JMenuItem();

        dAbout.setTitle("About");
        dAbout.setAlwaysOnTop(true);
        dAbout.setIconImage(null);
        dAbout.setMinimumSize(new java.awt.Dimension(500, 500));
        dAbout.setModal(true);
        dAbout.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        dAbout.setPreferredSize(new java.awt.Dimension(600, 600));
        dAbout.setSize(new java.awt.Dimension(600, 600));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 500));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 500));

        textDespre.setColumns(20);
        textDespre.setRows(5);
        textDespre.setText("Carte de telefoane\n\nAutor: Galina Logofatu\nVersiune: 1.0\nCreat pentru: curs Java 9 Decembrie 2017\nTermen de predare: 9 Aprilie 2018\n\nFacilitati:\npozele pentru reclame se iau dinamic;\nse poate defini cate poze de reclama sa apara in josul paginii\nla dublu-click pe celula din tabel - se poate edita inline celula(se updateaza Abonatul)\ncand sant selectate mai multe randuri, nu meaparat consecutive:\n\t- tasta DEL - intreaba daca vreu sa stergi fiecare abonat din selectie \n\t- tasta Enter - intreaba daca vreu sa modific fiecare abonat din selectie \nDaca nu este nici un abonat in lista, unele butoane sant dezactivate\nOrdonare: apasarea pe capul de tabel sau actionarea budonului de ordonare sau din meniu\nCautarea sau filtrarea se poate face dupa o anumita coloana sau dupa toate coloanele(cu operatorul OR)\nPentru nr de tel unice in Cartea de telefoane trebuie decomentat codul din clasa NrTel, in metoda: editTel;\nAcum se pot inregistra mai multi abonati cu acelasi numar.");
        jScrollPane1.setViewportView(textDespre);

        dAbout.getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        dAbout.getAccessibleContext().setAccessibleDescription("");
        dAbout.getAccessibleContext().setAccessibleParent(this);

        dAdauga.setTitle("Adauga Abonat");
        dAdauga.setMaximumSize(new java.awt.Dimension(600, 400));
        dAdauga.setMinimumSize(new java.awt.Dimension(600, 400));
        dAdauga.setModal(true);
        dAdauga.setPreferredSize(new java.awt.Dimension(600, 400));
        dAdauga.getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Nume");
        jLabel1.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(jLabel1, gridBagConstraints);
        jLabel1.getAccessibleContext().setAccessibleName("Nume");

        addNume.setMaximumSize(new java.awt.Dimension(500, 30));
        addNume.setMinimumSize(new java.awt.Dimension(200, 30));
        addNume.setPreferredSize(new java.awt.Dimension(200, 30));
        addNume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNumeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(addNume, gridBagConstraints);

        jLabel2.setText("Prenume");
        jLabel2.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel2.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(jLabel2, gridBagConstraints);

        addPrenume.setMaximumSize(new java.awt.Dimension(500, 30));
        addPrenume.setMinimumSize(new java.awt.Dimension(200, 30));
        addPrenume.setPreferredSize(new java.awt.Dimension(200, 30));
        addPrenume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPrenumeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(addPrenume, gridBagConstraints);

        jLabel3.setText("CNP");
        jLabel3.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel3.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel3.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(jLabel3, gridBagConstraints);

        addCnp.setMaximumSize(new java.awt.Dimension(500, 30));
        addCnp.setMinimumSize(new java.awt.Dimension(200, 30));
        addCnp.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(addCnp, gridBagConstraints);

        jLabel4.setText("Telefon");
        jLabel4.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel4.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 59;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(jLabel4, gridBagConstraints);

        addTelefon.setMaximumSize(new java.awt.Dimension(500, 30));
        addTelefon.setMinimumSize(new java.awt.Dimension(200, 30));
        addTelefon.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 59;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dAdauga.getContentPane().add(addTelefon, gridBagConstraints);

        mobil.setText("Mobil");
        mobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mobilActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 0, 18);
        dAdauga.getContentPane().add(mobil, gridBagConstraints);

        fix.setText("Fix");
        fix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        dAdauga.getContentPane().add(fix, gridBagConstraints);

        jButton7.setMnemonic('g');
        jButton7.setText("Adauga");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 60;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        dAdauga.getContentPane().add(jButton7, gridBagConstraints);

        dModifica.setTitle("Modifica Abonat");
        dModifica.setMaximumSize(new java.awt.Dimension(600, 400));
        dModifica.setMinimumSize(new java.awt.Dimension(600, 400));
        dModifica.setModal(true);
        dModifica.setPreferredSize(new java.awt.Dimension(600, 400));
        dModifica.getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel6.setText("Nume");
        jLabel6.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel6.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(jLabel6, gridBagConstraints);

        editNume.setMaximumSize(new java.awt.Dimension(500, 30));
        editNume.setMinimumSize(new java.awt.Dimension(200, 30));
        editNume.setPreferredSize(new java.awt.Dimension(200, 30));
        editNume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editNumeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(editNume, gridBagConstraints);

        jLabel7.setText("Prenume");
        jLabel7.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel7.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(jLabel7, gridBagConstraints);

        editPrenume.setMaximumSize(new java.awt.Dimension(500, 30));
        editPrenume.setMinimumSize(new java.awt.Dimension(200, 30));
        editPrenume.setPreferredSize(new java.awt.Dimension(200, 30));
        editPrenume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPrenumeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(editPrenume, gridBagConstraints);

        jLabel8.setText("CNP");
        jLabel8.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel8.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel8.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(jLabel8, gridBagConstraints);

        editCnp.setMaximumSize(new java.awt.Dimension(500, 30));
        editCnp.setMinimumSize(new java.awt.Dimension(200, 30));
        editCnp.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(editCnp, gridBagConstraints);

        jLabel9.setText("Telefon");
        jLabel9.setMaximumSize(new java.awt.Dimension(500, 30));
        jLabel9.setMinimumSize(new java.awt.Dimension(100, 30));
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 59;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(jLabel9, gridBagConstraints);

        editTelefon.setMaximumSize(new java.awt.Dimension(500, 30));
        editTelefon.setMinimumSize(new java.awt.Dimension(200, 30));
        editTelefon.setPreferredSize(new java.awt.Dimension(200, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 59;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(9, 10, 9, 10);
        dModifica.getContentPane().add(editTelefon, gridBagConstraints);

        editMobil.setText("Mobil");
        editMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMobilActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 57;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 0, 18);
        dModifica.getContentPane().add(editMobil, gridBagConstraints);

        editFix.setText("Fix");
        editFix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editFixActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 57;
        gridBagConstraints.insets = new java.awt.Insets(0, 18, 0, 18);
        dModifica.getContentPane().add(editFix, gridBagConstraints);

        jButton8.setMnemonic('m');
        jButton8.setText("Modifica");
        jButton8.setToolTipText("");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 60;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        dModifica.getContentPane().add(jButton8, gridBagConstraints);

        dOrder.setLocation(new java.awt.Point(0, 0));
        dOrder.setMinimumSize(new java.awt.Dimension(400, 400));
        dOrder.setModal(true);
        dOrder.setName("Order by!"); // NOI18N
        dOrder.setPreferredSize(new java.awt.Dimension(400, 400));
        dOrder.getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Order By ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jLabel5, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox2, gridBagConstraints);

        jButton9.setMnemonic('o');
        jButton9.setText("Ordoneaza!");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 18, 0);
        dOrder.getContentPane().add(jButton9, gridBagConstraints);

        jLabel10.setText("then by:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jLabel10, gridBagConstraints);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox4, gridBagConstraints);

        jLabel11.setText("then by:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jLabel11, gridBagConstraints);

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox6, gridBagConstraints);

        jLabel12.setText("al last by:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jLabel12, gridBagConstraints);

        jComboBox7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox7, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        dOrder.getContentPane().add(jComboBox8, gridBagConstraints);

        dFilter.setTitle("Filter");
        dFilter.setMinimumSize(new java.awt.Dimension(500, 300));
        dFilter.setPreferredSize(new java.awt.Dimension(500, 300));
        dFilter.getContentPane().setLayout(new java.awt.GridBagLayout());

        filterText.setMinimumSize(new java.awt.Dimension(100, 30));
        filterText.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(14, 14, 14, 14);
        dFilter.getContentPane().add(filterText, gridBagConstraints);

        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox9.setMinimumSize(new java.awt.Dimension(100, 30));
        jComboBox9.setPreferredSize(new java.awt.Dimension(100, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(14, 14, 14, 14);
        dFilter.getContentPane().add(jComboBox9, gridBagConstraints);

        jButton10.setMnemonic('c');
        jButton10.setText("Cauta in coloana aleasa");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(14, 14, 14, 14);
        dFilter.getContentPane().add(jButton10, gridBagConstraints);

        jButton11.setMnemonic('t');
        jButton11.setText("Caura in toate coloanele");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(14, 14, 14, 14);
        dFilter.getContentPane().add(jButton11, gridBagConstraints);

        jLabel13.setText("Cauta:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        dFilter.getContentPane().add(jLabel13, gridBagConstraints);

        jLabel14.setText("in coloana:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        dFilter.getContentPane().add(jLabel14, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1500, 750));
        setSize(new java.awt.Dimension(1500, 750));
        getContentPane().setLayout(new javax.swing.OverlayLayout(getContentPane()));

        splash.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        splash.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        splash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cartedetelefon/phone-book.jpg"))); // NOI18N
        splash.setText("Galina Logofatu");
        splash.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        splash.setAlignmentX(1.0F);
        splash.setAlignmentY(1.0F);
        splash.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        splash.setPreferredSize(new java.awt.Dimension(750, 750));
        splash.setVerifyInputWhenFocusTarget(false);
        splash.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(splash);

        mainPane.setMinimumSize(new java.awt.Dimension(750, 500));
        mainPane.setName(""); // NOI18N
        mainPane.setPreferredSize(new java.awt.Dimension(1500, 500));
        mainPane.setLayout(new java.awt.BorderLayout());

        jPanel2.setAlignmentX(5.0F);
        jPanel2.setAlignmentY(1.0F);
        jPanel2.setAutoscrolls(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 500));

        jLabel15.setText("Filter is applied!");
        jPanel2.add(jLabel15);

        jButton12.setMnemonic('r');
        jButton12.setText("Disable filter");
        jButton12.setToolTipText("Click here for showing entire phone book!");
        jButton12.setMaximumSize(new java.awt.Dimension(130, 29));
        jButton12.setMinimumSize(new java.awt.Dimension(130, 29));
        jButton12.setPreferredSize(new java.awt.Dimension(130, 29));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton12);

        jButton1.setMnemonic('c');
        jButton1.setText("Cautare");
        jButton1.setToolTipText("Cauta abonatul dupa unul din criterii");
        jButton1.setEnabled(false);
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);

        jButton2.setMnemonic('d');
        jButton2.setText("Adaugare");
        jButton2.setToolTipText("Creaza un abonat nou  si il adauga in cartea de telefoane");
        jButton2.setMaximumSize(new java.awt.Dimension(100, 30));
        jButton2.setMinimumSize(new java.awt.Dimension(100, 30));
        jButton2.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);

        jButton3.setMnemonic('s');
        jButton3.setText("Stergere");
        jButton3.setToolTipText("Sterge abonatul din cartea de telefon");
        jButton3.setEnabled(false);
        jButton3.setMaximumSize(new java.awt.Dimension(100, 30));
        jButton3.setMinimumSize(new java.awt.Dimension(100, 30));
        jButton3.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3);

        jButton4.setMnemonic('e');
        jButton4.setText("Editare");
        jButton4.setToolTipText("Editeaza abonatul");
        jButton4.setEnabled(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setMaximumSize(new java.awt.Dimension(100, 30));
        jButton4.setMinimumSize(new java.awt.Dimension(100, 30));
        jButton4.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4);

        jButton5.setMnemonic('o');
        jButton5.setText("Ordonare");
        jButton5.setToolTipText("Ordoneaza abonatii dupa criterii");
        jButton5.setEnabled(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setMaximumSize(new java.awt.Dimension(100, 30));
        jButton5.setMinimumSize(new java.awt.Dimension(100, 30));
        jButton5.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);

        jButton6.setMnemonic('i');
        jButton6.setText("Iesire");
        jButton6.setToolTipText("Inchide aplicatia");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setMaximumSize(new java.awt.Dimension(100, 30));
        jButton6.setMinimumSize(new java.awt.Dimension(100, 30));
        jButton6.setPreferredSize(new java.awt.Dimension(100, 30));
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6);

        mainPane.add(jPanel2, java.awt.BorderLayout.NORTH);

        tAbonati.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tAbonati);

        mainPane.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        rZone.setAutoscrolls(true);
        rZone.setMaximumSize(new java.awt.Dimension(32767, 100));
        rZone.setMinimumSize(new java.awt.Dimension(500, 200));
        rZone.setLayout(new java.awt.GridLayout(1, NRRECLAME));
        mainPane.add(rZone, java.awt.BorderLayout.SOUTH);

        getContentPane().add(mainPane);

        mFile.setMnemonic('f');
        mFile.setText("File");

        jMenuItem1.setMnemonic('o');
        jMenuItem1.setText("Open");
        jMenuItem1.setEnabled(false);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mFile.add(jMenuItem1);

        jMenuItem2.setMnemonic('s');
        jMenuItem2.setText("Save");
        jMenuItem2.setEnabled(false);
        mFile.add(jMenuItem2);
        mFile.add(jSeparator1);

        jMenuItem3.setMnemonic('i');
        jMenuItem3.setText("Iesire");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        mFile.add(jMenuItem3);

        jMenuBar1.add(mFile);

        mAbonati.setMnemonic('a');
        mAbonati.setText("Abonati");

        jMenuItem4.setMnemonic('d');
        jMenuItem4.setText("Adauga");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mAbonati.add(jMenuItem4);

        jMenuItem5.setMnemonic('c');
        jMenuItem5.setText("Cauta");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mAbonati.add(jMenuItem5);

        jMenuItem6.setMnemonic('s');
        jMenuItem6.setText("Sterge");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        mAbonati.add(jMenuItem6);

        jMenuItem7.setMnemonic('m');
        jMenuItem7.setText("Modifica");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        mAbonati.add(jMenuItem7);

        jMenuItem8.setMnemonic('o');
        jMenuItem8.setText("Ordoneaza");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        mAbonati.add(jMenuItem8);

        jMenuBar1.add(mAbonati);

        mHelp.setMnemonic('h');
        mHelp.setText("Help");

        jMenuItem9.setMnemonic('r');
        jMenuItem9.setText("Inregistrare");
        mHelp.add(jMenuItem9);
        mHelp.add(jSeparator2);

        about.setMnemonic('b');
        about.setText("About");
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        mHelp.add(about);

        jMenuBar1.add(mHelp);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        fereastraAdaugaAbonat();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutActionPerformed
        dAbout.setVisible(true);
    }//GEN-LAST:event_aboutActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        fereastraAdaugaAbonat();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void addNumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNumeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addNumeActionPerformed

    private void addPrenumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPrenumeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addPrenumeActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       try {
            String nume = addNume.getText();
            String prenume = addPrenume.getText();
            String cnp = addCnp.getText();
            String tel = addTelefon.getText();
            String type;
            if (mobil.isSelected()) {
                type = "mobil";
            } else if (fix.isSelected()) {
                type = "fix";
            } else{
                type = null;
            }

            Abonat a = new Abonat(nume, prenume, cnp, tel, type);
            model.adaugareAbonat(a);
            enableOrDisableButtons();
            dAdauga.setVisible(false);
//            dAdauga.dispose();
            addNume.setText("");
            addPrenume.setText("");
            addCnp.setText("");
            addTelefon.setText("");
        } catch (EmptyInput e){
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (TelException e){
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (CnpException e) {
            mesaj("CNP invalid!","Eroare",JOptionPane.ERROR_MESSAGE);
//        } catch (NumberFormatException e) {
//            mesaj("Telefonul nu e numeric","Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        }
        
    }                                          

    private void mesaj(String msg, String titlu, int tip){
        JOptionPane.showMessageDialog(
                this,
                msg,
                titlu,
                tip
        );
    }//GEN-LAST:event_jButton7ActionPerformed

    private void fereastraAdaugaAbonat(){
        dAdauga.setLocationRelativeTo(null);
        try {
            dAdauga.setIconImage(ImageIO.read(getClass().getResource("/cartedetelefon/phone-book.jpg")));
        } catch (IOException ex) {
        }
        finally{
            dAdauga.setVisible(true);
        }
    }
    private void stergeAbonatiiSelectati(){
        int[] selectedrows = tAbonati.getSelectedRows();
        
        if (selectedrows.length == 0) {
            mesaj("Nici un abonat nu a fost selectat!","Eroare",JOptionPane.ERROR_MESSAGE);
        }else{
            while (selectedrows.length > 0) {
                int confirm = JOptionPane.showConfirmDialog(null, "Doriti sa stergeti abonatul:" + CarteDeTelefon.abonati.get(selectedrows[0]) + "?", "Confirmati", JOptionPane.YES_NO_OPTION);
                if (confirm == 0){
                    if(selectedrows.length == 1){
                        tAbonati.clearSelection();
                    }else{
                        for (int i = 1; i < selectedrows.length; i++) {
                            tAbonati.changeSelection(selectedrows[i]-1, 4, false, false);
                        }
                    }
                    CarteDeTelefon.abonati.remove(CarteDeTelefon.abonati.get(selectedrows[0]));
                    selectedrows = tAbonati.getSelectedRows();
                    model.refreshCarte();
                }else{
                    if(selectedrows.length == 1){
                        tAbonati.clearSelection();
                    }else{
                        for (int i = 1; i < selectedrows.length; i++) {
                            tAbonati.changeSelection(selectedrows[i], 4, false, false);
                        }
                    }
                    selectedrows = tAbonati.getSelectedRows();
                }
            }
            
        }
        enableOrDisableButtons();
    }
    private void editeazaAbonatiiSelectati(){
        int[] selectedrows = tAbonati.getSelectedRows();
        
        if (selectedrows.length == 0) {
            mesaj("Nici un abonat nu a fost selectat!","Eroare",JOptionPane.ERROR_MESSAGE);
        }else{
            for (int i = 0; i < selectedrows.length; i++) {
                dModificaSetValues(CarteDeTelefon.abonati.get(selectedrows[i]));
                editAbonat = CarteDeTelefon.abonati.get(selectedrows[i]);
                dModifica.setVisible(true);
            }
            
//            while (selectedrows.length > 0) {
//                confirmEdit = 1;
//                dModificaSetValues(CarteDeTelefon.abonati.get(selectedrows[0]));
//                dModifica.setVisible(true);
//                if (confirmEdit == 0){
//                    if(selectedrows.length == 1){
//                        tAbonati.clearSelection();
//                    }else{
//                        for (int i = 1; i < selectedrows.length; i++) {
//                            tAbonati.changeSelection(selectedrows[i]-1, 4, false, false);
//                        }
//                    }
//                    try{
//                        String type = null;
//                        if(editMobil.isSelected()){
//                            type = "mobil";
//                        }else if(editFix.isSelected()){
//                            type = "fix";
//                        }
//                        CarteDeTelefon.abonati.get(selectedrows[0]).Edit( editNume.getText(),  editPrenume.getText(),  editCnp.getText(),  editTelefon.getText(),  type);
//                    } catch (EmptyInput e){
//                        mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
//                        confirmEdit = 1;
//                        dModifica.setVisible(true);
//                    } catch (TelException e){
//                        mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
//                        confirmEdit = 1;
//                        dModifica.setVisible(true);
//                    } catch (CnpException e) {
//                        mesaj("CNP invalid!","Eroare",JOptionPane.ERROR_MESSAGE);
//                        confirmEdit = 1;
//                        dModifica.setVisible(true);
//                    } catch (IllegalArgumentException e) {
//                        mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
//                        confirmEdit = 1;
//                        dModifica.setVisible(true);
//                    }
//                    
//                    selectedrows = tAbonati.getSelectedRows();
//                    model.refreshCarte();
//                }else{
//                    if(selectedrows.length == 1){
//                        tAbonati.clearSelection();
//                    }else{
//                        for (int i = 1; i < selectedrows.length; i++) {
//                            tAbonati.changeSelection(selectedrows[i], 4, false, false);
//                        }
//                    }
//                    selectedrows = tAbonati.getSelectedRows();
//                }
//            }
            
        }
        editAbonat = null;
        tAbonati.clearSelection();
        model.refreshCarte();
    }
    
    private void dModificaSetValues(Abonat a){
        editNume.setText(a.getNume());
        editPrenume.setText(a.getPrenume());
        editCnp.setText(a.getCnp());
        editTelefon.setText(a.getTel().getNumar());
        String type = a.getTel().getType();
        if (type.equals("mobil")){
            editMobil.setSelected(true);
            editTelefon.setToolTipText(toolTipMobil);
            jLabel9.setText(labelMobil);
        }else if(type.equals("fix")){
            editFix.setSelected(true);
            editTelefon.setToolTipText(toolTipFix);
            jLabel9.setText(labelFix);
        }
    }
    private void dModificaSetValues(){
        editNume.setText("");
        editPrenume.setText("");
        editCnp.setText("");
        editTelefon.setText("");
        editMobil.setSelected(false);
        editFix.setSelected(false);
        editTelefon.setToolTipText("");
        jLabel9.setText("Telefon");
    }
    
    private void newFilter() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tAbonati.getModel());
        tAbonati.setRowSorter(sorter);
        RowFilter<Object, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
//            rf = RowFilter.regexFilter(filterText.getText(), 0);
            String labels[] = model.getColumnsName();
            List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(labels.length);
            for (int i = 0; i < labels.length; i++) {
                filters.add(RowFilter.regexFilter(filterText.getText(), i));
            }
            rf = RowFilter.orFilter(filters);
        } catch (java.util.regex.PatternSyntaxException e) {
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
            return;
        }

        sorter.setRowFilter(rf);
        if (filterText.equals("")){
            jLabel15.setVisible(false);
            jButton12.setVisible(false);
        }
    }
    
    private void newFilter(int col) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tAbonati.getModel());
        tAbonati.setRowSorter(sorter);
        RowFilter<Object, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), col);
        } catch (java.util.regex.PatternSyntaxException e) {
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
            return;
        }

        sorter.setRowFilter(rf);
        if (filterText.equals("")){
            jLabel15.setVisible(false);
            jButton12.setVisible(false);
        }
    }
    
    private void mobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mobilActionPerformed
        addTelefon.setToolTipText(toolTipMobil);
        jLabel4.setText(labelMobil);
    }//GEN-LAST:event_mobilActionPerformed

    private void fixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixActionPerformed
        addTelefon.setToolTipText(toolTipFix);
        jLabel4.setText(labelFix);
    }//GEN-LAST:event_fixActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        stergeAbonatiiSelectati();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        stergeAbonatiiSelectati();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void editNumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editNumeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editNumeActionPerformed

    private void editPrenumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPrenumeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editPrenumeActionPerformed

    private void editMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMobilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editMobilActionPerformed

    private void editFixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editFixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editFixActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
//        confirmEdit = JOptionPane.showConfirmDialog(null, "Doriti sa salvati modificarile abonatului?", "Confirmati", JOptionPane.YES_NO_OPTION);
//        dModifica.setVisible(false);

        try{
            String type = null;
            if(editMobil.isSelected()){
                type = "mobil";
            }else if(editFix.isSelected()){
                type = "fix";
            }
            editAbonat.Edit( editNume.getText(),  editPrenume.getText(),  editCnp.getText(),  editTelefon.getText(),  type);
            dModifica.setVisible(false);
        } catch (EmptyInput e){
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (TelException e){
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (CnpException e) {
            mesaj("CNP invalid!","Eroare",JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            mesaj(e.getMessage(),"Eroare",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        editeazaAbonatiiSelectati();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        editeazaAbonatiiSelectati();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        orderBy();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        orderBy();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tAbonati.getModel());
        tAbonati.setRowSorter(sorter);
        
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(jComboBox1.getSelectedIndex(), SortOrder.values()[jComboBox2.getSelectedIndex()]));
        sortKeys.add(new RowSorter.SortKey(jComboBox3.getSelectedIndex(), SortOrder.values()[jComboBox4.getSelectedIndex()]));
        sortKeys.add(new RowSorter.SortKey(jComboBox5.getSelectedIndex(), SortOrder.values()[jComboBox6.getSelectedIndex()]));
        sortKeys.add(new RowSorter.SortKey(jComboBox7.getSelectedIndex(),  SortOrder.values()[jComboBox8.getSelectedIndex()]));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
        dOrder.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dFilter.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        dFilter.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        newFilter(jComboBox9.getSelectedIndex());
        dFilter.setVisible(false);
        jLabel15.setVisible(true);
        jButton12.setVisible(true);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        newFilter();
        dFilter.setVisible(false);
        jLabel15.setVisible(true);
        jButton12.setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        filterText.setText("");
        newFilter();
        jLabel15.setVisible(false);
        jButton12.setVisible(false);
    }//GEN-LAST:event_jButton12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Aplicatie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Aplicatie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Aplicatie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Aplicatie.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Aplicatie().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem about;
    private javax.swing.JTextField addCnp;
    private javax.swing.JTextField addNume;
    private javax.swing.JTextField addPrenume;
    private javax.swing.JTextField addTelefon;
    private javax.swing.JDialog dAbout;
    private javax.swing.JDialog dAdauga;
    private javax.swing.JDialog dFilter;
    private javax.swing.JDialog dModifica;
    private javax.swing.JDialog dOrder;
    private javax.swing.JTextField editCnp;
    private javax.swing.JRadioButton editFix;
    private javax.swing.JRadioButton editMobil;
    private javax.swing.JTextField editNume;
    private javax.swing.JTextField editPrenume;
    private javax.swing.ButtonGroup editTelType;
    private javax.swing.JTextField editTelefon;
    private javax.swing.JTextField filterText;
    private javax.swing.JRadioButton fix;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JComboBox<String> jComboBox8;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenu mAbonati;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mHelp;
    private javax.swing.JPanel mainPane;
    private javax.swing.JRadioButton mobil;
    private javax.swing.JPanel rZone;
    private javax.swing.JLabel splash;
    private javax.swing.JTable tAbonati;
    private javax.swing.ButtonGroup telType;
    private javax.swing.JTextArea textDespre;
    // End of variables declaration//GEN-END:variables
}