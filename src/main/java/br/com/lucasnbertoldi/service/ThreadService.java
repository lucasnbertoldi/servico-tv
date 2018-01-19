/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.arduino.SerialService;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukin
 */
public class ThreadService extends Thread {

    @Override
    public void run() {
        KodiService kodiService = new KodiService();
        
        SerialService serialService = new SerialService();
        serialService.initialize(kodiService);

        while (true) {
            if (serialService.error) {
                ServicoLucasTV.info("Preparando para novas tentativas de configuração da porta serial.");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    ServicoLucasTV.LOG.error(ex.getMessage(), ex);
                }
                serialService.initialize(kodiService);
            } else {
                kodiService.updateProperties();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ServicoLucasTV.LOG.error(ex.getMessage(), ex);
            }
        }
    }
    
    
}
