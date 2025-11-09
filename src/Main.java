import database.DB;
import gui.FenetrePrincipale;

public class Main {
    public static void main(String[] args) {
        DB.init();
        FenetrePrincipale fenetrePrincipale = new FenetrePrincipale();
        fenetrePrincipale.setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread(DB::deconnect));
    }
}