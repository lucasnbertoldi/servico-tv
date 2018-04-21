package br.com.lucasnbertoldi.gui;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.service.configuration.ConfigurationDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import br.com.lucasnbertoldi.service.DataService;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ViewUtils {


    /**
     * Seta o visual do programa de acordo com o Sistema Operacional que o
     * inicia.
     */
    public static void setarVisualPadrao() {
        String sistema = System.getProperty("os.name");
        ServicoLucasTV.LOG.info("Sistema operacional: " + sistema);
        try {
            if (sistema.contains("Linux")) {
                ServicoLucasTV.SISTEMA = "Linux";
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } else {
                ServicoLucasTV.SISTEMA = sistema;
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao definir visual!", "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void printOutput(String texto, String type) {
        clearOutput(ServicoLucasTV.mainView.logTextArea);
        
        
        DataService data = new DataService();

        StyledDocument document = (StyledDocument) ServicoLucasTV.mainView.logTextArea.getStyledDocument();
        SimpleAttributeSet keyWordColor = new SimpleAttributeSet();
        switch (type) {
            case "info": {
                StyleConstants.setForeground(keyWordColor, Color.BLUE);
                break;
            }
            case "warning": {
                StyleConstants.setForeground(keyWordColor, Color.GRAY);
                break;
            }
            case "danger": {
                StyleConstants.setForeground(keyWordColor, Color.RED);
                break;
            }
        }
        StyleConstants.setBold(keyWordColor, true);
        try {
            document.insertString(document.getLength(), data.formatarData(data.getDataSistema(), "dd/MM/yyyy HH:mm:ss") + " : ", null);
            document.insertString(document.getLength(), texto + "\n", keyWordColor);
        } catch (BadLocationException ex) {
            ServicoLucasTV.LOG.error("Erro ao inserir texto na área de texto", ex);
        }
        ServicoLucasTV.mainView.logTextArea.setCaretPosition(document.getLength());
    }
    
    private static void clearOutput(javax.swing.JTextPane textPanel){
        if (textPanel.getText().length() > 999999) {
            textPanel.setText("");
        }
    }

    public static void printControllerOutput(String texto, String type) {
        clearOutput(ServicoLucasTV.mainView.logOutputController);

        DataService data = new DataService();

        StyledDocument document = (StyledDocument) ServicoLucasTV.mainView.logOutputController.getStyledDocument();
        SimpleAttributeSet keyWordColor = new SimpleAttributeSet();
        switch (type) {
            case "info": {
                StyleConstants.setForeground(keyWordColor, Color.BLUE);
                break;
            }
            case "warning": {
                StyleConstants.setForeground(keyWordColor, Color.GRAY);
                break;
            }
            case "danger": {
                StyleConstants.setForeground(keyWordColor, Color.RED);
                break;
            }
        }
        StyleConstants.setBold(keyWordColor, true);
        try {
            document.insertString(document.getLength(), data.formatarData(data.getDataSistema(), "dd/MM/yyyy HH:mm:ss") + " : ", null);
            document.insertString(document.getLength(), texto + "\n", keyWordColor);
        } catch (BadLocationException ex) {
            ServicoLucasTV.LOG.error("Erro ao inserir texto na área de texto", ex);
        }
        ServicoLucasTV.mainView.logOutputController.setCaretPosition(document.getLength());
    }

    public static void setConfigurationFields() {
        ConfigurationDTO config = ConfigurationService.getConfiguration();
        ServicoLucasTV.mainView.getUpField().setText(config.getTextUp());
        ServicoLucasTV.mainView.getDownField().setText(config.getTextDown());
        ServicoLucasTV.mainView.getLeftField().setText(config.getTextLeft());
        ServicoLucasTV.mainView.getRightField().setText(config.getTextRight());
        ServicoLucasTV.mainView.getOkField().setText(config.getTextOk());
        ServicoLucasTV.mainView.getBackField().setText(config.getTextBack());
        ServicoLucasTV.mainView.getVolumeDownsField().setText(config.getTextVolumeDown());
        ServicoLucasTV.mainView.getVolumeUpField().setText(config.getTextVolumeUp());
        ServicoLucasTV.mainView.getSubtitleScreenField().setText(config.getTextSubtitleScreen());
        ServicoLucasTV.mainView.getPlayPauseField().setText(config.getTextPlayPause());
        ServicoLucasTV.mainView.getStopField().setText(config.getTextStop());
        ServicoLucasTV.mainView.getSettingsField().setText(config.getTextSettings());
        ServicoLucasTV.mainView.getDisableField().setText(config.getTextDisable());

        ServicoLucasTV.mainView.getUrlKODIField().setText(config.urlKODI);
        ServicoLucasTV.mainView.getUserField().setText(config.user);
        ServicoLucasTV.mainView.getPasswordField().setText(config.password);
        ServicoLucasTV.mainView.getSistemaField().setSelectedItem(config.sistema);
        ServicoLucasTV.mainView.getShowScreenCheckbox().setSelected(config.showScreen);

    }

}
