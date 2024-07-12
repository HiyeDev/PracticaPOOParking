package dialogs;

import cars.Car;
import color.ColorTransiction;
import color.MyColors;
import data.ExitDAO;
import data.ParkDAO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddExitDialog extends JDialog {
    private JComboBox<String> licensePlateComboBox;
    private JTextField dateField;
    private JTextField timeField;

    public AddExitDialog(Frame parent) {
        super(parent, "Add Exit", true);

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

        // Get the list of parked cars without exit
        List<Car> parkedCars = ExitDAO.getParkedCarsWithoutExit();
        licensePlateComboBox = new JComboBox<>();
        for (Car car : parkedCars) {
            licensePlateComboBox.addItem(car.getLicense_plate());
        }
        licensePlateComboBox.setForeground(MyColors.TURQUOISE);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(licensePlateComboBox, gbc);

        JLabel dateLabel = new JLabel("Exit Date (YYYY-MM-DD):");
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

        JLabel timeLabel = new JLabel("Exit Time (HH:MM):");
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
                String licensePlate = (String) licensePlateComboBox.getSelectedItem();
                Date exitDate = Date.valueOf(dateField.getText());
                Time exitTime = Time.valueOf(timeField.getText() + ":00");

                Car car = new Car(licensePlate);
                ExitDAO.registerExit(car, exitDate, exitTime);
                int parkingDuration = ParkDAO.calculateTimeParking(car);
                
                if (parkingDuration != 0) {
                    double cost = parkingDuration * 0.02;
                    new CostDialog(parent, parkingDuration, cost);
                }
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
