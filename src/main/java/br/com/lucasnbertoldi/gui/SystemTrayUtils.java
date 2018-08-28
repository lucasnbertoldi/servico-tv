package br.com.lucasnbertoldi.gui;

import br.com.lucasnbertoldi.ServicoLucasTV;
import dorkbox.notify.Notify;
import dorkbox.notify.Pos;
import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.SystemTray;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.commons.io.IOUtils;

public class SystemTrayUtils {

    public static final String PATH_ICONE_MENOR = "img/icone-menor.png";
    
    private static Notify notify;

    public static void createSystemTrayIconDorkBox() {
        if (ServicoLucasTV.SISTEMA.equals("Linux")) {
            SystemTray.FORCE_GTK2 = true;
        }

        SystemTray systemTray = SystemTray.get();

        if (systemTray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }

        if (ServicoLucasTV.SISTEMA.equals("Linux")) {
            systemTray.setImage(new File(ServicoLucasTV.PATH_PRINCIPAL_LINUX + PATH_ICONE_MENOR));
        } else {
            systemTray.setImage(SystemTray.class.getResource("/" + PATH_ICONE_MENOR));
        }

        systemTray.setStatus("Serviço Lucas TV");
        systemTray.setTooltip("Serviço Lucas TV");

        systemTray.getMenu().add(new MenuItem("Mostrar Serviço", (final ActionEvent e) -> {
            ServicoLucasTV.mainView.setVisible(true);
        }));

        systemTray.getMenu().add(new MenuItem("Sair", (final ActionEvent e) -> {
            int option = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar o serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                ServicoLucasTV.info("Sistema Fechado.");
                systemTray.shutdown();
                System.exit(0);
            }
        }));
    }

    // Criando Icone na Barra de Tarefas
    public static void createSystemTrayIcon() {
        if (java.awt.SystemTray.isSupported()) {
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

            java.awt.MenuItem mostrarAuxiliar = new java.awt.MenuItem("Mostrar Serviço");
            mostrarAuxiliar.addActionListener((ActionEvent e) -> {
                ServicoLucasTV.mainView.setVisible(true);
            });
            menu.add(mostrarAuxiliar);
            java.awt.MenuItem quitItem = new java.awt.MenuItem("Sair");
            quitItem.addActionListener((ActionEvent e) -> {
                int option = JOptionPane.showConfirmDialog(null, "Deseja realmente fechar o Serviço?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ServicoLucasTV.info("Sistema Fechado.");
                    System.exit(0);
                }
            });
            menu.add(quitItem);

            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();

            byte[] imagebyte = null;
            try {
                if (ServicoLucasTV.SISTEMA.equals("Linux")) {
                    imagebyte = IOUtils.toByteArray(new FileInputStream(new File(ServicoLucasTV.PATH_PRINCIPAL_LINUX + "img/icone.png")));
                } else {
                    imagebyte = IOUtils.toByteArray(java.awt.SystemTray.class.getResourceAsStream("/img/icone.png"));
                }
            } catch (IOException ex) {
                ServicoLucasTV.error("Erro ao recuperar imagem do icone.", ex);
            }
            ImageIcon icon = new ImageIcon(imagebyte);
            Image image = icon.getImage();

            TrayIcon trayIcon = new TrayIcon(image, "Serviço Lucas TV", menu);
            trayIcon.setImageAutoSize(true);
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                ServicoLucasTV.error("Erro ao iniciar o icone de notificação.", ex);
            }
            trayIcon.addMouseListener(mouseListener);
        }
    }

    public static void  showMessage(String title, String text, String type) {
        notify = Notify.create().text(text).title(title).onAction((Notify t) -> {
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
