package tiw1.emprunt.contexte;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    List<Observer> observers;

    Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer obs){
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notifyObservers() {
        for (Observer obs : observers)
            obs.update();
    }

}
