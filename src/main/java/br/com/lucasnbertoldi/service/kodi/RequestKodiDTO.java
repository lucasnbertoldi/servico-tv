package br.com.lucasnbertoldi.service.kodi;

import java.util.Objects;

public class RequestKodiDTO {

    protected String type;
    protected String description;
    protected String params;
    protected int id;
    protected String response;

    public RequestKodiDTO(String type, String description, String params) {
        this.type = type;
        this.description = description;
        this.params = params;
        this.response = "";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.params);
        hash = 79 * hash + this.id;
        hash = 79 * hash + Objects.hashCode(this.response);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RequestKodiDTO other = (RequestKodiDTO) obj;
        return this.id == other.id;
    }

}
