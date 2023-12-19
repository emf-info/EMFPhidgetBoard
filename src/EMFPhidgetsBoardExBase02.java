
import EMFPhidgetsBoard.Board;

/**
 * Application "EMFPhidgetsBoardExBase02".
 *
 * @author <a href="mailto:paul.friedli@edufr.ch">Paul Friedli</a>
 * @since 04 novembre 2014
 * @version 0.1
 */
public class EMFPhidgetsBoardExBase02 {

    public static void main( String[] args ) {

        // Création de l'objet Board permettant de communiquer simplement avec l'EMFPhidgetBoard
        Board board = new Board();

        // Tenter de se connecter au board..
        if ( board.seConnecter() ) {
            // On est bien connectés
            System.out.println( "Nous sommes CONNECTÉS au EMFPhidgetBoard !" );

            // Continuer tant qu'on ne presse pas le bouton N°7
            while ( !board.bouton7.estPresse() ) {

                // Allumer ou éteindre les leds
                board.led0.setEtat( board.bouton0.getEtat() );
                board.led1.setEtat( board.bouton1.getEtat() );
                board.led2.setEtat( board.bouton2.getEtat() );
                board.led3.setEtat( board.bouton3.getEtat() );
                board.led4.setEtat( board.bouton4.getEtat() );
                board.led5.setEtat( board.bouton5.getEtat() );
                board.led6.setEtat( board.bouton6.getEtat() );
                board.led7.setEtat( board.bouton7.getEtat() );

                // Composer une valeur BYTE avec chacun des bits des leds
                int valeur = 0;
                if ( board.led0.getEtat() ) {
                    valeur += 1;
                }
                if ( board.led1.getEtat() ) {
                    valeur += 2;
                }
                if ( board.led2.getEtat() ) {
                    valeur += 4;
                }
                if ( board.led3.getEtat() ) {
                    valeur += 8;
                }
                if ( board.led4.getEtat() ) {
                    valeur += 16;
                }
                if ( board.led5.getEtat() ) {
                    valeur += 32;
                }
                if ( board.led6.getEtat() ) {
                    valeur += 64;
                }
                if ( board.led7.getEtat() ) {
                    valeur += 128;
                }

                // Afficher la valeur
                System.out.println( "La valeur correspondante du byte est : " + valeur );

                // Dormir un peu pour pas aller trop vite (ici 20x par seconde on va vérifier)
                board.dormir( 50 );
            }

            // Se déconnecter proprement du board
            board.seDeconnecter();

            // Notifier l'utilisateur
            System.out.println( "Nous sommes DÉCONNECTÉS de l'EMFPhidgetBoard !" );
        } else {
            // Indiquer le problème à l'utilisateur
            System.out.println( "Erreur : pas connecté au EMFPhidgetBoard !" );
        }
    }
}
