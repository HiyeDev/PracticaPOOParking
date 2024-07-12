package dialogs;

import color.MyColors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CostDialog extends JDialog {
    public CostDialog(Frame parent, int minutes, double cost) {
        super(parent, "Parking Cost", true);
        setSize(500, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        add(panel);

        JLabel messageLabel = new JLabel(
            String.format("The parking cost for %d minutes is: %.2f €", minutes, cost),
            SwingConstants.CENTER
        );
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        messageLabel.setForeground(MyColors.TURQUOISE);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(okButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
