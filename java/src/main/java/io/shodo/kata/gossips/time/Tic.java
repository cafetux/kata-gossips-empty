package io.shodo.kata.gossips.time;

import java.util.ArrayList;
import java.util.List;

public class Tic {

    public static final Tic INSTANCE = new Tic();

    private long tic = 0;
    private List<TicObersver> observers = new ArrayList<>();

    private Tic(){

    }

    public void abonne(TicObersver observer) {
        this.observers.add(observer);
    }

    public void nextTic(){
        tic+=1;
        this.observers.forEach(o->o.observeTic(tic));
    }

    public long getTic(){
        return tic;
    }

}
