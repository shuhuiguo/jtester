package org.jtester.spec.demo.gameoflife;

public interface GameObserver {

    GameObserver NULL = new GameObserver() {

        public void gridChanged(Grid grid) {}
        
    };

    void gridChanged(Grid grid);

}
