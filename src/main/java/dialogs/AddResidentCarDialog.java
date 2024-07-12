package dialogs;

import cars.ResidentCar;
import color.ColorTransiction;
import color.MyColors;
import data.ResidentCarDAO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddResidentCarDialog extends JDialog{
    private JTextField licensePlateField;
    
    public AddResidentCarDialog(Frame parent) {
        super(parent, "Add Resident Car", true);
        
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

        JButton addButton = new JButton("Add");
        addButton.addMouseListener(new MouseAdapter() {
                      
            @Override
            public void mouseClicked(MouseEvent e) {
                ResidentCarDAO.registerResidentCar(new ResidentCar(licensePlateField.getText()));
                dispose();
            }

          @Override
            public void mouseEntered(MouseEvent e) {
                ColorTransiction.startColorTransition(addButton, Color.WHITE, MyColors.TURQUOISE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ColorTransiction.startColorTransition(addButton, MyColors.TURQUOISE, MyColors.WHITE);
            }

        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(MyColors.AQUAMARINE_BLUE);
        panel.add(addButton, gbc);
        
    }
}
