package yams.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import yams.control.YamControl;

// TODO prendre en comptes les préférences

public class PreferencesVue extends JFrame {
    private YamControl _myControler;
    private JCheckBox _sounds;
    private ButtonGroup _select;
    private ButtonGroup _combinations;
    private List<Boolean> _prefs;



    public PreferencesVue(YamControl yc, List<Boolean> prefs){
        super("Préférences");

        //initialisation des variables
        this._myControler = yc;
        this._prefs = prefs;
        if(this._prefs.isEmpty()){
            //this._prefs = new ArrayList<Boolean>();
            for(int i=0; i<3; i++){
                this._prefs.add(true);
            }
        }
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        JPanel panPrefs = new JPanel(new BorderLayout());

        //construction des éléments
        this._sounds = new JCheckBox("Sons");
        this._sounds.setSelected(this._prefs.get(YamControl.PREFSOUND));
        this._select = new ButtonGroup();
        this._combinations = new ButtonGroup();
        panPrefs.add(this._sounds, BorderLayout.NORTH);

        JPanel panSelect = new JPanel(new GridLayout(2, 1));
        panSelect.setBorder(BorderFactory.createTitledBorder("Sélection des dés"));
        JRadioButton rbGarde = new JRadioButton();
        rbGarde.setText("Garder les dés sélectionnés");
        rbGarde.setSelected(this._prefs.get(YamControl.PREFSELECT));
        JRadioButton rbRelance = new JRadioButton();
        rbRelance.setText("Relancer les dés sélectionnés");
        rbRelance.setSelected(!this._prefs.get(YamControl.PREFSELECT));
        this._select.add(rbGarde);
        this._select.add(rbRelance);
        panSelect.add(rbGarde);
        panSelect.add(rbRelance);
        panPrefs.add(panSelect, BorderLayout.CENTER);

        JPanel panCombinations = new JPanel(new GridLayout(2, 1));
        panCombinations.setBorder(BorderFactory.createTitledBorder("Combinaisons"));
        JRadioButton rbComb1 = new JRadioButton();
        rbComb1.setText("soustraction des scores + et -");
        rbComb1.setSelected(this._prefs.get(YamControl.PREFRULES));
        JRadioButton rbComb2 = new JRadioButton();
        rbComb2.setText("Brelan, petite suite, grande suite, chance");
        rbComb2.setSelected(!this._prefs.get(YamControl.PREFRULES));
        this._combinations.add(rbComb1);
        this._combinations.add(rbComb2);
        panCombinations.add(rbComb1);
        panCombinations.add(rbComb2);
        panPrefs.add(panCombinations, BorderLayout.SOUTH);

        JPanel panValidate = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnValidate = new JButton("Ok");
        panValidate.add(btnValidate);

        panel.add(panPrefs, BorderLayout.CENTER);
        panel.add(panValidate, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setSize(400, 225);
        this.setResizable(false);
        this.setLocationRelativeTo(this.getParent()); //centrage de la fenêtre
        this.setVisible(true);
    }

    public boolean isSounds() {
        return _sounds.isSelected();
    }

    public void setSounds(boolean sounds) {
        this._sounds.setSelected(sounds);
    }

    public boolean isSelect() {
        throw new UnsupportedOperationException("Not yet supported");
    }

    public void setSelect(boolean select) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    public boolean isCombinations() {
        throw new UnsupportedOperationException("Not yet supported");
    }

    public void setCombinations(boolean combinations) {
        throw new UnsupportedOperationException("Not yet supported");
    }
	
}
