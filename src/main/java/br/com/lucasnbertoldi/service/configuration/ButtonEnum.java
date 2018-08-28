package br.com.lucasnbertoldi.service.configuration;

public enum ButtonEnum {
    UP(1, "Cima", "button.up"),
    DOWN(2, "Baixo", "button.down"),
    LEFT(3, "Esquerda", "button.left"),
    RIGHT(4, "Direita", "button.right"),
    OK(5, "Confirmar", "button.ok"),
    BACK(6, "Voltar", "button.back"),
    VOLUME_UP(7, "Aumentar Volume", "button.volumeUp"),
    VOLUME_DOWN(8, "Diminuir Volume", "button.volumeDown"),
    SUBTITLE_SCREEN(9, "Tela de Legenda", "button.subtitleScreen"),
    PLAY_PAUSE(10, "Play/Pause", "button.playPause"),
    SETTINGS(11, "Configurações/Mais Opções", "button.moreOptions"),
    STOP(12, "Stop", "button.stop"),
    DISABLE(13, "Desabilitar Controle Remoto", "button.disable"),
    ONE(14, "Número 1", "button.one"),
    TWO(15, "Número 2", "button.two"),
    THREE(16, "Número 3", "button.three"),
    FOUR(17, "Número 4", "button.four"),
    FIVE(18, "Número 5", "button.five"),
    SIX(19, "Número 6", "button.six"),
    SEVEN(20, "Número 7", "button.seven"),
    EIGHT(21, "Número 8", "button.eight"),
    NINE(22, "Número 9", "button.nine"),
    ZERO(23, "Número 0", "button.zero"),
    RESTART(24, "Reiniciar Sistema", "button.restart"),
    OPEN_KODI(25, "Abrir Kodi", "button.openKodi"),
    SWITCH_MODE(26, "Alterar Modo", "button.switchMode");

    private ButtonEnum(int id, String description, String propertyName) {
        this.id = id;
        this.description = description;
        this.propertyName = propertyName;
    }

    private final int id;
    private final String description;
    private final String propertyName;

    public boolean equals(ButtonEnum buttonEnum) {
        return this.id == buttonEnum.id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ButtonEnum{" + "id=" + id + ", description=" + description + ", propertyName=" + propertyName + '}';
    }

}
