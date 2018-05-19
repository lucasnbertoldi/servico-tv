package br.com.lucasnbertoldi.service.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ButtonDTO {

    private final ButtonEnum buttonEnum;
    private List<String> codeList;

    public ButtonDTO(ButtonEnum buttonEnum) {
        this.buttonEnum = buttonEnum;
    }

    public ButtonEnum getButtonEnum() {
        return buttonEnum;
    }

    public List<String> getCodeList() {
        return codeList;
    }

    public String getTextCodeList() {
        return convertArrayToString(codeList);
    }

    public void setCodeList(String codeList) {
        this.codeList = convertStringToArray(codeList);
    }

    private List<String> convertStringToArray(String text) {
        if (text == null) {
            return new ArrayList<>();
        } else {
            List<String> newList = new ArrayList<>();
            newList.addAll(Arrays.asList(text.split(",")));
            return newList;
        }
    }

    private String convertArrayToString(List<String> array) {
        StringBuilder string = new StringBuilder("");
        for (String s : array) {
            string.append(s).append(",");
        }
        try {
            string = new StringBuilder(string.substring(0, string.length() - 1));
        } catch (Exception e) {
        }
        return string.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ButtonDTO) {
            return this.buttonEnum.getId() == ((ButtonDTO) obj).getButtonEnum().getId();
        } else if (obj instanceof ButtonEnum) {
            return this.buttonEnum.getId() == ((ButtonEnum) obj).getId();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.buttonEnum);
        hash = 29 * hash + Objects.hashCode(this.codeList);
        return hash;
    }

}
