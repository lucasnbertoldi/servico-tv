package br.com.lucasnbertoldi.service.kodi;

import br.com.lucasnbertoldi.ServicoLucasTV;
import br.com.lucasnbertoldi.service.SystemService;
import br.com.lucasnbertoldi.service.configuration.ButtonDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationDTO;
import br.com.lucasnbertoldi.service.configuration.ConfigurationService;
import br.com.lucasnbertoldi.service.http.HTTPURLConnection;
import br.com.lucasnbertoldi.service.http.RespostaHTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KodiService {

    public boolean kodiIsOpen = false;

    public boolean read(ButtonDTO buttonDTO) {
        boolean read = true;
        switch (buttonDTO.getButtonEnum()) {
            case DOWN: {
                makeRequest(SHOW_MESSAGE, new RequestKodiDTO(DOWN, "Botão Baixo", null));
                break;
            }
            case UP: {
                makeRequest(SHOW_MESSAGE, new RequestKodiDTO(UP, "Botão Cima", null));
                break;
            }
            case LEFT: {
                switch (kodiDTO.windowID) {
                    case FULLSCREEN_VIDEO_WINDOW: {
                        int speed = kodiDTO.speed;
                        if (speed == 0) {
                            speed = -1;
                        }
                        if (speed > 0) {
                            speed = speed / 2;
                        } else {
                            speed = speed * 2;
                        }
                        if (speed >= MIN_SPEED) {
                            makeRequest(SHOW_MESSAGE, new RequestKodiDTO(SET_SPEED, "Diminuir Velocidade Reprodução", "[" + kodiDTO.playerID + "," + speed + "]"));
                        }
                        break;
                    }
                    case AUDIO_VIEW: {
                         makeRequest(SHOW_MESSAGE, new RequestKodiDTO("Player.GoTo", "Música Anterior", "[" + kodiDTO.playerID + ",'previous']"));
                        break;
                    }
                    default: {
                        makeRequest(SHOW_MESSAGE, new RequestKodiDTO(LEFT, "Botão Esquerda", null));
                    }
                }
                break;
            }
            case RIGHT: {
                switch (kodiDTO.windowID) {
                    case FULLSCREEN_VIDEO_WINDOW: {
                        int speed = kodiDTO.speed;
                        if (speed == 0) {
                            speed = 1;
                        }
                        if (speed < 0) {
                            speed = speed / 2;
                        } else {
                            speed = speed * 2;
                        }
                        if (speed <= MAX_SPEED) {
                            makeRequest(SHOW_MESSAGE, new RequestKodiDTO(SET_SPEED, "Diminuir Velocidade Reprodução", "[" + kodiDTO.playerID + "," + speed + "]"));
                        }
                        break;
                    }
                                        case AUDIO_VIEW: {
                         makeRequest(SHOW_MESSAGE, new RequestKodiDTO("Player.GoTo", "Música Anterior", "[" + kodiDTO.playerID + ",'next']"));
                        break;
                    }
                    default: {
                        makeRequest(SHOW_MESSAGE, new RequestKodiDTO(RIGHT, "Botão Direita", null));
                    }
                }
                break;
            }
            case BACK: {
                makeRequest(SHOW_MESSAGE, new RequestKodiDTO(BACK, "Botão Voltar", null));
                break;
            }
            case VOLUME_UP: {
                if (kodiDTO.volume != MAX_VOLUME) {
                    int newVolume = kodiDTO.volume + RANGE_VOLUME;
                    if (newVolume > MAX_VOLUME) {
                        newVolume = MAX_VOLUME;
                    }
                    String result = makeRequest(SHOW_MESSAGE, new RequestKodiDTO(VOLUME, "Botão Volume Mais", new JSONArray().put(0, newVolume).toString()))[0].response;
                    if (result != null) {
                        kodiDTO.volume = Integer.parseInt(result);
                    }
                }
                break;
            }
            case VOLUME_DOWN: {
                if (kodiDTO.volume != MIN_VOLUME) {
                    int newVolume = kodiDTO.volume - RANGE_VOLUME;
                    if (newVolume < MIN_VOLUME) {
                        newVolume = MIN_VOLUME;
                    }
                    String result = makeRequest(SHOW_MESSAGE, new RequestKodiDTO(VOLUME, "Botão Volume Menos", new JSONArray().put(0, newVolume).toString()))[0].response;
                    if (result != null) {
                        kodiDTO.volume = Integer.parseInt(result);
                    }
                }
                break;
            }
            case OK: {
                switch (kodiDTO.windowID) {
                    case FULLSCREEN_VIDEO_WINDOW:
                    case AUDIO_VIEW: {
                        makeRequest(SHOW_MESSAGE, new RequestKodiDTO(PLAY_PAUSE, "Pausar/Continuar", "[" + kodiDTO.playerID + "]"));
                        break;
                    }
                    default: {
                        makeRequest(SHOW_MESSAGE, new RequestKodiDTO(OK, "Botão Selecionar", null));
                    }
                }
                break;
            }
            case PLAY_PAUSE: {
                break;
            }
            case SETTINGS: {

                if (kodiDTO.windowID == FULLSCREEN_VIDEO_WINDOW || kodiDTO.windowID == AUDIO_VIEW) {
                    makeRequest(SHOW_MESSAGE, new RequestKodiDTO("Input.ShowOSD", "Exibir opções do Player", null));
                } else {
                    makeRequest(SHOW_MESSAGE, new RequestKodiDTO(CONTEXT_MENU, "Botão de Opções", null));
                }
                break;
            }
            case SUBTITLE_SCREEN: {
                if (kodiDTO.playerID != null && kodiDTO.playerType.equals(PLAYER_VIDEO)) {
                    makeRequest(SHOW_MESSAGE, new RequestKodiDTO(ACTIVATE_WINDOW, "Botão Tela Legenda", "{'window':'subtitlesearch'}"));
                }
                break;
            }
            case STOP: {
                if (kodiDTO.playerID != null) {
                    makeRequest(SHOW_MESSAGE, new RequestKodiDTO(STOP, "Parar", "[" + kodiDTO.playerID + "]"));
                }
                break;
            }
            case RESTART: {
                makeRequest(SHOW_MESSAGE, new RequestKodiDTO("System.Reboot", "Reiniciar Sistema", null));
                break;
            }
            default: {
                read = false;
            }
        }
        return read;
    }

    public void sendAMessage(String message) {
        makeRequest(false, new RequestKodiDTO(NOTIFY, "Notificação", "{\"title\":\"" + ServicoLucasTV.NOME_SOFTWARE + "\", \"message\":\"" + message + "\"}"));
    }

    private RequestKodiDTO[] makeRequest(boolean showMessage, RequestKodiDTO... requests) {
        HTTPURLConnection http = new HTTPURLConnection();
        ConfigurationDTO config = ConfigurationService.getConfiguration();
        JSONArray bodyArray = new JSONArray();
        for (RequestKodiDTO request : requests) {
            JSONObject body = new JSONObject();
            body.put("jsonrpc", "2.0");
            body.put("method", request.type);
            if (request.params == null) {
                body.put("params", new JSONArray());
            } else {
                try {
                    body.put("params", new JSONObject(request.params));
                } catch (JSONException e) {
                }
                try {
                    body.put("params", new JSONArray(request.params));
                } catch (JSONException e) {
                }
            }
            if (id == 1000000000) {
                id = 0;
            }
            request.id = id++;

            body.put("id", request.id);
            bodyArray.put(body);
        }
        RespostaHTTP resposta = http.post(config.urlKODI)
                .addHeader("Authorization", http.getBasicAuth(config.user, config.password))
                .setDoOutput(true)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                .setBody(bodyArray.toString())
                .startRequest();
        return checkMethodResponse(resposta, showMessage, requests);
    }
    private KodiDTO kodiDTO = new KodiDTO();

    public void updateProperties(boolean first) {
        if (first) {
            sendAMessage("O Serviço está executando.");
            SystemService.changeMode(this, SystemService.ModeButton.KODI_MODE);
        }

        RequestKodiDTO requestPropery = new RequestKodiDTO(GET_PROPERTIES, "Recolher Propriedades", new JSONArray().put(0, new JSONArray().put(0, "volume").put(1, "muted")).toString());
        RequestKodiDTO requestActivePlayer = new RequestKodiDTO(GET_ACTIVE_PLAYERS, "Players Ativos", null);
        RequestKodiDTO requestGuiProperties = new RequestKodiDTO(GET_WINDOW_PROPERTIES, "Propriedades da Janela", "[[\"currentwindow\"]]");

        if (kodiDTO.playerID != null) {
            RequestKodiDTO requestPlayerProperties = new RequestKodiDTO(GET_PLAYER_PROPERTIES, "Propriedades do player", "[" + kodiDTO.playerID + ",[\"speed\"]]");
            makeRequest(false, requestActivePlayer, requestPropery, requestGuiProperties, requestPlayerProperties);
            if (!requestPlayerProperties.response.equals("")) {
                JSONObject results = new JSONObject(requestPlayerProperties.response);
                kodiDTO.speed = results.getInt("speed");
            }
        } else {
            makeRequest(false, requestActivePlayer, requestPropery, requestGuiProperties);
        }

        if (!requestActivePlayer.response.equals("")) {
            try {
                JSONObject results = new JSONArray(requestActivePlayer.response).getJSONObject(0);
                kodiDTO.playerID = results.getInt("playerid");
                kodiDTO.playerType = results.getString("type");
            } catch (JSONException e) {
                kodiDTO.playerID = null;
                kodiDTO.playerType = "";
            }
        }
        if (!requestPropery.response.equals("")) {
            JSONObject results = new JSONObject(requestPropery.response);
            kodiDTO.muted = results.getBoolean("muted");
            kodiDTO.volume = results.getInt("volume");
        }

        if (!requestGuiProperties.equals("")) {
            JSONObject results = new JSONObject(requestGuiProperties.response);
            kodiDTO.windowID = results.getJSONObject("currentwindow").getInt("id");
        }
    }

    private RequestKodiDTO[] checkMethodResponse(RespostaHTTP resposta, boolean showMessage, RequestKodiDTO... requests) {
        for (RequestKodiDTO request : requests) {
            String result = "";
            boolean erro = true;
            if (resposta.getStatus() == 200) {
                try {
                    JSONArray arrayResponse = new JSONArray(resposta.getResponse());
                    for (int i = 0; i < arrayResponse.length(); i++) {
                        int id = arrayResponse.getJSONObject(i).getInt("id");
                        if (id == request.id) {
                            result = arrayResponse.getJSONObject(i).get("result").toString();
                            request.response = result;
                            break;
                        }
                    }
                    erro = false;
                    if (showMessage) {
                        SystemService.showLOGController(request.description);
                    }
                } catch (JSONException e) {
                    erro = true;
                }
            }
            if (erro) {
                ServicoLucasTV.warning("Requisição do tipo " + request.description + " não foi bem sucedida. Resposta: " + resposta.getResponse() + " - Código: " + resposta.getStatus());
            }
        }
        return requests;
    }

    private final String UP = "Input.Up";
    private final String DOWN = "Input.Down";
    private final String LEFT = "Input.Left";
    private final String RIGHT = "Input.Right";
    private final String OK = "Input.Select";
    private final String BACK = "Input.Back";
    private final String CONTEXT_MENU = "Input.ContextMenu";

    private final String ACTIVATE_WINDOW = "GUI.ActivateWindow";
    private final String GET_PROPERTIES = "Application.GetProperties";

    private final String NOTIFY = "GUI.ShowNotification";

    private final String GET_WINDOW_PROPERTIES = "GUI.GetProperties";
    private final int FULLSCREEN_VIDEO_WINDOW = 12005;
    private final int OSD_FULLSCREN_VIDEO_WINDOW = 12901;
    private final int AUDIO_VIEW = 12006;
    private final int AUDIO_OSD = 10120;

    private final String PLAY_PAUSE = "Player.PlayPause";
    private final String STOP = "Player.Stop";
    private final String GET_PLAYER_PROPERTIES = "Player.GetProperties";

    private final String SET_SPEED = "Player.SetSpeed";
    private final int MAX_SPEED = 32;
    private final int MIN_SPEED = -32;

    private final String VOLUME = "Application.SetVolume";
    private final int RANGE_VOLUME = 5;
    private final int MAX_VOLUME = 100;
    private final int MIN_VOLUME = 0;

    private final String GET_ACTIVE_PLAYERS = "Player.GetActivePlayers";
    private final String PLAYER_AUDIO = "audio";
    private final String PLAYER_VIDEO = "video";

    private final boolean SHOW_MESSAGE = true;

    private static int id = 0;
}
