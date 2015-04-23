import SearchPatterns.FSM;
import SearchPatterns.KMP;
import SearchPatterns.RabinKarp;
import Utilities.Toolbox;

import javax.tools.Tool;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, NumberFormatException {
        // Ne pas modifier cette partie
        String fileName = null;
        String motif = null;
        int algo = 0;

        switch (args.length) {
            case 3:
                fileName = args[2];
            case 2:
                algo = Integer.parseInt(args[1]);
                motif = args[0];
                break;
            default:
                System.err.println("usage: java Main <motif> <algo> (<fichier_texte>)");
                System.exit(1);
        }

        // Rien d'autre ne doit etre affiche que ce qui est indique ci-dessous
        switch (algo) {
            case 1: //Rabin-Karp
                RabinKarp rk = new RabinKarp(motif);
                // Format de sortie -> à générer avec votre code
                if (fileName == null) {
                    // Afficher la base, le nombre 1er pour le modulo, le hash du motif
                    rk.printState();
                } else {
                    // Afficher le nombre d'occurences du motif
                    // suivi de la liste des positions de sa 1ere lettre dans le texte
                    Toolbox.printOutput(rk.search(motif, fileName));
                }
                break;
            case 2: //Automate fini
                FSM fsm = new FSM(motif);
                // Format de sortie -> à générer avec votre code
                if (fileName == null) {
                    // Affiche le tableau.
                    fsm.printState();
                } else {
                    // Afficher le nombre d'occurences du motif
                    Toolbox.printOutput(fsm.search(motif, fileName));
                }
                break;
            case 3: //Knut-Morris-Pratt
                KMP kmp = new KMP(motif);
                // Format de sortie -> à générer avec votre code
                if (fileName == null) {
                    //Afficher le tableau des prefixes
                    kmp.printOverlap();
                } else {
                    // Afficher le nombre d'occurences du motif
                    // suivi de la liste des positions de sa 1ere lettre dans le texte
                    Toolbox.printOutput(kmp.search(motif, fileName));
                }
                break;
            case 4: //Boyer-Moore
                // Format de sortie -> à générer avec votre code
                if (fileName == null) {
                    //Afficher les deux tableaux des decalages
                    // P. ex. pour le motif M = "anpanman"
                    // 1er tableau
                    //                  a n p m *       lettre (selon ordre dans le motif)
                    System.out.println("1 0 5 2 8"); // decalage
                    // 2eme tableau
                    // partie du motif bonne (depuis la droite):
                    //            n an man nman anman panman npanman anpanman
                    // decalage:  8  3  6    6    6      6      6       6
                    System.out.println("8 3 6 6 6 6 6 6"); // decalage
                } else {
                    // Afficher le nombre d'occurences du motif
                    // suivi de la liste des positions de sa 1ere lettre dans le texte
                    System.out.println("13"); // nombre d'occurences du motifs
                    System.out.println("0 3 46 67 109"); //liste des positions du motif
                }
                break;
            default:
                System.err.println("Algorithm not implemented");
                System.exit(2);
        }
    }
}











