package br.com.lucasnbertoldi.arduino;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.service.SystemService;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class SerialService {

    public SerialPort comPort;
    public static boolean disabled = false;

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
                ButtonDTO buttonSelected = read(string);
                try {
                    if (buttonSelected != null) {
                        verifyDisable(buttonSelected, kodiService);
                    }
                    if (!disabled) {
                        if (buttonSelected != null) {
                            boolean kodyRead = false;
                            if (kodiService.kodiIsOpen) {
                                kodyRead = kodiService.read(buttonSelected);
                            }
                            if (!kodyRead) {
                                SystemService.readSystemCommand(buttonSelected, kodiService);
                            }
                        }
                    } else {
                        if (buttonSelected != null) {
                            if (!buttonSelected.getButtonEnum().equals(ButtonEnum.DISABLE)) {
                                if (!kodiService.kodiIsOpen) {
                                    SystemTrayUtils.showMessage("Mensagem", "O controle está desativado.", "warning");
                                } else {
                                    kodiService.sendAMessage("O controle está desativado.");
                                }
                            }
                        }
                    }
                    ServicoLucasTV.mainView.getAddCodeField().setText(string);
                    if (buttonSelected == null) {
                        if (ConfigurationService.getConfiguration().showControllerLOG) {
                            ServicoLucasTV.warning("Comando não encontrado. Comando: " + string);
                        }
                        ServicoLucasTV.mainView.getLastButtonPressedDescription().setText(" ");
                    } else {
                        ServicoLucasTV.mainView.getLastButtonPressedDescription().setText(buttonSelected.getButtonEnum().getDescription());
                    }
                } catch (Exception e) {
                    ServicoLucasTV.error("O comando " + string + " foi recebido, mas ocorreu um erro ao processar a informação.", e);
                }

            }
        });
    }

    private ButtonDTO read(String text) {
        ButtonDTO buttonSelected = null;
        for (ButtonDTO buttonDTO : ConfigurationService.buttonList) {
            if (buttonDTO.getCodeList().contains(text)) {
                buttonSelected = buttonDTO;
            }
        }
        return buttonSelected;
    }

    private void verifyDisable(ButtonDTO buttonSelected, KodiService kodiService) {
        switch (buttonSelected.getButtonEnum()) {
            case DISABLE: {
                if (SerialService.disabled) {
                    SystemService.showLOGController("Desativação do Controle Remoto");
                    if (kodiService.kodiIsOpen) {
                        kodiService.sendAMessage("Controle Remoto Ativado.");
                    } else {
                        SystemTrayUtils.showMessage("Mensagem", "Controle Remoto Ativado.", "info");
                    }
                    SerialService.disabled = false;
                } else {
                    SerialService.disabled = true;
                    SystemService.showLOGController("Ativação do Controle Remoto");
                    if (kodiService.kodiIsOpen) {
                        kodiService.sendAMessage("Controle Remoto Desativado.");
                    } else {
                        SystemTrayUtils.showMessage("Mensagem", "Controle Remoto Desativado.", "info");
                    }
                }
                break;
            }
        }
    }

}
