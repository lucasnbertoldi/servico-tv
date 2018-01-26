/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.arduino.SerialService;
import br.com.lucasnbertoldi.service.kodi.KodiService;

/**
 *
 * @author lukin
 */
public class ThreadService extends Thread {

    @Override
    public void run() {
        KodiService kodiService = new KodiService();

        SerialService serialService = new SerialService();
        //serialService.initialize(kodiService);

        while (true) {
            if (serialService.error) {
                ServicoLucasTV.info("Preparando para novas tentativas de configuração da porta serial.");
                delay(5000);
                serialService.initialize(kodiService);
            } else {
                try {
                    kodiService.updateProperties();
                    //kodiService.read("92");
                } catch (Exception e) {
                    ServicoLucasTV.error("Erro ao consultar informações do KODI", e);
                    ServicoLucasTV.info("Preparando para novas para consultar informações.");
                    delay(5000);
                }
            }
            delay(1000);
        }
    }

    private void delay(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            ServicoLucasTV.LOG.error(ex.getMessage(), ex);
        }
    }

}
