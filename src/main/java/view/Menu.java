package view;

import color.MyColors;
import data.ReportDAO;
import data.ResetParkTimeDAO;
import dialogs.AddExitDialog;
import dialogs.AddEntryDialog;
import dialogs.AddOficialCarDialog;
import dialogs.AddResidentCarDialog;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Menu extends javax.swing.JFrame {
    
    private static final int WIDTH = 100;
    private static final int HEIGHT = 100;


    public Menu() {
        initComponents();
        jLabelTextMenu.setForeground(MyColors.TURQUOISE);
        addImagesOptions();
        addDialogsOnButtons();
        setLocationRelativeTo(null);

    }
    
    private void addDialogsOnButtons() {
        addDialogOnClickOficialCar();
        addDialogOnClickResidentCar();
        addDialogOnClickEntry();
        addDialogOnClickExit();
        addDialogOnClickReport();
        addActionOnClickResetMonth();
    }
    
    private void addActionOnClickResetMonth() {
        jLabelStartMonth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(Menu.this, "Are you sure you want to reset all park time values to 0?", "Confirm Reset", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    ResetParkTimeDAO.resetAllParkTimeToZero();
                    JOptionPane.showMessageDialog(Menu.this, "All park time values have been reset to 0.");
        }
            }
        
        });
    }
    
    private void addDialogOnClickReport() {
        jLabelGeneratePayResidents.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String fileName = JOptionPane.showInputDialog(Menu.this, "Enter file name:", "File Name", JOptionPane.PLAIN_MESSAGE);
                if (fileName != null && !fileName.trim().isEmpty()) {
                    try {
                        ReportDAO.generateReport(fileName.trim());
                        JOptionPane.showMessageDialog(Menu.this, "Report generated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(Menu.this, "An error occurred while generating the report.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(Menu.this, "Please enter a valid file name.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        
        });
    }
    
    private void addDialogOnClickExit() {
        jlabelExitRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               AddExitDialog addExitDialog = new AddExitDialog(Menu.this);
               addExitDialog.setVisible(true);
            }
        
        });
    }
    
    private void addDialogOnClickEntry() {
        jLabelEntryRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddEntryDialog addEntryDialog = new AddEntryDialog(Menu.this);
                addEntryDialog.setVisible(true);
            }
        
        });
    }
    
    private void addDialogOnClickOficialCar() {
        jLabelNewOficialCar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddOficialCarDialog addOficialCarDialog = new AddOficialCarDialog(Menu.this);
                addOficialCarDialog.setVisible(true);
            }
        
        });
    }
    
    private void addDialogOnClickResidentCar() {
        jLabelNewResidentCar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AddResidentCarDialog addResidentCarDialog = new AddResidentCarDialog(Menu.this);
                addResidentCarDialog.setVisible(true);
            }
        });
    }
    
    private void addGifOnLabel(JLabel jLabel, String pathGif) {
        ImageIcon staticIcon = (ImageIcon) jLabel.getIcon();
        ImageIcon gifIcon = new ImageIcon(this.getClass().getResource(pathGif));
        
        gifIcon.setImage(gifIcon.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
        
        jLabel.addMouseListener(new MouseAdapter() {
        

            @Override
            public void mouseEntered(MouseEvent e) {
                jLabel.setIcon(gifIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jLabel.setIcon(staticIcon);
            }        
        });
    }

    private void addImagesOptions() {
        Map<JLabel, String[]> jLabelRouteImageMap = new HashMap<>();

        jLabelRouteImageMap.put(jLabelEntryRegister, new String[]{"/images/bandeja-de-entrada-static.png", "/images/bandeja-de-entrada.gif"});
        jLabelRouteImageMap.put(jlabelExitRegister, new String[]{"/images/salida-static.png", "/images/salida.gif"});
        jLabelRouteImageMap.put(jLabelNewOficialCar, new String[]{"/images/coche-de-policia-static.png", "/images/coche-de-policia.gif"});
        jLabelRouteImageMap.put(jLabelNewResidentCar, new String[]{"/images/coche-static.png", "/images/coche.gif"});
        jLabelRouteImageMap.put(jLabelStartMonth, new String[]{"/images/proximo-static.png", "/images/proximo.gif"});
        jLabelRouteImageMap.put(jLabelGeneratePayResidents, new String[]{"/images/impresora-static.png", "/images/impresora.gif"});

        jLabelRouteImageMap.forEach((label, paths) -> {
            String pathPng = paths[0];
            String pathGif = paths[1];
            ImageIcon pngImage =  new ImageIcon(this.getClass().getResource(pathPng));
            pngImage.setImage(pngImage.getImage().getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH));

            label.setIcon(pngImage);
            label.setBorder(BorderFactory.createLineBorder(MyColors.AQUAMARINE_BLUE, 2));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            
            addGifOnLabel(label, pathGif);
        });

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabelNewResidentCar = new javax.swing.JLabel();
        jLabelEntryRegister = new javax.swing.JLabel();
        jlabelExitRegister = new javax.swing.JLabel();
        jLabelNewOficialCar = new javax.swing.JLabel();
        jLabelStartMonth = new javax.swing.JLabel();
        jLabelGeneratePayResidents = new javax.swing.JLabel();
        jLabelTextMenu = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parking App");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(800, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelNewResidentCar.setBackground(new java.awt.Color(153, 255, 255));
        jLabelNewResidentCar.setForeground(new java.awt.Color(204, 255, 255));
        jLabelNewResidentCar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNewResidentCar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelNewResidentCar.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jLabelNewResidentCar.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabelNewResidentCar.setPreferredSize(new java.awt.Dimension(150, 150));
        jLabelNewResidentCar.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jLabelNewResidentCar, gridBagConstraints);

        jLabelEntryRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEntryRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelEntryRegister.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jLabelEntryRegister.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabelEntryRegister.setPreferredSize(new java.awt.Dimension(150, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jLabelEntryRegister, gridBagConstraints);

        jlabelExitRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabelExitRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jlabelExitRegister.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jlabelExitRegister.setMinimumSize(new java.awt.Dimension(150, 150));
        jlabelExitRegister.setPreferredSize(new java.awt.Dimension(150, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jlabelExitRegister, gridBagConstraints);

        jLabelNewOficialCar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNewOficialCar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelNewOficialCar.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jLabelNewOficialCar.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabelNewOficialCar.setPreferredSize(new java.awt.Dimension(150, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jLabelNewOficialCar, gridBagConstraints);

        jLabelStartMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStartMonth.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelStartMonth.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jLabelStartMonth.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabelStartMonth.setPreferredSize(new java.awt.Dimension(150, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jLabelStartMonth, gridBagConstraints);

        jLabelGeneratePayResidents.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelGeneratePayResidents.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelGeneratePayResidents.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jLabelGeneratePayResidents.setMinimumSize(new java.awt.Dimension(150, 150));
        jLabelGeneratePayResidents.setPreferredSize(new java.awt.Dimension(150, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(65, 65, 65, 65);
        jPanel1.add(jLabelGeneratePayResidents, gridBagConstraints);

        jLabelTextMenu.setBackground(new java.awt.Color(255, 255, 255));
        jLabelTextMenu.setFont(new java.awt.Font("Rockwell", 1, 36)); // NOI18N
        jLabelTextMenu.setForeground(new java.awt.Color(204, 204, 204));
        jLabelTextMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTextMenu.setText("APP PARKING ");
        jLabelTextMenu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTextMenu.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(30, 11, 11, 11);
        jPanel1.add(jLabelTextMenu, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelEntryRegister;
    private javax.swing.JLabel jLabelGeneratePayResidents;
    private javax.swing.JLabel jLabelNewOficialCar;
    private javax.swing.JLabel jLabelNewResidentCar;
    private javax.swing.JLabel jLabelStartMonth;
    private javax.swing.JLabel jLabelTextMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jlabelExitRegister;
    // End of variables declaration//GEN-END:variables
}
