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
    ONE(1, '1', ButtonEnum.ONE, KeyEvent.VK_1, KeyboardTypeEnum.NUMBER, 1),
    TWO(1, '2', ButtonEnum.TWO, KeyEvent.VK_2, KeyboardTypeEnum.NUMBER, 1),
    THREE(1, '3', ButtonEnum.THREE, KeyEvent.VK_3, KeyboardTypeEnum.NUMBER, 1),
    FOUR(1, '4', ButtonEnum.FOUR, KeyEvent.VK_4, KeyboardTypeEnum.NUMBER, 1),
    FIVE(1, '5', ButtonEnum.FIVE, KeyEvent.VK_5, KeyboardTypeEnum.NUMBER, 1),
    SIX(1, '6', ButtonEnum.SIX, KeyEvent.VK_6, KeyboardTypeEnum.NUMBER, 1),
    SEVEN(1, '7', ButtonEnum.SEVEN, KeyEvent.VK_7, KeyboardTypeEnum.NUMBER, 1),
    EIGHT(1, '8', ButtonEnum.EIGHT, KeyEvent.VK_8, KeyboardTypeEnum.NUMBER, 1),
    NINE(1, '9', ButtonEnum.NINE, KeyEvent.VK_9, KeyboardTypeEnum.NUMBER, 1),
    ZERO(1, '0', ButtonEnum.ZERO, KeyEvent.VK_0, KeyboardTypeEnum.NUMBER, 1),
    SPACE(1, ' ', ButtonEnum.ZERO, KeyEvent.VK_SPACE, KeyboardTypeEnum.LETTER, 1),
    DOT(1, '.', ButtonEnum.ONE, KeyEvent.VK_PERIOD, KeyboardTypeEnum.LETTER, 2), COMMA(2, ',', ButtonEnum.ONE, KeyEvent.VK_COMMA, KeyboardTypeEnum.LETTER, 2),
    A(1, 'A', ButtonEnum.TWO, KeyEvent.VK_A, KeyboardTypeEnum.LETTER, 3), B(2, 'B', ButtonEnum.TWO, KeyEvent.VK_B, KeyboardTypeEnum.LETTER, 3), C(3, 'C', ButtonEnum.TWO, KeyEvent.VK_C, KeyboardTypeEnum.LETTER, 3),
    D(1, 'D', ButtonEnum.THREE, KeyEvent.VK_D, KeyboardTypeEnum.LETTER, 3), E(2, 'E', ButtonEnum.THREE, KeyEvent.VK_E, KeyboardTypeEnum.LETTER, 3), F(3, 'F', ButtonEnum.THREE, KeyEvent.VK_F, KeyboardTypeEnum.LETTER, 3),
    G(1, 'G', ButtonEnum.FOUR, KeyEvent.VK_G, KeyboardTypeEnum.LETTER, 3), H(2, 'H', ButtonEnum.FOUR, KeyEvent.VK_H, KeyboardTypeEnum.LETTER, 3), I(3, 'I', ButtonEnum.FOUR, KeyEvent.VK_I, KeyboardTypeEnum.LETTER, 3),
    J(1, 'J', ButtonEnum.FIVE, KeyEvent.VK_J, KeyboardTypeEnum.LETTER, 3), K(2, 'K', ButtonEnum.FIVE, KeyEvent.VK_K, KeyboardTypeEnum.LETTER, 3), L(3, 'L', ButtonEnum.FIVE, KeyEvent.VK_L, KeyboardTypeEnum.LETTER, 3),
    M(1, 'M', ButtonEnum.SIX, KeyEvent.VK_M, KeyboardTypeEnum.LETTER, 3), N(2, 'N', ButtonEnum.SIX, KeyEvent.VK_N, KeyboardTypeEnum.LETTER, 3), O(3, 'O', ButtonEnum.SIX, KeyEvent.VK_O, KeyboardTypeEnum.LETTER, 3),
    P(1, 'P', ButtonEnum.SEVEN, KeyEvent.VK_P, KeyboardTypeEnum.LETTER, 4), Q(2, 'Q', ButtonEnum.SEVEN, KeyEvent.VK_Q, KeyboardTypeEnum.LETTER, 4), R(3, 'R', ButtonEnum.SEVEN, KeyEvent.VK_R, KeyboardTypeEnum.LETTER, 4), S(4, 'S', ButtonEnum.SEVEN, KeyEvent.VK_S, KeyboardTypeEnum.LETTER, 4),
    T(1, 'T', ButtonEnum.EIGHT, KeyEvent.VK_T, KeyboardTypeEnum.LETTER, 3), U(2, 'U', ButtonEnum.EIGHT, KeyEvent.VK_U, KeyboardTypeEnum.LETTER, 3), V(3, 'V', ButtonEnum.EIGHT, KeyEvent.VK_V, KeyboardTypeEnum.LETTER, 3),
    W(1, 'W', ButtonEnum.NINE, KeyEvent.VK_W, KeyboardTypeEnum.LETTER, 4), X(2, 'X', ButtonEnum.NINE, KeyEvent.VK_X, KeyboardTypeEnum.LETTER, 4), Y(3, 'Y', ButtonEnum.NINE, KeyEvent.VK_Y, KeyboardTypeEnum.LETTER, 4), Z(4, 'Z', ButtonEnum.NINE, KeyEvent.VK_Z, KeyboardTypeEnum.LETTER, 4);

    private CharacterEnum(int times, char character, ButtonEnum button, int keyEvent, KeyboardTypeEnum type, int maxTimes) {
        this.times = times;
        this.character = character;
        this.button = button;
        this.keyEvent = keyEvent;
        this.maxTimes = maxTimes;
        this.type = type;
    }

    public final int times;
    public final int maxTimes;
    public final char character;
    public final ButtonEnum button;
    public final int keyEvent;
    public final KeyboardTypeEnum type;

    public static boolean hasTimes(CharacterEnum character) {
        return character.maxTimes > 1;
    }

    public static CharacterEnum valueOf(ButtonEnum button, int times, KeyboardTypeEnum type) {
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
            if (value.times == newTimes && value.button.equals(button) && type == value.type) {
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
