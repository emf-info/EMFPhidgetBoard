import EMFPhidgetsBoard.Board;

/**
 * Application "EMFPhidgetsBoardExBase04".
 *
 * @author <a href="mailto:paul.friedli@edufr.ch">Paul Friedli</a>
 * @since 04 novembre 2014
 * @version 0.1
 */
public class EMFPhidgetsBoardExBase04 {

    public final static int MAX_NUMBER = 100;   // Le plus grand nombre du jeu
    public final static int MIN_NUMBER = 1;     // Le plus petit nombre du jeu

    public static void main( String[] args ) {

        // Création de l'objet Board permettant de communiquer simplement avec l'EMFPhidgetBoard
        Board board = new Board();

        // Tenter de se connecter au board..
        if ( board.seConnecter() ) {

            // Indiquer au joueur qu'il doit penser à un nombre
            System.out.println( "Veuillez penser à un nombre de " + MIN_NUMBER + " à " + MAX_NUMBER + " !" );

            // Proposition et limites au départ
            int currentMin = MIN_NUMBER - 1;
            int currentMax = MAX_NUMBER + 1;
            int proposition = ( currentMax - currentMin ) / 2 + currentMin;

            // Continuer tant qu'on ne presse pas le bouton N°5 (nombre trouvé)
            while ( !board.bouton5.estPresse() ) {

                // Afficher la proposition
                System.out.println( "Je pense que c'est le nombre " + proposition );

                // Attendre que l'un des boutons 4, 5, 6 ou 7 soit pressé
                while ( !( board.bouton4.estPresse() || board.bouton5.estPresse() || board.bouton6.estPresse() ) ) {
                    // Dormir un peu pour pas aller trop vite (ici 20x par seconde on va vérifier)
                    board.dormir( 50 );
                }

                // Si le bouton 4 est pressé, c'est que c'est trop petit !
                if ( board.bouton4.estPresse() ) {
                    // La valeur courante devient le minimum
                    currentMin = proposition;
                    // On avance de la moitié de la distance avec la valeur MAX_NUMBER
                    proposition = proposition + ( currentMax - proposition ) / 2;
                } else if ( board.bouton6.estPresse() ) {
                    // La valeur courante devient le maximum
                    currentMax = proposition;
                    // On recule de la moitié de la distance avec la valeur MIN_NUMBER
                    proposition = proposition - ( proposition - currentMin ) / 2;
                }

                // Attendre que les boutons 4, 5, 6 ou 7 soient tous relâchés
                while ( board.bouton4.estPresse() || board.bouton6.estPresse() ) {
                    // Dormir un peu pour pas aller trop vite (ici 20x par seconde on va vérifier)
                    board.dormir( 50 );
                }
            }

            // Indiquer la réussite
            System.out.println( "Merci d'avoir joué avec moi, c'était trop facile de trouver votre nombre :-)" );

            // Se déconnecter proprement du board
            board.seDeconnecter();
        }
    }
}
