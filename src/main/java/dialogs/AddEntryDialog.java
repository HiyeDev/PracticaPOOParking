package dialogs;

import cars.Car;
import color.ColorTransiction;
import color.MyColors;
import data.EntryDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddEntryDialog extends JDialog {
    private JTextField licensePlateField;
    private JTextField dateField;
    private JTextField timeField;

    public AddEntryDialog(Frame parent) {
        super(parent, "Add Entry", true);

        setSize(500, 250);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel licensePlateLabel = new JLabel("License Plate:");
        licensePlateLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        licensePlateLabel.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(licensePlateLabel, gbc);

        licensePlateField = new JTextField(10);
        licensePlateField.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(licensePlateField, gbc);

        JLabel dateLabel = new JLabel("Entry Date (YYYY-MM-DD):");
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        dateLabel.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(dateLabel, gbc);

        dateField = new JTextField(10);
        dateField.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(dateField, gbc);

        JLabel timeLabel = new JLabel("Entry Time (HH:MM):");
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        timeLabel.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(timeLabel, gbc);

        timeField = new JTextField(10);
        timeField.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(timeField, gbc);

        JButton addButton = new JButton("Add");
        addButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String licensePlate = licensePlateField.getText();
                Date entryDate = Date.valueOf(dateField.getText());
                Time entryTime = Time.valueOf(timeField.getText() + ":00");

                Car car = new Car(licensePlate);
                EntryDAO.registerNewEntry(car, entryDate, entryTime);
                dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ColorTransiction.startColorTransition(addButton, Color.WHITE, MyColors.TURQUOISE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ColorTransiction.startColorTransition(addButton, MyColors.TURQUOISE, Color.WHITE);
            }

        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(MyColors.AQUAMARINE_BLUE);
        panel.add(addButton, gbc);
    }
}
