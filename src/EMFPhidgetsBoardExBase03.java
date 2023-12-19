
import EMFPhidgetsBoard.Board;

/**
 * Application "EMFPhidgetsBoardExBase03".
 *
 * @author <a href="mailto:paul.friedli@edufr.ch">Paul Friedli</a>
 * @since 04 novembre 2014
 * @version 0.1
 */
public class EMFPhidgetsBoardExBase03 {

    public final static int VITESSE_INITIALE = 100;     // On dort 100ms donc 10x par seconde
    public final static int VITESSE_MIN = 500;          // On dort 500ms donc 2x par seconde
    public final static int VITESSE_MAX = 10;           // On ne dort que 10ms donc 100x par seconde
    public final static int VITESSE_INCREMENT = 10;     // Accélération ou décélération
    
    public static void main( String[] args ) {

        // Création de l'objet Board permettant de communiquer simplement avec l'EMFPhidgetBoard
        Board board = new Board();

        // Tenter de se connecter au board..
        if ( board.seConnecter() ) {
            
            // L'indice de la lumière allumée de notre "chenillard"..
            int positionAllumee = 0;

            // Histoire de les voir bouger, on ne fait que 10 animations par seconde (au début en tout cas)...
            int msDormir = VITESSE_INITIALE;
            
            // Ce programme ne s'arrêtera jamais, sauf si on presse les boutons 4 et 7 en même temps            
            while ( !( board.bouton4.estPresse() && board.bouton7.estPresse() ) ) {

                // Allumer chaque led selon la position courante du chenillard
                for ( int i = 0; i < board.leds.length; i++ ) {
                    board.leds[i].setEtat( positionAllumee == i );
                }

                // Implémenter la rotation de lumières du chenillard... on prend simplement la suivante !
                positionAllumee = ( positionAllumee + 1 ) % board.leds.length;    // Mieux encore

                // Histoire de le voir bouger, on ne fait que 20 animations par seconde
                board.dormir( msDormir );

                // Plus vite ?
                if ( board.bouton6.estPresse() ) {
                    msDormir = Math.max( VITESSE_MAX, msDormir - VITESSE_INCREMENT );
                    // Attendre que le bouton soit relaché.. (=> 1 pression à la fois ;-)
                    while ( board.bouton6.estPresse() ) {
                        board.dormir( msDormir );
                    }
                    System.out.println( "Vitesse : " + ( 1000 / msDormir ) + " fois par seconde" );
                }
                // Moins vite ?
                if ( board.bouton5.estPresse() ) {
                    msDormir = Math.min( VITESSE_MIN, msDormir + VITESSE_INCREMENT );
                    // Attendre que le bouton soit relaché.. (=> 1 pression à la fois ;-)
                    while ( board.bouton5.estPresse() ) {
                        board.dormir( msDormir );
                    }
                    System.out.println( "Vitesse : " + ( 1000 / msDormir ) + " fois par seconde" );
                }
            }

            // Ne laisser aucune led allumée avant de quitter le programme
            for ( int i = 0; i < board.leds.length; i++ ) {
                board.leds[i].setEtat( false );
            }

            // On se déconnecte du board
            board.seDeconnecter();
        }
    }
}
