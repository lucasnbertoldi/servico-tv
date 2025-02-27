/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.arduino.SerialService;
import br.com.lucasnbertoldi.service.SystemService.ModeButton;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 *
 * @author lukin
 */
public class ThreadService extends Thread {

    private boolean primeiraTentativaKodi = true;
    private boolean mostrarPrimeiraMensagem = true;

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
                    kodiService.updateProperties(mostrarPrimeiraMensagem);
                    if (!primeiraTentativaKodi) {
                        ServicoLucasTV.info("O KODI está novamente aberto e funcionando o/");
                        SystemService.changeMode(kodiService, ModeButton.KODI_MODE);
                    }
                    kodiService.kodiIsOpen = true;
                    primeiraTentativaKodi = true;
                    mostrarPrimeiraMensagem = false;
                } catch (RuntimeException e) {
                    kodiService.kodiIsOpen = false;
                    mostrarPrimeiraMensagem = true;
                    if (e.getCause() != null) {
                        if (e.getCause() instanceof ConnectException || e.getCause() instanceof SocketTimeoutException) {
                            if (primeiraTentativaKodi) {
                                ServicoLucasTV.warning("Parece que o KODI está ou foi fechado :/");
                                primeiraTentativaKodi = false;
                                SystemService.changeMode(kodiService, ModeButton.KEYBOARD_MODE);
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
