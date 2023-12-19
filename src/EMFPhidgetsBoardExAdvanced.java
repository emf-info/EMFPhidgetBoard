import EMFPhidgetsBoard.Board;

/**
 * Application "EMFPhidgetsBoardExAdvanced".
 *
 * Application de base pour que l'élève puisse s'éclater un peu :-)
 *
 * @author <a href="mailto:paul.friedli@edufr.ch">Paul Friedli</a>
 * @since 13 août 2013
 * @version 0.1
 */
public class EMFPhidgetsBoardExAdvanced {

    public static void main(String[] args) {

        // Création de l'objet EMFPhidgetsBoard pour converser avec l'EMFPhidgetBoard
        final Board board = new EMFPhidgetsBoard.Board();

        // Installer les listeners pour afficher les changements d'état à la volée
        board.setAnalogicSensorChangesListener((index, valeur) -> {
            System.out.println("Le phidget N°" + index + " à une valeur de " + valeur);
        });

        board.setDigitalInputChangesListener((index, etat) -> {
            System.out.println("L'entrée N°" + index + " est à " + etat);
            board.leds[index].setEtat(etat);
        });

        board.setDigitalOutputChangesListener((index, etat) -> {
            System.out.println("La sortie N°" + index + " est à " + etat);
        });

        // Se connecter à l'EMFPhidgetBoard
        if (board.seConnecter()) {
            // Afficher la température et l'humidité
            System.out.println("-----------------------------------------------------");
            System.out.println(
                    "La température actuelle est de : " + calculerTemperature(board.capteur6.getValeur()) + "°C");
            System.out.println("L'humidité actuelle est de : " + calculerHumidite(board.capteur4.getValeur()) + "%");
            System.out.println("-----------------------------------------------------");

            // Tant qu'on ne presse pas le bouton N°7, on continue... de dormir...
            do {
                board.dormir(50);
            } while (!board.bouton7.estPresse());

            // Se déconnecter du contrôleur
            board.seDeconnecter();
        }
    }

    public static double calculerTemperature(int phidgetValue) {
        // Cette formule de conversion est fournie avec le capteur de température
        return (int) (((phidgetValue * 0.22222) - 61.11) * 100.0) / 100.0;
    }

    public static double calculerHumidite(int phidgetValue) {
        // Cette formule de conversion est fournie avec le capteur d'humidité
        return (int) (((phidgetValue * 0.1906) - 40.2) * 100.0) / 100.0;
    }
}
