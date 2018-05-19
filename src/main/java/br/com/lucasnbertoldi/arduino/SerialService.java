package br.com.lucasnbertoldi.arduino;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.ViewUtils;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.io.IOException;

public class SerialService {

    public SerialPort comPort;

    public void initialize(KodiService kodiService) {
        if (comPort != null) {
            comPort.closePort();
            comPort = null;
        }
        for (SerialPort commPort : SerialPort.getCommPorts()) {
            comPort = commPort;
        }
        if (comPort == null) {
            ServicoLucasTV.error("Nenhuma porta serial encontrada.");
            return;
        }
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                    return;
                }
                byte[] data = event.getReceivedData();
                String string = new String(data);
                ButtonDTO buttonSelected = null;
                try {
                    buttonSelected = kodiService.read(string);
                } catch (Exception e) {
                    ServicoLucasTV.error("O comando " + string + " foi recebido, mas ocorreu um erro ao processar a informação.", e);
                }
                ViewUtils.printControllerOutput(buttonSelected == null ? string : "Botao: " + buttonSelected.getButtonEnum().getDescription() + " - " + string + "", "info");
                if (buttonSelected != null) {
                    ServicoLucasTV.mainView.getLastButtonPressedDescription().setText(buttonSelected.getButtonEnum().getDescription());
                    switch (buttonSelected.getButtonEnum()) {
                        case OPEN_KODI: {
                            if (kodiService.kodiIsOpen) {
                                String message = "Ué, o KODI já tá aberto o_o";
                                kodiService.sendAMessage(message);
                                ServicoLucasTV.warning(message);
                            } else {
                                if (ServicoLucasTV.SISTEMA.equals("Linux")) {
                                    try {
                                        Runtime.getRuntime().exec("kodi");
                                        ServicoLucasTV.info("Abrindo o KODI...");
                                    } catch (IOException ex) {
                                        ServicoLucasTV.error("Foi mal, mas aconteceu um erro ao tentar abrir o KODI :/", ex);
                                    }
                                } else {
                                    ServicoLucasTV.warning("Foi mal, mas a funcionalidade de abrir o KODI não funciona para o sistema operacional " + ServicoLucasTV.SISTEMA + " :/");
                                }
                            }
                        }
                    }
                } else {
                    ServicoLucasTV.warning("Comando não encontrado. Comando: " + string);
                    ServicoLucasTV.mainView.getLastButtonPressedDescription().setText(" ");
                }
                ServicoLucasTV.mainView.getAddCodeField().setText(string);
            }
        });
    }
}
