
package br.com.lucasnbertoldi.service.http;

import java.util.List;
import java.util.Map;

public class RespostaHTTP {
    
    private final int status;
    private final String response;
    private final Map<String,List<String>> responseHeaders;
    private final byte[] arquivo;

    public RespostaHTTP(int status, String response, Map<String, List<String>> responseHeaders) {
        this.status = status;
        this.response = response;
        this.responseHeaders = responseHeaders;
        this.arquivo = null;
    }

    public RespostaHTTP(int status, Map<String, List<String>> responseHeaders, byte[] arquivo) {
        this.status = status;
        this.responseHeaders = responseHeaders;
        this.arquivo = arquivo;
        this.response = null;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public int getStatus() {
        return status;
    }

    public String getResponse() {
        return response;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public String toString() {
        return "RespostaHTTP{" + "status=" + status + ", response=" + response + ", responseHeaders=" + responseHeaders + '}';
    }
}
