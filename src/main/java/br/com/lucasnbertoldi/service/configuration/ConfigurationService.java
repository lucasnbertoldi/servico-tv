package br.com.lucasnbertoldi.service.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Properties;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.IIOException;

public class ConfigurationService {

    private static final String PATH_FILE = "/servico.properties";

    private static ConfigurationDTO configuration;

    public static void setConfiguration(ConfigurationDTO config) throws IOException {

        String path = new File(".").getCanonicalPath();

        Properties properties = new Properties();

        //setando as propriedades(key) e os seus valores(value)
        properties.setProperty("config.user", config.user);
        properties.setProperty("config.password", encryptText(config.password));
        properties.setProperty("config.urlKODI", config.urlKODI);
        properties.setProperty("config.sistema", config.sistema);
        
        properties.setProperty("button.up", config.getTextUp());
        properties.setProperty("button.down", config.getTextDown());
        properties.setProperty("button.left", config.getTextLeft());
        properties.setProperty("button.right", config.getTextRight());
        properties.setProperty("button.ok", config.getTextOk());
        properties.setProperty("button.back", config.getTextBack());
        properties.setProperty("button.volumeUp", config.getTextVolumeUp());
        properties.setProperty("button.volumeDown", config.getTextVolumeDown());
        properties.setProperty("button.disable", config.getTextDisable());
        properties.setProperty("button.settings", config.getTextSettings());
        properties.setProperty("button.playPause", config.getTextPlayPause());
        properties.setProperty("button.stop", config.getTextStop());
        properties.setProperty("button.subtitleScreen", config.getTextSubtitleScreen());

        try {
            //grava os dados no arquivo
            try ( //Criamos um objeto FileOutputStream
                    FileOutputStream fos = new FileOutputStream(path + PATH_FILE)) {
                //grava os dados no arquivo
                properties.store(fos, "LUCAS TV PROPERTIES:");
                //fecha o arquivo
            }

            ConfigurationService.configuration = config;

        } catch (IOException ex) {
            throw new IIOException(ex.getMessage(), ex);
        }
    }
    
        public static ConfigurationDTO getConfiguration() {
        if (ConfigurationService.configuration != null) {

            return ConfigurationService.configuration;

        } else {
            String path;
            try {
                path = new File(".").getCanonicalPath();
            } catch (IOException ex) {
                throw new RuntimeException("Erro ao encontrar o caminho do arquivo.", ex);
            }

            ConfigurationDTO config = new ConfigurationDTO();

            Properties properties = new Properties();

            try {
                //Setamos o arquivo que vai ser lido
                FileInputStream fis = new FileInputStream(path + PATH_FILE);
                //metodo load faz a leitura atraves do objeto fis
                properties.load(fis);

                config.setDown(properties.getProperty("button.down"));
                config.setLeft(properties.getProperty("button.left"));
                config.setRight(properties.getProperty("button.right"));
                config.setUp(properties.getProperty("button.up"));
                config.setOk(properties.getProperty("button.ok"));
                config.setBack(properties.getProperty("button.back"));
                config.setVolumeDown(properties.getProperty("button.volumeDown"));
                config.setVolumeUp(properties.getProperty("button.volumeUp"));
                config.setSubtitleScreen(properties.getProperty("button.subtitleScreen"));
                config.setPlayPause(properties.getProperty("button.playPause"));
                config.setStop(properties.getProperty("button.stop"));
                config.setSettings(properties.getProperty("button.settings"));
                config.setDisable(properties.getProperty("button.disable"));
                
                config.password = decryptText(properties.getProperty("config.password"));
                config.urlKODI = properties.getProperty("config.urlKODI");
                config.user = properties.getProperty("config.user");
                config.sistema = properties.getProperty("config.sistema");
                
                configuration = config;

            } catch (IOException e) {
                throw new RuntimeException("Erro ao carregar o arquivo de configurações.", e);
            }

            return config;
        }
    }

    private static String encryptText(String texto) {
        try {
            Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV_KEY.getBytes("UTF-8")));
            byte[] bytes = encripta.doFinal(texto.getBytes("UTF-8"));
            String encryptString = "";
            for (int i = 0; i < bytes.length; i++) {
                encryptString = encryptString + new Integer(bytes[i]) + " ";
            }
            return encryptString;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException("Erro ao criptografar o texto.");
        }
    }

    private static String decryptText(String texto) {
        if (texto.equals("")) {
            return "";
        }
        try {
            byte[] textoencriptado = converterStringEmBytes(texto);
            Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
            decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV_KEY.getBytes("UTF-8")));
            return new String(decripta.doFinal(textoencriptado), "UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | UnsupportedEncodingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
            throw new RuntimeException("Erro ao descriptografar o texto.");
        }
    }

    private static byte[] converterStringEmBytes(String texto) {
        String[] bytesString = texto.split(" ");
        byte[] bytes = new byte[bytesString.length];

        for (int i = 0; i < bytesString.length; i++) {
            bytes[i] = (byte) Integer.parseInt(bytesString[i]);
        }
        return bytes;
    }

    private final static String IV_KEY = "?QqQ~/\"6BVq3_PkD";
    private final static String KEY = "&&XiACFa77P<sLka";
}
