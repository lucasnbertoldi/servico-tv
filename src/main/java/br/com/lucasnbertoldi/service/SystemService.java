package br.com.lucasnbertoldi.service;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.gui.SystemTrayUtils;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import br.com.lucasnbertoldi.service.kodi.KodiService;
import br.com.lucasnbertoldi.service.numerickeyboard.KeyboardTypeEnum;
import br.com.lucasnbertoldi.service.numerickeyboard.NumericKeyboardService;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemService {

    private static ModeButton mode = ModeButton.KEYBOARD_MODE;
    private static Robot robot;

    private static int timesPressed = 1;
    private static ButtonEnum lastMouseButtonPressed;
    private static final int MIN_MOUSE_DIFERENCE = 5;
    private static final int MIN_MOUSE_WHEEL_DIFERENCE = 1;
    private static long lastTimeMousePressed = 0;

    private static long lastTimeOpenKodi = 0;

    private static final int RANGE_VOLUME = 5;
    private static int volume = 0;

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
            case BACK:
            case BACKSPACE: {
                executeRobot(buttonSelected.getButtonEnum(), kodiService);
                break;
            }
            case VOLUME_DOWN: {
                changeVolume(-RANGE_VOLUME);
                showLOGController(ButtonEnum.VOLUME_DOWN.getDescription());
                break;
            }
            case VOLUME_UP: {
                changeVolume(RANGE_VOLUME);
                showLOGController(ButtonEnum.VOLUME_UP.getDescription());
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
                showMessage("Modo do controle alterado para " + mode.description + ".", kodiService);
                showLOGController(ButtonEnum.SWITCH_MODE.getDescription() + " - " + mode.description);
                break;
            }
            default: {
                NumericKeyboardService.readKeyBoard(buttonSelected.getButtonEnum(), mode == ModeButton.KEYBOARD_MODE ? KeyboardTypeEnum.LETTER : KeyboardTypeEnum.NUMBER);
            }

        }
    }

    private static void changeVolume(int range) {
        volume = volume + range;
        if (volume < 0) {
            volume = 0;
        }
        if (volume > 100) {
            volume = 100;
        }
        if (ServicoLucasTV.SISTEMA.equals("Linux")) {
            setLinuxVolume(volume);
        } else if (ServicoLucasTV.SISTEMA.contains("Windows")) {
            setWindowsVolume(volume);
        }
    }

    public static void setLinuxVolume(int volume) {
        Runtime rt = Runtime.getRuntime();
        Process pr;
        try {
            String command = "amixer -D pulse sset Master " + volume + "%";
            pr = rt.exec(command);
        } catch (IOException ex) {
            ServicoLucasTV.error("Erro ao modificar volume.", ex);
        }
    }

    public static void setWindowsVolume(int volume) {

        double endVolume = 655.35 * volume;

        Runtime rt = Runtime.getRuntime();
        Process pr;
        try {
            String nircmdFilePath = ConfigurationService.getSoftwareFolderName() + "\\nircmd\\nircmd.exe";
            pr = rt.exec(nircmdFilePath + " setsysvolume " + endVolume);
            pr = rt.exec(nircmdFilePath + " mutesysvolume 0");

        } catch (IOException ex) {
            ServicoLucasTV.error("Erro ao modificar volume.", ex);
        }

    }

    private synchronized static void executeRobot(ButtonEnum button, KodiService kodi) {
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
                case BACKSPACE: {
                    if (mode.equals(ModeButton.KEYBOARD_MODE)) {
                        robot.keyPress(KeyEvent.VK_BACK_SPACE);
                        robot.keyRelease(KeyEvent.VK_BACK_SPACE);
                    }
                    break;
                }
            }
            robot.delay(5);
            showLOGController(button.getDescription());
        } catch (AWTException ex) {
            ServicoLucasTV.error("Erro ao executar função Robot do Java", ex);
            showMessage("Erro ao Iniciar Robot do Java", kodi);
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

    private static void showMessage(String message, KodiService kodi) {
        if (kodi.kodiIsOpen) {
            kodi.sendAMessage(message);
        } else {
            SystemTrayUtils.showMessage("Mensagem", message, "info");
        }
    }
}
