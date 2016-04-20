package reversi.ui;

import reversi.GameController;
import reversi.board.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BoardView extends JFrame implements BoardObserver {

    private static final Color UNCLAIMED_COLOR = Color.GRAY;
    private static final Color LIGHT_COLOR = Color.white;
    private static final Color DARK_COLOR = Color.black;


    private BoardModel model;
    private GameController controller;
    private Button[][] buttons;

    public BoardView(BoardModel model, GameController controller) {
        int size = model.getSize();
        this.model = model;
        this.controller = controller;
        this.buttons = new Button[size][size];
    }

    public void render() {
        setTitle("Reversi");

        ScorePanel scorePanel = new ScorePanel(model);
        model.addObserver(scorePanel);
        add(scorePanel, BorderLayout.NORTH);
        add(createButtonPanel(new ButtonActionListener(controller)), BorderLayout.CENTER);

        setBackground(Color.darkGray);
        setVisible(true);
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private JPanel createButtonPanel(ButtonActionListener listener) {
        int size = buttons.length;
        JPanel buttonPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(size, size);
        gridLayout.setHgap(1);
        gridLayout.setVgap(1);

        buttonPanel.setLayout(gridLayout);

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                Button button = new Button(row, column);
                button.setBorder(new EmptyBorder(0, 0, 0, 0));
                button.setPreferredSize(new Dimension(20, 20));
                button.setBackground(UNCLAIMED_COLOR);
                button.addActionListener(listener);

                buttons[row][column] = button;
                buttonPanel.add(button);
            }
        }
        return buttonPanel;
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
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons.length; col++) {
                Button button = buttons[row][col];
                toggleButtonColor(button);
            }
        }
    }

    private void toggleButtonColor(Button button) {
        switch (model.get(button)) {
            case DARK:
                button.setBackground(DARK_COLOR);
                break;
            case LIGHT:
                button.setBackground(LIGHT_COLOR);
                break;
            case NEUTRAL:
                button.setBackground(UNCLAIMED_COLOR);
                break;
        }
    }

}
