package br.com.lucasnbertoldi.gui;

import br.com.lucasnbertoldi.ServicoLucasTV;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class SystemTrayUtils {

    // Criando Icone na Barra de Tarefas
    public static void createSystemTrayIcon() throws AWTException {

        if (SystemTray.isSupported()) {
            MouseListener mouseListener;
            mouseListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        if (e.getClickCount() == 2) {
                            ServicoLucasTV.mainView.setVisible(true);
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }
            };

            PopupMenu menu = new PopupMenu();

            MenuItem mostrarAuxiliar = new MenuItem("Mostrar Serviço");
            mostrarAuxiliar.addActionListener((ActionEvent e) -> {
                ServicoLucasTV.mainView.setVisible(true);
            });
            menu.add(mostrarAuxiliar);
            menu.addSeparator();
            MenuItem quitItem = new MenuItem("Sair");
            quitItem.addActionListener((ActionEvent e) -> {
                int option = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar o serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ServicoLucasTV.info("Sistema Fechado.");
                    System.exit(0);
                }
            });
            menu.add(quitItem);
            
            SystemTray tray = SystemTray.getSystemTray();

            ImageIcon icon = new ImageIcon(SystemTray.class.getResource("/img/icone.png"));
            Image image = icon.getImage();

            TrayIcon trayIcon = new TrayIcon(image, "Serviço Lucas-TV", menu);
            trayIcon.setImageAutoSize(true);
            tray.add(trayIcon);
            trayIcon.addMouseListener(mouseListener);
        }
    }

    public static void showMessage(String title, String text, String type) {

        TrayIcon trayIcon = SystemTray.getSystemTray().getTrayIcons()[0];

        switch (type) {
            case "info": {
                trayIcon.displayMessage(title, text, TrayIcon.MessageType.INFO);
                break;
            }
            case "error": {
                trayIcon.displayMessage(title, text, TrayIcon.MessageType.ERROR);
                break;
            }
            case "warning": {
                trayIcon.displayMessage(title, text, TrayIcon.MessageType.WARNING);
                break;
            }
            default: {
                throw new IllegalArgumentException("Tipo não informado");
            }
        }
    }
}
