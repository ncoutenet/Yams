package yams.views;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import yams.control.YamControl;

public class PreferencesVue extends JFrame {
    private YamControl _myControler;
    private JCheckBox _sounds;
    private ButtonGroup _select;
    private ButtonGroup _combinations;



    public PreferencesVue(YamControl yc){
        super("Préférences");

        //initialisation des variables
        this._myControler = yc;
        Container panel = this.getContentPane();
        panel.setLayout(new BorderLayout());
        JPanel panPrefs = new JPanel(new GridLayout(3, 1));

        //construction des éléments
        this._sounds = new JCheckBox("Sons");
        this._sounds.setSelected(true);
        this._select = new ButtonGroup();
        this._combinations = new ButtonGroup();
        panPrefs.add(this._sounds);

        JPanel panSelect = new JPanel(new GridLayout(2, 1));
        panSelect.setBorder(BorderFactory.createTitledBorder("Sélection des dés"));
        JRadioButton rbGarde = new JRadioButton();
        rbGarde.setText("Garder les dés sélectionnés");
        rbGarde.setSelected(true);
        JRadioButton rbRelance = new JRadioButton();
        rbRelance.setText("Relancer les dés sélectionnés");
        this._select.add(rbGarde);
        this._select.add(rbRelance);
        panSelect.add(rbGarde);
        panSelect.add(rbRelance);
        panPrefs.add(panSelect);

        JPanel panCombinations = new JPanel(new GridLayout(2, 1));
        panCombinations.setBorder(BorderFactory.createTitledBorder("Combinaisons"));
        JRadioButton rbComb1 = new JRadioButton();
        rbComb1.setText("soustraction des scores + et -");
        rbComb1.setSelected(true);
        JRadioButton rbComb2 = new JRadioButton();
        rbComb2.setText("Brelan, petite suite, grande suite, chance");
        this._combinations.add(rbComb1);
        this._combinations.add(rbComb2);
        panCombinations.add(rbComb1);
        panCombinations.add(rbComb2);
        panPrefs.add(panCombinations);

        JButton btnValidate = new JButton("Ok");

        panel.add(panPrefs, BorderLayout.CENTER);
        panel.add(btnValidate, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
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
