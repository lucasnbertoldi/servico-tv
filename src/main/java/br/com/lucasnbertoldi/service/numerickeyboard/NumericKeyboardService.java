package br.com.lucasnbertoldi.service.numerickeyboard;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class NumericKeyboardService {

    private static ButtonEnum lastButtonPressed;
    private static int timesPressed = 1;
    private static long lastTimeKeyPressed = 0;
    private static Robot robot;

    public synchronized static void readKeyBoard(ButtonEnum button, KeyboardTypeEnum type) {
        if (CharacterEnum.isCharacter(button)) {
            try {
                if (button.equals(lastButtonPressed)) {
                    long diferenceTime = System.currentTimeMillis() - lastTimeKeyPressed;
                    if (diferenceTime <= ConfigurationService.getConfiguration().delayNumber) {
                        timesPressed++;
                    } else {
                        timesPressed = 1;
                    }
                } else {
                    timesPressed = 1;
                }
                lastButtonPressed = button;
                lastTimeKeyPressed = System.currentTimeMillis();

                CharacterEnum character = CharacterEnum.valueOf(button, timesPressed, type);
                if (character != null) {
                    robot = new Robot();
                    if (timesPressed >= 2 && CharacterEnum.hasTimes(character)) {
                        robot.keyPress(KeyEvent.VK_BACK_SPACE);
                        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                        robot.delay(5);
                    }
                    robot.keyPress(character.keyEvent);
                    robot.keyRelease(character.keyEvent);
                    robot.delay(5);
                }
            } catch (AWTException ex) {
                ServicoLucasTV.error("Erro ao executar função Robot do Java", ex);
                SystemTrayUtils.showMessage("Mensagem", "Erro ao Iniciar Robot do Java", "error");
            }
        }
    }

}
