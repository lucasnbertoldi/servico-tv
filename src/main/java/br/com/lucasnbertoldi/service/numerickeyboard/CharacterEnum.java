/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.lucasnbertoldi.service.numerickeyboard;

import br.com.lucasnbertoldi.service.configuration.ButtonEnum;
import java.awt.event.KeyEvent;

/**
 *
 *
 * @author Lucas
 */
public enum CharacterEnum {
    SPACE(1, ' ', ButtonEnum.ZERO, KeyEvent.VK_SPACE, 1),
    DOT(1, '.', ButtonEnum.ONE, KeyEvent.VK_PERIOD, 2), COMMA(2, ',', ButtonEnum.ONE, KeyEvent.VK_COMMA, 2),
    A(1, 'A', ButtonEnum.TWO, KeyEvent.VK_A, 3), B(2, 'B', ButtonEnum.TWO, KeyEvent.VK_B, 3), C(3, 'C', ButtonEnum.TWO, KeyEvent.VK_C, 3),
    D(1, 'D', ButtonEnum.THREE, KeyEvent.VK_D, 3), E(2, 'E', ButtonEnum.THREE, KeyEvent.VK_E, 3), F(3, 'F', ButtonEnum.THREE, KeyEvent.VK_F, 3),
    G(1, 'G', ButtonEnum.FOUR, KeyEvent.VK_G, 3), H(2, 'H', ButtonEnum.FOUR, KeyEvent.VK_H, 3), I(3, 'I', ButtonEnum.FOUR, KeyEvent.VK_I, 3),
    J(1, 'J', ButtonEnum.FIVE, KeyEvent.VK_J, 3), K(2, 'K', ButtonEnum.FIVE, KeyEvent.VK_K, 3), L(3, 'L', ButtonEnum.FIVE, KeyEvent.VK_L, 3),
    M(1, 'M', ButtonEnum.SIX, KeyEvent.VK_M, 3), N(2, 'N', ButtonEnum.SIX, KeyEvent.VK_N, 3), O(3, 'O', ButtonEnum.SIX, KeyEvent.VK_O, 3),
    P(1, 'P', ButtonEnum.SEVEN, KeyEvent.VK_P, 4), Q(2, 'Q', ButtonEnum.SEVEN, KeyEvent.VK_Q, 4), R(3, 'R', ButtonEnum.SEVEN, KeyEvent.VK_R, 4), S(4, 'S', ButtonEnum.SEVEN, KeyEvent.VK_S, 4),
    T(1, 'T', ButtonEnum.EIGHT, KeyEvent.VK_T, 3), U(2, 'U', ButtonEnum.EIGHT, KeyEvent.VK_U, 3), V(3, 'V', ButtonEnum.EIGHT, KeyEvent.VK_V, 3),
    W(1, 'W', ButtonEnum.NINE, KeyEvent.VK_W, 4), X(2, 'X', ButtonEnum.NINE, KeyEvent.VK_X, 4), Y(3, 'Y', ButtonEnum.NINE, KeyEvent.VK_Y, 4), Z(4, 'Z', ButtonEnum.NINE, KeyEvent.VK_Z, 4);

    private CharacterEnum(int times, char character, ButtonEnum button, int keyEvent, int maxTimes) {
        this.times = times;
        this.character = character;
        this.button = button;
        this.keyEvent = keyEvent;
        this.maxTimes = maxTimes;
    }

    public final int times;
    public final int maxTimes;
    public final char character;
    public final ButtonEnum button;
    public final int keyEvent;

    public static CharacterEnum valueOf(ButtonEnum button, int times) {
        for (CharacterEnum value : CharacterEnum.values()) {
            int newTimes;
            if (times <= value.maxTimes) {
                newTimes = times;
            } else {
                newTimes = times - ((times / value.maxTimes) * value.maxTimes);
                if (newTimes == 0) {
                    newTimes = value.maxTimes;
                }
            }
            if (value.times == newTimes && value.button.equals(button)) {
                return value;
            }
        }
        return null;
    }

    public static boolean isCharacter(ButtonEnum button) {
        for (CharacterEnum value : CharacterEnum.values()) {
            if (value.button.equals(button)) {
                return true;
            }
        }
        return false;
    }

}
