package yams.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.*;
import yams.Yams;
import yams.control.YamControl;
import yams.events.YamEvents;


public class PreferencesVue extends JFrame {
    private transient YamControl myControler;
    private JCheckBox sounds;
    private List<Boolean> prefs;
    
    private ButtonGroup select;
    private JRadioButton rbGarde;
    private JRadioButton rbRelance;
    private ButtonGroup combinations;
    private JRadioButton rbComb1;
    private JRadioButton rbComb2;


    public PreferencesVue(YamControl yc, List<Boolean> prefs){
        super("Préférences");

        //initialisation des variables
        this.myControler = yc;
        this.prefs = prefs;
        if(this.prefs.isEmpty()){
            for(int i=0; i<3; i++){
                this.prefs.add(true);
            }
        }
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        JPanel panPrefs = new JPanel(new BorderLayout());

        //construction des éléments
        this.sounds = new JCheckBox("Sons");
        this.sounds.setSelected(this.prefs.get(Yams.PREFSOUND));
        this.select = new ButtonGroup();
        this.combinations = new ButtonGroup();
        panPrefs.add(this.sounds, BorderLayout.NORTH);

        JPanel panSelect = new JPanel(new GridLayout(2, 1));
        panSelect.setBorder(BorderFactory.createTitledBorder("Sélection des dés"));
        rbGarde = new JRadioButton();
        rbGarde.setText("Garder les dés sélectionnés");
        rbGarde.setSelected(this.prefs.get(Yams.PREFSELECT));
        
        rbRelance = new JRadioButton();
        rbRelance.setText("Relancer les dés sélectionnés");
        rbRelance.setSelected(!this.prefs.get(Yams.PREFSELECT));
        this.select.add(rbGarde);
        this.select.add(rbRelance);
        panSelect.add(rbGarde);
        panSelect.add(rbRelance);
        panPrefs.add(panSelect, BorderLayout.CENTER);

        JPanel panCombinations = new JPanel(new GridLayout(2, 1));
        panCombinations.setBorder(BorderFactory.createTitledBorder("Combinaisons"));
        rbComb1 = new JRadioButton();
        rbComb1.setText("soustraction des scores + et -");
        rbComb1.setSelected(this.prefs.get(Yams.PREFRULES));
        rbComb2 = new JRadioButton();
        rbComb2.setText("Brelan, petite suite, grande suite, chance");
        rbComb2.setSelected(!this.prefs.get(Yams.PREFRULES));
        this.combinations.add(rbComb1);
        this.combinations.add(rbComb2);
        panCombinations.add(rbComb1);
        panCombinations.add(rbComb2);
        panPrefs.add(panCombinations, BorderLayout.SOUTH);

        JPanel panValidate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnValidate = new JButton("Ok");
        btnValidate.addActionListener(new YamEvents(this.myControler));
        btnValidate.setActionCommand("changePrefs");
        panValidate.add(btnValidate);

        panel.add(panPrefs, BorderLayout.CENTER);
        panel.add(panValidate, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setSize(400, 225);
        this.setResizable(false);
        this.setLocationRelativeTo(this.getParent()); //centrage de la fenêtre
        this.setVisible(true);
    }

    public List<Boolean> getPrefs(){
        return this.prefs;
    }
    
    /*
     * Sauvegarde les préférences dans le tableau que récupère le controleur
     */
    public void setPrefs(){
        this.prefs.set(Yams.PREFSOUND, this.sounds.isSelected());
        this.prefs.set(Yams.PREFSELECT, this.rbGarde.isSelected());
        this.prefs.set(Yams.PREFRULES, this.rbComb1.isSelected());
    }
    
    public void enableGroup(int group, boolean enable){
        switch(group){
            case 0:
                this.sounds.setEnabled(enable);
                break;
            case 1:
                this.rbGarde.setEnabled(enable);
                this.rbRelance.setEnabled(enable);
                break;
            case 2:
                this.rbComb1.setEnabled(enable);
                this.rbComb2.setEnabled(enable);
                break;
            default:
                break; //n'arrivera pas
        }
    }
}
