package br.com.lucasnbertoldi.gui;

import br.com.lucasnbertoldi.ServicoLucasTV;
import dorkbox.notify.Notify;
import dorkbox.notify.Pos;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class SystemTrayUtils {

    // Criando Icone na Barra de Tarefas
    public static void createSystemTrayIcon() {

        SystemTray systemTray = SystemTray.get();
        if (systemTray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }
        systemTray.setImage(SystemTray.class.getResource("/img/icone.png"));

        systemTray.setStatus("Serviço Lucas TV");
        systemTray.setTooltip("Serviço Lucas TV");

        systemTray.getMenu().add(new MenuItem("Mostrar Serviço", (final ActionEvent e) -> {
            ServicoLucasTV.mainView.setVisible(true);
        }));

        systemTray.getMenu().add(new MenuItem("Sair", (final ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar o serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                ServicoLucasTV.info("Sistema Fechado.");
                System.exit(0);
                systemTray.shutdown();
            }
        }));
    }

    public static void showMessage(String title, String text, String type) {
        Notify notify = Notify.create().text(text).title(title).onAction((Notify t) -> {
            ServicoLucasTV.mainView.setVisible(true);
        }).hideAfter(3000);
        String sistema = System.getProperty("os.name");
        if (sistema.contains("Linux")) {
            notify.position(Pos.TOP_RIGHT);
        } else {
            notify.position(Pos.BOTTOM_RIGHT);
        }
        switch (type) {
            case "info": {
                notify.showInformation();
                break;
            }
            case "error": {
                notify.showError();
                break;
            }
            case "warning": {
                notify.showWarning();
                break;
            }
            default: {
                throw new IllegalArgumentException("Tipo não informado");
            }
        }
    }
}
