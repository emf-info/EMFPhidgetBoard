
import EMFPhidgetsBoard.Board;

/**
 * Application "EMFPhidgetsBoardExBase01".
 *
 * @author <a href="mailto:paul.friedli@edufr.ch">Paul Friedli</a>
 * @since 04 novembre 2014
 * @version 0.1
 */
public class EMFPhidgetsBoardExBase01 {

    public static void main( String[] args ) {

        // Création de l'objet Board permettant de communiquer simplement avec l'EMFPhidgetBoard
        Board board = new Board();

        // Tenter de se connecter au board..
        if ( board.seConnecter() ) {
            // On est bien connectés
            System.out.println( "Nous sommes CONNECTÉS au EMFPhidgetBoard !" );

            // Continuer tant qu'on ne presse pas le bouton N°7
            while (!board.bouton7.estPresse()) {

                // Faut-il allumer ou éteindre les leds ?
                boolean allumer = board.bouton4.estPresse();

                // Allumer ou éteindre les leds
                board.led0.setEtat( allumer );
                board.led1.setEtat( allumer );
                board.led2.setEtat( allumer );
                board.led3.setEtat( allumer );
                board.led4.setEtat( allumer );
                board.led5.setEtat( allumer );
                board.led6.setEtat( allumer );
                board.led7.setEtat( allumer );
                
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
