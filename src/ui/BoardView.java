package ui;

import reversi.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BoardView extends JFrame implements BoardObserver {

    BoardModelImpl board;
    Button[][] buttons;

    public BoardView(BoardModelImpl board) {
        this.board = board;
        this.buttons = new Button[board.getSize()][board.getSize()];

        JPanel buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(board.getSize(), board.getSize());
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        buttonPanel.setLayout(gridLayout);

        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                Button button = new Button(row, column);
                button.setBorder(new EmptyBorder(0, 0, 0, 0));
                button.setPreferredSize(new Dimension(20, 20));
                button.setBackground(Color.GRAY);

                button.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Button source = (Button) e.getSource();
                        board.occupy(source, board.getCurrentPlayer());
                        board.makeTurn();
                    }
                });

                buttons[row][column] = button;
                buttonPanel.add(button);
            }
        }

        add(buttonPanel);
        setBackground(Color.darkGray);
    }

    public void render() {
        setTitle("Reversi");
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void notify(BoardChangedEvent event) {
        if (event instanceof BoardChangedEvents.ResetEvent) {
            resetBoard();
        } else if (event instanceof BoardChangedEvents.LocationChanged) {
            BoardLocation changed = ((BoardChangedEvents.LocationChanged) event).getLocation();
            toggleButtonColor(buttons[changed.getRow()][changed.getColumn()]);
        }
    }

    private void resetBoard() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                Button button = buttons[row][col];
                toggleButtonColor(button);
            }
        }
    }

    private void toggleButtonColor(Button button) {
        switch (board.get(button)) {
            case DARK:
                button.setBackground(Color.black);
                break;
            case LIGHT:
                button.setBackground(Color.white);
                break;
        }
    }

    private static class Button extends JButton implements BoardLocation {

        int row;
        int column;

        public Button(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {
            return column;
        }
    }

    public static void main(String[] args) {
        BoardModelImpl model = new BoardModelImpl(8);
        BoardView boardView = new BoardView(model);
        model.addObserver(boardView);

        boardView.render();
        model.resetBoard();
    }
}
