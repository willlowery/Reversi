package reversi.ui;

import reversi.board.BoardChangedEvent;
import reversi.board.BoardModel;
import reversi.board.BoardObserver;

import javax.swing.*;
import java.awt.*;

class ScorePanel extends JPanel implements BoardObserver {

    private final JLabel dark;
    private final JLabel unclaimed;
    private final JLabel light;
    private BoardModel model;

    ScorePanel(BoardModel model) {
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BorderLayout());


        this.model = model;


        dark = new JLabel("Dark");
        unclaimed = new JLabel("Unclaimed", SwingConstants.CENTER);
        light = new JLabel("Light");

        add(dark, BorderLayout.LINE_START);
        add(unclaimed, BorderLayout.CENTER);
        add(light, BorderLayout.LINE_END);

    }


    @Override
    public void notify(BoardChangedEvent event) {
        int dark = 0;
        int light = 0;
        int unclaimed = 0;

        for (int row = 0; row < model.getSize(); row++) {
            for (int col = 0; col < model.getSize(); col++) {
                switch (model.get(new Location(row, col))) {
                    case DARK:
                        dark++;
                        break;
                    case LIGHT:
                        light++;
                        break;
                    case NEUTRAL:
                        unclaimed++;
                        break;
                }
            }
        }

        this.light.setText("Light " + light);
        this.dark.setText("Dark " + dark);
        this.unclaimed.setText("Unclaimed " + unclaimed);

    }
}
