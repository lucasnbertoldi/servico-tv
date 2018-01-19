package br.com.lucasnbertoldi;

import br.com.lucasnbertoldi.service.ThreadService;
import br.com.lucasnbertoldi.gui.MainView;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.gui.ViewUtils;
import gnu.io.NoSuchPortException;
import java.awt.AWTException;
import org.apache.log4j.Logger;

public class ServicoLucasTV {

    public static MainView mainView;
    public static final Logger LOG = Logger.getLogger(ServicoLucasTV.class);

    public static void main(String[] args) throws NoSuchPortException {

        ViewUtils.setarVisualPadrao();

        mainView = new MainView();
        mainView.setVisible(true);

        ServicoLucasTV.info("Iniciando Sistema");

        try {
            SystemTrayUtils.createSystemTrayIcon();
        } catch (AWTException ex) {
            ServicoLucasTV.error("Erro ao iniciar icone do serviço.", ex);
        }

        ServicoLucasTV.info("Carregando Configurações.");
        try {
            ViewUtils.setConfigurationFields();
        } catch (RuntimeException e) {
            ServicoLucasTV.error("Erro ao carregar configurações.", e);
        }

        ServicoLucasTV.info("Inicializando serial.");
        
        Thread t = new ThreadService();
        t.start();
    }

    public static void error(String message, Throwable cause) {
        LOG.error(message, cause);
        ViewUtils.printOutput(message, "danger");
    }

    public static void info(String message) {
        ViewUtils.printOutput(message, "info");
        LOG.info(message);
    }

    public static void warning(String message) {
        ViewUtils.printOutput(message, "warning");
        LOG.info(message);
    }
}
