package br.com.lucasnbertoldi;

import br.com.lucasnbertoldi.service.ThreadService;
import br.com.lucasnbertoldi.gui.MainView;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.gui.ViewUtils;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import br.com.lucasnbertoldi.service.configuration.ConfigurationDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import java.util.EnumSet;
import org.apache.log4j.Logger;

public class ServicoLucasTV {

    public static MainView mainView;
    public static final Logger LOG = Logger.getLogger(ServicoLucasTV.class);
    public static final String PATH_PRINCIPAL_LINUX = "/home/lucas/Serviço/";
    public static final String NOME_SOFTWARE = "Serviço Lucas TV";
    public static String SISTEMA = "";

    public static void main(String[] args) {

        EnumSet.allOf(ButtonEnum.class)
                .forEach(button -> {
                    ButtonDTO buttonDTO = new ButtonDTO(button);
                    ConfigurationService.buttonList.add(buttonDTO);
                });

        ViewUtils.setarVisualPadrao();

        mainView = new MainView();

        ServicoLucasTV.info("Iniciando Sistema");

        SystemTrayUtils.createSystemTrayIconDorkBox();

        ConfigurationDTO config;
        try {
            config = ConfigurationService.getConfiguration();

            ServicoLucasTV.info("Carregando Configurações.");
            ViewUtils.setConfigurationFields();
        } catch (RuntimeException e) {
            ServicoLucasTV.error("Erro ao carregar configurações.", e);
            config = new ConfigurationDTO();
        }

        mainView.setVisible(config.showScreen);

        ServicoLucasTV.info("Inicializando serial.");

        Thread t = new ThreadService();
        t.start();

    }

    public static void error(String message, Throwable cause) {
        LOG.error(message, cause);
        ViewUtils.printOutput(message, "danger");
    }

    public static void error(String message) {
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
