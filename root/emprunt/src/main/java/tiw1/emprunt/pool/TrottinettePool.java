package tiw1.emprunt.pool;

import org.picocontainer.Startable;
import tiw1.annotation.annotations.NIVEAU;
import tiw1.annotation.annotations.Todo;
import tiw1.emprunt.model.Trottinette;
import tiw1.emprunt.persistence.TrottinetteLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TrottinettePool implements Startable {


    private Map<Long, Trottinette> trottinettes ;

    public TrottinettePool() {
        this.trottinettes = new HashMap<>();
    }

    public  void release(Trottinette trottinette) {
        if (trottinettes.containsKey(trottinette.getId())) {
            if (!trottinette.isDisponible()) {
                trottinette.setDisponible(true);
                trottinettes.put(trottinette.getId(),trottinette);
            }
        }
    }

     public Trottinette getTrottinette(long idTrottinette) {

             Trottinette trottinette=trottinettes.get(idTrottinette);
             if (trottinette.isDisponible()){
                 trottinette.setDisponible(false);
                 trottinettes.put(idTrottinette,trottinette);

                 (new Thread(
                         () -> {
                             try {
                                 Thread.sleep(3000);
                                 release(trottinette);
                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                 )).start();

                 return trottinette;
             }
        return null;
    }

    public List<Trottinette> getTrottinettesList() {
        return new ArrayList<Trottinette>(trottinettes.values());
    }

    public Map<Long, Trottinette> getTrottinettes() {
        return trottinettes;
    }

    public void setTrottinettes(HashMap<Long, Trottinette> trottinettes) {
        this.trottinettes = trottinettes;
    }

    @Todo(value = NIVEAU.BUG, auteur = "AISSBEN", destinataire = "GITLAB", commentaire = "TestComment")
    private Map<Long, Trottinette> loadTrottinette() {
        try {
            TrottinetteLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TrottinetteLoader.getTrottinettes();
    }

    @Override
    public void start() {
        this.trottinettes = (HashMap<Long, Trottinette>) loadTrottinette();
    }

    @Override
    public void stop() {

    }
}
