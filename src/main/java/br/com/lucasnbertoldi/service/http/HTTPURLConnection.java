package br.com.lucasnbertoldi.service.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.io.IOUtils;

public class HTTPURLConnection {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HTTPURLConnection.class);

    private Map parametros;
    private String url;
    private String tipo;
    private boolean postGetChamado;
    private Map headers;
    private String body;
    private int conectTimeout;
    private int readTimeout;
    private boolean doOutput;

    private void verificarConfiguracao() {
        if (!this.postGetChamado) {
            throw new IllegalStateException("O método get ou post deve ser chamado antes de qualquer configuração.");
        }
    }

    private void configuracoesIniciais() {
        this.postGetChamado = true;
        this.parametros = new HashMap();
        this.headers = new HashMap();
        this.body = "";
        this.conectTimeout = 15000;
        this.readTimeout = 10000;
        this.doOutput = false;
    }

    public HTTPURLConnection post(String url) {
        configuracoesIniciais();
        this.url = url;
        this.tipo = "POST";
        return this;
    }

    public HTTPURLConnection get(String url) {
        configuracoesIniciais();
        this.url = url;
        this.tipo = "GET";
        return this;
    }

    public HTTPURLConnection addParam(String nome, String valor) {
        verificarConfiguracao();
        parametros.put(nome, valor);
        return this;
    }

    public HTTPURLConnection addParam(String nome, Integer valor) {
        verificarConfiguracao();
        parametros.put(nome, valor);
        return this;
    }

    public HTTPURLConnection addHeader(String nome, String valor) {
        verificarConfiguracao();
        headers.put(nome, valor);
        return this;
    }

    public HTTPURLConnection addParam(String nome, Float valor) {
        verificarConfiguracao();
        parametros.put(nome, valor);
        return this;
    }

    public HTTPURLConnection setBody(String body) {
        verificarConfiguracao();
        this.body = body;
        return this;
    }

    public HTTPURLConnection setDoOutput(boolean doOutput) {
        verificarConfiguracao();
        this.doOutput = doOutput;
        return this;
    }

    public RespostaHTTP startRequest() {
        try {
            verificarConfiguracao();
            this.postGetChamado = false;
            return request();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    public RespostaHTTP startRequestFile() {
        try {
            verificarConfiguracao();
            this.postGetChamado = false;
            return requestFile();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex);
        } catch (UnsupportedEncodingException ex) {
            throw new UnsupportedOperationException(ex);
        }
    }

    private RespostaHTTP request() throws MalformedURLException, UnsupportedEncodingException {

        URL URL;
        URL = criarUrl(this.url, this.parametros);

        String jsonResponse = "";
        if (URL == null) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        int status = 0;
        Map<String, List<String>> responseHeaders = new HashMap();

        try {

            urlConnection = (HttpURLConnection) URL.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod(this.tipo);
            urlConnection.setDoOutput(this.doOutput);

            // Configurações da Requisição
            Iterator entries = this.headers.entrySet().iterator();
            while (entries.hasNext()) {
                Entry thisEntry = (Entry) entries.next();
                String key = (String) thisEntry.getKey();
                String value = (String) thisEntry.getValue();
                urlConnection.setRequestProperty(key, value);
            }
            urlConnection.connect();

            // Enviando informações do body
            if (!this.body.equals("")) {
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                wr.write(this.body);
                wr.flush();
            }

            // Setando heads
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            map.entrySet().forEach((entry) -> {
                responseHeaders.put(entry.getKey(), entry.getValue());
            });

            // Resposta
            if (urlConnection.getResponseCode() == 200 || urlConnection.getResponseCode() == 201 || urlConnection.getResponseCode() == 202) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = lerInputStream(inputStream);
            } else {
                inputStream = urlConnection.getErrorStream();
                jsonResponse = lerInputStream(inputStream);

            }
            status = urlConnection.getResponseCode();
        } catch (IOException ex) {
            throw new RuntimeException("Erro com a conexão com o servidor!", ex);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Erro com a conexão com o servidor!", ex);
                }
            }
        }
        return new RespostaHTTP(status, jsonResponse, responseHeaders);
    }

    private RespostaHTTP requestFile() throws MalformedURLException, UnsupportedEncodingException {
        byte[] arquivo = null;
        URL URL = null;
        int status = 0;
        String jsonResponse = null;
        Map<String, List<String>> responseHeaders = new HashMap();
        URL = criarUrl(this.url, parametros);
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) URL.openConnection();
            urlConnection.setReadTimeout(this.readTimeout /* milliseconds */);
            urlConnection.setConnectTimeout(this.conectTimeout /* milliseconds */);
            urlConnection.setDoOutput(this.doOutput);

            // Configurações da Requisição
            Iterator entries = this.headers.entrySet().iterator();
            while (entries.hasNext()) {
                Entry thisEntry = (Entry) entries.next();
                String key = (String) thisEntry.getKey();
                String value = (String) thisEntry.getValue();
                urlConnection.setRequestProperty(key, value);
            }

            urlConnection.setRequestMethod(this.tipo);
            urlConnection.connect();

            // Enviando informações do body
            if (!this.body.equals("")) {
                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8");
                wr.write(this.body);
                wr.flush();
            }

            status = urlConnection.getResponseCode();

            if (status == 200 || urlConnection.getResponseCode() == 201 || urlConnection.getResponseCode() == 202) {
                inputStream = urlConnection.getInputStream();
                arquivo = IOUtils.toByteArray(inputStream);
            } else {
                jsonResponse = lerInputStream(inputStream);
            }
            Map<String, List<String>> map = urlConnection.getHeaderFields();
            map.entrySet().forEach((entry) -> {
                responseHeaders.put(entry.getKey(), entry.getValue());
            });

        } catch (IOException ex) {
            throw new RuntimeException("Erro com a conexão com o servidor!", ex);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    throw new RuntimeException("Erro com a conexão com o servidor!", ex);
                }
            }
        }
        if (status == 200) {
            return new RespostaHTTP(status, responseHeaders, arquivo);
        } else {
            return new RespostaHTTP(status, jsonResponse, responseHeaders);
        }
    }

    private URL criarUrl(String stringUrl, Map query) throws MalformedURLException, UnsupportedEncodingException {

        Iterator entries = query.entrySet().iterator();

        StringBuilder s = new StringBuilder(stringUrl);
        s.append("?");
        while (entries.hasNext()) {
            Entry thisEntry = (Entry) entries.next();
            String key = (String) thisEntry.getKey();
            String value = (String) thisEntry.getValue();
            s.append(key).append("=").append(URLEncoder.encode(value, "UTF-8")).append("&");
        }
        s.delete(s.length() - 1, s.length());

        String URL = s.toString();
        return new URL(URL);
    }

    private String lerInputStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            } catch (IOException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

        return output.toString();
    }

    public String getBasicAuth(String usuario, String senha) {
        String encoded = Base64.getEncoder().encodeToString((usuario + ":" + senha).getBytes(StandardCharsets.UTF_8));
        return "Basic " + encoded;
    }

}
