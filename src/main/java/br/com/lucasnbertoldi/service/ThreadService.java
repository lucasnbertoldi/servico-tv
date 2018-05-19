/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.arduino.SerialService;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import java.net.ConnectException;

/**
 *
 * @author lukin
 */
public class ThreadService extends Thread {

    private boolean primeiraTentativaKodi = true;

    @Override
    public void run() {
        KodiService kodiService = new KodiService();

        SerialService serialService = new SerialService();
        serialService.initialize(kodiService);

        while (true) {
            if (serialService.comPort == null || !serialService.comPort.isOpen()) {
                ServicoLucasTV.error("Não foi possível se conectar com a porta serial.");
                ServicoLucasTV.info("Preparando para novas tentativas de configuração da porta serial.");
                delay(5000);
                serialService.initialize(kodiService);
            } else {
                try {
                    kodiService.updateProperties();
                    if (!primeiraTentativaKodi) {
                        ServicoLucasTV.info("O KODI está novamente aberto e funcionando o/");
                    }
                    kodiService.kodiIsOpen = true;
                    primeiraTentativaKodi = true;
                } catch (RuntimeException e) {
                    kodiService.kodiIsOpen = false;
                    if (e.getCause() != null) {
                        if (e.getCause() instanceof ConnectException) {
                            if (primeiraTentativaKodi) {
                                ServicoLucasTV.warning("Parece que o KODI está ou foi fechado :/");
                                primeiraTentativaKodi = false;
                            }
                        } else {
                            ServicoLucasTV.error("Erro ao consultar informações do KODI :/", e);
                            ServicoLucasTV.info("Preparando para novas para consultar informações.");
                        }
                    } else {
                        ServicoLucasTV.error("Erro ao consultar informações do KODI :/", e);
                        ServicoLucasTV.info("Preparando para novas para consultar informações.");
                    }
                    delay(5000);
                }
            }
            delay(500);
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
