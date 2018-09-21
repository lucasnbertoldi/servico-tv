/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.gui;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author lukin
 */
public class MainView extends javax.swing.JFrame {

    /**
     * Creates new form MainView
     */
    public MainView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        scrollLogTextArea = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        buttonComboBox = new javax.swing.JComboBox<>();
        codeComboBox = new javax.swing.JComboBox<>();
        removeCodeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        addCodeField = new javax.swing.JTextField();
        addCodeButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        lastButtonPressedDescription = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        urlKODIField = new javax.swing.JTextField();
        userField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        passwordField = new javax.swing.JPasswordField();
        sistemaField = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        showScreenCheckbox = new javax.swing.JCheckBox();
        showLogControllerCheckBox = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        delayKodiField = new javax.swing.JFormattedTextField();
        jLabel12 = new javax.swing.JLabel();
        delayNumberField = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        delayMouseField = new javax.swing.JFormattedTextField();

        setTitle("Serviço Lucas TV");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        logTextArea.setEditable(false);
        logTextArea.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        scrollLogTextArea.setViewportView(logTextArea);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Saída:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollLogTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(scrollLogTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addGap(35, 35, 35))
        );

        jTabbedPane1.addTab("Saída", jPanel1);

        jPanel2.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel2ComponentShown(evt);
            }
        });

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        buttonComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0 - Selecione um Botão" }));
        buttonComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonComboBoxActionPerformed(evt);
            }
        });

        codeComboBox.setEnabled(false);

        removeCodeButton.setText("X");
        removeCodeButton.setEnabled(false);
        removeCodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCodeButtonActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Lista de Botões");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lista de Códigos do Botão");

        addCodeField.setEnabled(false);

        addCodeButton.setText("Adicionar");
        addCodeButton.setEnabled(false);
        addCodeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCodeButtonActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Adicionar Novo Código");

        lastButtonPressedDescription.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lastButtonPressedDescription.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(codeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(removeCodeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(addCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(addCodeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastButtonPressedDescription, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCodeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lastButtonPressedDescription)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeCodeButton))
                .addGap(44, 44, 44)
                .addComponent(jButton1)
                .addGap(71, 71, 71))
        );

        jTabbedPane1.addTab("Botões", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel3.add(urlKODIField, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 40, 441, -1));
        jPanel3.add(userField, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 98, 205, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("URL KODI");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 20, 456, -1));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Senha");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 78, 204, -1));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Sistema");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 136, 205, -1));

        jButton2.setText("Salvar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(176, 303, 156, -1));
        jPanel3.add(passwordField, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 98, 204, -1));

        sistemaField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Windows", "Linux" }));
        sistemaField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sistemaFieldActionPerformed(evt);
            }
        });
        jPanel3.add(sistemaField, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 156, 205, -1));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Usuário");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 78, 205, -1));

        showScreenCheckbox.setText("Mostrar Tela ao Iniciar");
        showScreenCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showScreenCheckboxActionPerformed(evt);
            }
        });
        jPanel3.add(showScreenCheckbox, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 252, 204, -1));

        showLogControllerCheckBox.setText("Exibir Log de Requisições do Controle");
        showLogControllerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLogControllerCheckBoxActionPerformed(evt);
            }
        });
        jPanel3.add(showLogControllerCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 252, -1, -1));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Delay para Abrir Kodi");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 136, 204, -1));

        delayKodiField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jPanel3.add(delayKodiField, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 156, 204, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Delay para Movimentar Mouse");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 194, 205, -1));

        delayNumberField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jPanel3.add(delayNumberField, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 214, 204, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Delay para Tecla Numérica");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 194, 204, -1));

        delayMouseField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jPanel3.add(delayMouseField, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 214, 205, -1));

        jTabbedPane1.addTab("Configurações", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        SystemTrayUtils.showMessage("Mensagem", "O serviço ainda está executando.", "info");
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ConfigurationDTO config;
        try {
            config = ConfigurationService.getConfiguration();
        } catch (Exception e) {
            config = new ConfigurationDTO();
        }
        try {
            ConfigurationService.setConfiguration(config, buttonList);
            ServicoLucasTV.info("Configurações de botões salvas.");
            JOptionPane.showMessageDialog(this, "Configurações salvas com sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ServicoLucasTV.error("Erro ao Salvar Configurações.", ex);
            JOptionPane.showMessageDialog(this, "Erro ao salvar configurações.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ConfigurationDTO config;
        try {
            config = ConfigurationService.getConfiguration();
        } catch (Exception e) {
            config = new ConfigurationDTO();
        }

        try {
            config.password = new String(passwordField.getPassword());
            config.urlKODI = urlKODIField.getText();
            config.user = userField.getText();
            config.sistema = sistemaField.getSelectedItem().toString();
            config.showScreen = showScreenCheckbox.isSelected();
            config.showControllerLOG = showLogControllerCheckBox.isSelected();
            config.delayMouse = Integer.parseInt(delayMouseField.getText());
            config.delayOpenKodi = Integer.parseInt(delayKodiField.getText());
            config.delayNumber = Integer.parseInt(delayNumberField.getText());

            ConfigurationService.setConfiguration(config, ConfigurationService.buttonList);
            ServicoLucasTV.info("Configurações gerais salvas.");
            JOptionPane.showMessageDialog(this, "Configurações salvas com sucesso.", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException | IOException ex) {
            ServicoLucasTV.error("Erro ao Salvar Configurações.", ex);
            JOptionPane.showMessageDialog(this, "Erro ao salvar configurações.", "Erro", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void sistemaFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sistemaFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sistemaFieldActionPerformed

    private void showScreenCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showScreenCheckboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showScreenCheckboxActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        if (ServicoLucasTV.SISTEMA.equals("Linux")) {
            JFrame.setDefaultLookAndFeelDecorated(true);
            ImageIcon icon = new ImageIcon(ServicoLucasTV.PATH_PRINCIPAL_LINUX + "img/icone-maior.png");
            Image image = icon.getImage();
            setIconImage(image);
        } else {
            setIconImage(new ImageIcon(getClass().getResource("/img/icone-maior.png")).getImage());
        }

    }//GEN-LAST:event_formComponentShown

    private void removeCodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeCodeButtonActionPerformed
        buttonDTOSelected.getCodeList().remove(codeComboBox.getSelectedItem());
        codeComboBox.removeItem(codeComboBox.getSelectedItem());
    }//GEN-LAST:event_removeCodeButtonActionPerformed

    private void buttonComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonComboBoxActionPerformed
        if (buttonComboBox.getSelectedIndex() == 0) {
            codeComboBox.removeAllItems();
            addCodeButton.setEnabled(false);
            addCodeField.setEnabled(false);
            codeComboBox.setEnabled(false);
            removeCodeButton.setEnabled(false);
        } else {
            for (ButtonDTO buttonDTO : buttonList) {
                String text = buttonDTO.getButtonEnum().getId() + " - " + buttonDTO.getButtonEnum().getDescription();
                if (text.equals(buttonComboBox.getSelectedItem())) {
                    buttonDTOSelected = buttonDTO;
                    codeComboBox.removeAllItems();
                    for (String code : buttonDTO.getCodeList()) {
                        codeComboBox.addItem(code);
                    }
                    addCodeButton.setEnabled(true);
                    addCodeField.setEnabled(true);
                    codeComboBox.setEnabled(true);
                    removeCodeButton.setEnabled(true);
                }
            }
        }

    }//GEN-LAST:event_buttonComboBoxActionPerformed

    private void jPanel2ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel2ComponentShown
        buttonList = new ArrayList<>();

        for (ButtonDTO buttonDTO : ConfigurationService.buttonList) {
            ButtonDTO newButtonDTO = new ButtonDTO(buttonDTO.getButtonEnum());
            newButtonDTO.setCodeList(buttonDTO.getTextCodeList());
            buttonList.add(newButtonDTO);
        }

        for (ButtonDTO buttonDTO : buttonList) {
            buttonComboBox.addItem(buttonDTO.getButtonEnum().getId() + " - " + buttonDTO.getButtonEnum().getDescription());
        }

        buttonComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_jPanel2ComponentShown

    private void addCodeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCodeButtonActionPerformed
        if (buttonDTOSelected.getCodeList().contains(addCodeField.getText())) {
            JOptionPane.showMessageDialog(this, "Tá doido? Esse código já tá na lista.", "Erro", JOptionPane.WARNING_MESSAGE);
        } else if (addCodeField.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Na boa, digita um código aí.", "Erro", JOptionPane.WARNING_MESSAGE);
        } else {
            buttonDTOSelected.getCodeList().add(addCodeField.getText());
            codeComboBox.addItem(addCodeField.getText());
            addCodeField.setText("");
        }
    }//GEN-LAST:event_addCodeButtonActionPerformed

    private void showLogControllerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLogControllerCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_showLogControllerCheckBoxActionPerformed

    ButtonDTO buttonDTOSelected;
    List<ButtonDTO> buttonList;

    public JTextField getPasswordField() {
        return passwordField;
    }

    public JTextField getUrlKODIField() {
        return urlKODIField;
    }

    public JTextField getUserField() {
        return userField;
    }

    public JComboBox<String> getSistemaField() {
        return sistemaField;
    }

    public JCheckBox getShowScreenCheckbox() {
        return showScreenCheckbox;
    }

    public JTextField getAddCodeField() {
        return addCodeField;
    }

    public JLabel getLastButtonPressedDescription() {
        return lastButtonPressedDescription;
    }

    public JCheckBox getShowLogCheckBox() {
        return showLogControllerCheckBox;
    }

    public JFormattedTextField getDelayKodiField() {
        return delayKodiField;
    }

    public JFormattedTextField getDelayMouseField() {
        return delayMouseField;
    }

    public JFormattedTextField getDelayNumberField() {
        return delayNumberField;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCodeButton;
    private javax.swing.JTextField addCodeField;
    private javax.swing.JComboBox<String> buttonComboBox;
    private javax.swing.JComboBox<String> codeComboBox;
    private javax.swing.JFormattedTextField delayKodiField;
    private javax.swing.JFormattedTextField delayMouseField;
    private javax.swing.JFormattedTextField delayNumberField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lastButtonPressedDescription;
    protected javax.swing.JTextPane logTextArea;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton removeCodeButton;
    protected javax.swing.JScrollPane scrollLogTextArea;
    private javax.swing.JCheckBox showLogControllerCheckBox;
    private javax.swing.JCheckBox showScreenCheckbox;
    private javax.swing.JComboBox<String> sistemaField;
    private javax.swing.JTextField urlKODIField;
    private javax.swing.JTextField userField;
    // End of variables declaration//GEN-END:variables
}
