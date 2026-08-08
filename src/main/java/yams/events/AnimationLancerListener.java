/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import yams.control.YamControl;
import yams.views.JeuVue;

/**
 *
 * @author nicolas
 */

/*
 * Gère l'animation de défilement des dés lors d'un lancer : affiche des faces
 * aléatoires pendant quelques ticks avant de fixer le résultat final.
 */
public class AnimationLancerListener implements ActionListener{
    public static final int NB_TICKS = 10;
    public static final int INTERVAL_MS = 70;

    private JeuVue myView;
    private YamControl myControler;
    private boolean[] aAnimer;
    private int[] valeursFinales;
    private int lancesRestants;
    private int ticksRestants;

    public AnimationLancerListener(JeuVue vue, YamControl yc, boolean[] aAnimer, int[] valeursFinales, int lancesRestants){
        this.myView = vue;
        this.myControler = yc;
        this.aAnimer = aAnimer;
        this.valeursFinales = valeursFinales;
        this.lancesRestants = lancesRestants;
        this.ticksRestants = NB_TICKS;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        this.ticksRestants--;
        if(this.ticksRestants <= 0){
            ((Timer) ae.getSource()).stop();
            for(int i = 0; i < 5; i++){
                if(this.aAnimer[i]){
                    this.myView.setValDe(i, this.valeursFinales[i], false);
                }
            }
            this.myControler.finLancer(this.lancesRestants);
        }
        else{
            this.myView.afficherFacesAleatoires(this.aAnimer);
        }
    }
}
