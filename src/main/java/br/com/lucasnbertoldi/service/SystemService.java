package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class SystemService {

    private static ModeButton mode = ModeButton.KEYBOARD_MODE;
    private static Robot robot;

    private static int timesPressed = 1;
    private static ButtonEnum lastMouseButtonPressed;
    private static final int MIN_MOUSE_DIFERENCE = 5;
    private static final int MIN_MOUSE_WHEEL_DIFERENCE = 1;
    private static long lastTimeMousePressed = 0;

    private static long lastTimeOpenKodi = 0;

    public static void readSystemCommand(ButtonDTO buttonSelected, KodiService kodiService) {
        switch (buttonSelected.getButtonEnum()) {
            case OPEN_KODI: {
                if (kodiService.kodiIsOpen) {
                    String message = "Ué, o KODI já tá aberto o_o";
                    kodiService.sendAMessage(message);
                    ServicoLucasTV.warning(message);
                } else {
                    if (ServicoLucasTV.SISTEMA.equals("Linux")) {
                        long diferenceTime = System.currentTimeMillis() - lastTimeOpenKodi;
                        if (diferenceTime >= ConfigurationService.getConfiguration().delayOpenKodi) {
                            try {
                                Process pro = Runtime.getRuntime().exec("kodi");
                                ServicoLucasTV.info("Abrindo o KODI...");
                                lastTimeOpenKodi = System.currentTimeMillis();
                            } catch (IOException ex) {
                                ServicoLucasTV.error("Foi mal, mas aconteceu um erro ao tentar abrir o KODI :/", ex);
                            }
                        } else {
                            ServicoLucasTV.info("Já existe uma chamada para abrir o kodi.");
                        }

                    } else {
                        ServicoLucasTV.warning("Foi mal, mas a funcionalidade de abrir o KODI não funciona para o sistema operacional " + ServicoLucasTV.SISTEMA + " :/");
                    }
                }
            }
            case UP:
            case DOWN:
            case LEFT:
            case RIGHT:
            case OK:
            case BACK: {
                executeRobot(buttonSelected.getButtonEnum());
                break;
            }
            case SWITCH_MODE: {
                if (null == mode) {
                    mode = ModeButton.MOUSE_WHEEL_MODE;
                } else {
                    switch (mode) {
                        case KEYBOARD_MODE:
                            mode = ModeButton.MOUSE_MODE;
                            break;
                        case MOUSE_MODE:
                            mode = ModeButton.MOUSE_WHEEL_MODE;
                            break;
                        default:
                            mode = ModeButton.KEYBOARD_MODE;
                            break;
                    }
                }
                SystemTrayUtils.showMessage("Mensagem", "Modo do controle alterado para " + mode.description + ".", "info");
                showLOGController(ButtonEnum.SWITCH_MODE.getDescription() + " - " + mode.description);
                break;
            }

        }
    }

    private synchronized static void executeRobot(ButtonEnum button) {
        lastMouseButtonPressed = button;
        try {
            robot = new Robot();
            switch (button) {
                case UP: {
                    switch (mode) {
                        case MOUSE_MODE:
                            int positionY = MouseInfo.getPointerInfo().getLocation().y;
                            int positionX = MouseInfo.getPointerInfo().getLocation().x;
                            robot.mouseMove(positionX, positionY - getMouseDiference(button, MIN_MOUSE_DIFERENCE));
                            break;
                        case MOUSE_WHEEL_MODE:
                            robot.mouseWheel(-getMouseDiference(button, MIN_MOUSE_WHEEL_DIFERENCE));
                            break;
                        default:
                            robot.keyPress(KeyEvent.VK_UP);
                            robot.keyRelease(KeyEvent.VK_UP);
                            break;
                    }
                    break;
                }
                case DOWN: {
                    switch (mode) {
                        case MOUSE_MODE:
                            int positionY = MouseInfo.getPointerInfo().getLocation().y;
                            int positionX = MouseInfo.getPointerInfo().getLocation().x;
                            robot.mouseMove(positionX, positionY + getMouseDiference(button, MIN_MOUSE_DIFERENCE));
                            break;
                        case MOUSE_WHEEL_MODE:
                            robot.mouseWheel(getMouseDiference(button, MIN_MOUSE_WHEEL_DIFERENCE));
                            break;
                        default:
                            robot.keyPress(KeyEvent.VK_DOWN);
                            robot.keyRelease(KeyEvent.VK_DOWN);
                            break;
                    }
                    break;
                }
                case LEFT: {
                    if (mode.equals(ModeButton.MOUSE_MODE)) {
                        int positionY = MouseInfo.getPointerInfo().getLocation().y;
                        int positionX = MouseInfo.getPointerInfo().getLocation().x;
                        robot.mouseMove(positionX - getMouseDiference(button, MIN_MOUSE_DIFERENCE), positionY);
                    } else {
                        robot.keyPress(KeyEvent.VK_LEFT);
                        robot.keyRelease(KeyEvent.VK_LEFT);
                    }
                    break;
                }
                case RIGHT: {
                    if (mode.equals(ModeButton.MOUSE_MODE)) {
                        int positionY = MouseInfo.getPointerInfo().getLocation().y;
                        int positionX = MouseInfo.getPointerInfo().getLocation().x;
                        robot.mouseMove(positionX + getMouseDiference(button, MIN_MOUSE_DIFERENCE), positionY);
                    } else {
                        robot.keyPress(KeyEvent.VK_RIGHT);
                        robot.keyRelease(KeyEvent.VK_RIGHT);
                    }
                    break;
                }
                case OK: {
                    switch (mode) {
                        case MOUSE_MODE:
                            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                            break;
                        case MOUSE_WHEEL_MODE:
                            robot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                            robot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                            break;
                        default:
                            robot.keyPress(KeyEvent.VK_ENTER);
                            robot.keyRelease(KeyEvent.VK_ENTER);
                            break;
                    }
                    break;
                }
                case BACK: {
                    if (mode.equals(ModeButton.KEYBOARD_MODE)) {
                        robot.keyPress(KeyEvent.VK_ESCAPE);
                        robot.keyRelease(KeyEvent.VK_ESCAPE);
                    }
                    break;
                }
                case SETTINGS: {
                    if (mode.equals(ModeButton.MOUSE_MODE)) {
                        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    }
                    break;
                }
            }
            robot.delay(5);
            showLOGController(button.getDescription());
        } catch (AWTException ex) {
            ServicoLucasTV.error("Erro ao executar função Robot do Java", ex);
            SystemTrayUtils.showMessage("Mensagem", "Erro ao Iniciar Robot do Java", "error");
        }
    }

    private static synchronized int getMouseDiference(ButtonEnum buttonPressed, int minimumDifference) {
        if (buttonPressed.equals(lastMouseButtonPressed)) {
            long diferenceTime = System.currentTimeMillis() - lastTimeMousePressed;
            if (diferenceTime <= ConfigurationService.getConfiguration().delayMouse) {
                timesPressed++;
            } else {
                timesPressed = 1;
            }
        } else {
            timesPressed = 1;
        }
        lastMouseButtonPressed = buttonPressed;
        lastTimeMousePressed = System.currentTimeMillis();
        return minimumDifference * timesPressed;
    }

    public static void showLOGController(String type) {
        if (ConfigurationService.getConfiguration().showControllerLOG) {
            ServicoLucasTV.info("Requisição do tipo " + type + " realizada com sucesso.");
        }
    }

    private enum ModeButton {
        KEYBOARD_MODE("Teclado"), MOUSE_MODE("Mouse"), MOUSE_WHEEL_MODE("Rodinha Mouse");

        private ModeButton(String description) {
            this.description = description;
        }

        public final String description;
    }

}
