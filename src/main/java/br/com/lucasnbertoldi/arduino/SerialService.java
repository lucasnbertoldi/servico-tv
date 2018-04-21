package br.com.lucasnbertoldi.arduino;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.ViewUtils;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

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
                try {
                    kodiService.read(string);
                } catch (Exception e) {
                    ServicoLucasTV.error("O comando " + string + " foi recebido, mas ocorreu um erro ao processar a informação.", e);
                }
                ViewUtils.printControllerOutput(string, "info");
            }
        });
    }
}
