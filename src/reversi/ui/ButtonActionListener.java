package reversi.ui;

import reversi.GameController;
import reversi.board.BoardLocation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ButtonActionListener implements ActionListener {

    GameController controller;

    public ButtonActionListener(GameController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.occupy((BoardLocation) e.getSource());
    }
}
