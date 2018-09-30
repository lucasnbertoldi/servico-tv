/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service.automation;

import br.com.lucasnbertoldi.arduino.SerialService;
import br.com.lucasnbertoldi.service.SystemService;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;

/**
 *
 * @author Lucas
 */
public class AutomationService {

    private static boolean isSendding = false;

    public synchronized static void changeLightState(ButtonDTO button, SerialService serialService) {
        switch (button.getButtonEnum()) {
            case LIGHT: {
                if (!isSendding) {
                    isSendding = true;
                    serialService.write("light");
                    SystemService.showLOGController(button.getButtonEnum().getDescription());
                    isSendding = false;
                }
                break;
            }
        }
    }
}
