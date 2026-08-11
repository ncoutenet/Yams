/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yams.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Table de correspondance entre l'index d'un coup et son libellé, partagée entre
 * YamControl (résumé des coups restants) et FinTourVue (liste déroulante de choix)
 */
public enum ItemName {
    NUMERO_1(0, "1", "1"),
    NUMERO_2(1, "2", "2"),
    NUMERO_3(2, "3", "3"),
    NUMERO_4(3, "4", "4"),
    NUMERO_5(4, "5", "5"),
    NUMERO_6(5, "6", "6"),
    BRELAN_OU_PLUS(6, Libelles.BRELAN, "+"),
    PETITE_SUITE_OU_MOINS(7, Libelles.PETITE_SUITE, "-"),
    SUITE_OU_GRANDE_SUITE(8, Libelles.GRANDE_SUITE, Libelles.SUITE),
    FULL(9, "full", "full"),
    CARRE_COUP(10, Libelles.CARRE, Libelles.CARRE),
    YAM_COUP(11, Libelles.YAM, Libelles.YAM),
    CHANCE_OU_VIDE(12, Libelles.CHANCE, "");

    public static final String SUITE = Libelles.SUITE;
    public static final String PETITE_SUITE = Libelles.PETITE_SUITE;
    public static final String CHANCE = Libelles.CHANCE;
    public static final String CARRE = Libelles.CARRE;
    public static final String YAM = Libelles.YAM;
    public static final String GRANDE_SUITE = Libelles.GRANDE_SUITE;
    public static final String BRELAN = Libelles.BRELAN;

    // classe séparée : une énum ne peut pas référencer ses propres constantes depuis sa liste
    // de valeurs (elle doit être déclarée en premier), donc les littéraux vivent ici, une seule fois
    private static final class Libelles {
        static final String SUITE = "suite";
        static final String PETITE_SUITE = "petite suite";
        static final String CHANCE = "chance";
        static final String CARRE = "carré";
        static final String YAM = "yam's";
        static final String GRANDE_SUITE = "grande suite";
        static final String BRELAN = "brelan";

        private Libelles(){
        }
    }

    private final int index;
    private final String libelleLibre;
    private final String libelleRegles;

    ItemName(int index, String libelleLibre, String libelleRegles){
        this.index = index;
        this.libelleLibre = libelleLibre;
        this.libelleRegles = libelleRegles;
    }

    public String getLibelle(boolean rules){
        return rules ? libelleRegles : libelleLibre;
    }

    public static String nomCoup(int i, boolean rules){
        ItemName item = Arrays.stream(ItemName.values()).filter(v -> v.index == i).findFirst().orElse(null);
        return Objects.nonNull(item) ? item.getLibelle(rules) : "";
    }
}
