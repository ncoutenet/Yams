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
    private YamControl _myControler;
    private JCheckBox _sounds;
    private List<Boolean> _prefs;
    
    private ButtonGroup _select;
    private JRadioButton _rbGarde;
    private JRadioButton _rbRelance;
    private ButtonGroup _combinations;
    private JRadioButton _rbComb1;
    private JRadioButton _rbComb2;


    public PreferencesVue(YamControl yc, List<Boolean> prefs){
        super("Préférences");

        //initialisation des variables
        this._myControler = yc;
        this._prefs = prefs;
        if(this._prefs.isEmpty()){
            for(int i=0; i<3; i++){
                this._prefs.add(true);
            }
        }
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        JPanel panPrefs = new JPanel(new BorderLayout());

        //construction des éléments
        this._sounds = new JCheckBox("Sons");
        this._sounds.setSelected(this._prefs.get(Yams.PREFSOUND));
        this._select = new ButtonGroup();
        this._combinations = new ButtonGroup();
        panPrefs.add(this._sounds, BorderLayout.NORTH);

        JPanel panSelect = new JPanel(new GridLayout(2, 1));
        panSelect.setBorder(BorderFactory.createTitledBorder("Sélection des dés"));
        _rbGarde = new JRadioButton();
        _rbGarde.setText("Garder les dés sélectionnés");
        _rbGarde.setSelected(this._prefs.get(Yams.PREFSELECT));
        
        _rbRelance = new JRadioButton();
        _rbRelance.setText("Relancer les dés sélectionnés");
        _rbRelance.setSelected(!this._prefs.get(Yams.PREFSELECT));
        this._select.add(_rbGarde);
        this._select.add(_rbRelance);
        panSelect.add(_rbGarde);
        panSelect.add(_rbRelance);
        panPrefs.add(panSelect, BorderLayout.CENTER);

        JPanel panCombinations = new JPanel(new GridLayout(2, 1));
        panCombinations.setBorder(BorderFactory.createTitledBorder("Combinaisons"));
        _rbComb1 = new JRadioButton();
        _rbComb1.setText("soustraction des scores + et -");
        _rbComb1.setSelected(this._prefs.get(Yams.PREFRULES));
        _rbComb2 = new JRadioButton();
        _rbComb2.setText("Brelan, petite suite, grande suite, chance");
        _rbComb2.setSelected(!this._prefs.get(Yams.PREFRULES));
        this._combinations.add(_rbComb1);
        this._combinations.add(_rbComb2);
        panCombinations.add(_rbComb1);
        panCombinations.add(_rbComb2);
        panPrefs.add(panCombinations, BorderLayout.SOUTH);

        JPanel panValidate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnValidate = new JButton("Ok");
        btnValidate.addActionListener(new YamEvents(this._myControler));
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
        return this._prefs;
    }
    
    /*
     * Sauvegarde les préférences dans le tableau que récupère le controleur
     */
    public void setPrefs(){
        this._prefs.set(Yams.PREFSOUND, this._sounds.isSelected());
        this._prefs.set(Yams.PREFSELECT, this._rbGarde.isSelected());
        this._prefs.set(Yams.PREFRULES, this._rbComb1.isSelected());
    }
    
    public void enableGroup(int group, boolean enable){
        switch(group){
            case 0:
                this._sounds.setEnabled(enable);
                break;
            case 1:
                this._rbGarde.setEnabled(enable);
                this._rbRelance.setEnabled(enable);
                break;
            case 2:
                this._rbComb1.setEnabled(enable);
                this._rbComb2.setEnabled(enable);
                break;
            default:
                break; //n'arrivera pas
        }
    }
}
