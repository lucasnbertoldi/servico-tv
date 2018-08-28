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
import java.util.ArrayList;
import java.util.List;
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

    public static List<ButtonDTO> buttonList = new ArrayList<>();

    private static String PATH_SOFTWARE;

    public static String getSoftwareFolderName() {
        if (PATH_SOFTWARE == null) {
            PATH_SOFTWARE = System.getProperty("user.dir");
        }
        return PATH_SOFTWARE;
    }

    public static void setConfiguration(ConfigurationDTO config, List<ButtonDTO> newButtonList) throws IOException {

        String path = new File(getSoftwareFolderName()).getCanonicalPath();

        Properties properties = new Properties();

        //setando as propriedades(key) e os seus valores(value)
        properties.setProperty("config.user", config.user);
        properties.setProperty("config.password", encryptText(config.password));
        properties.setProperty("config.urlKODI", config.urlKODI);
        properties.setProperty("config.sistema", config.sistema);
        properties.setProperty("config.showScreen", config.showScreen + "");
        properties.setProperty("config.showControllerLOG", config.showControllerLOG + "");
        properties.setProperty("config.delayMouse", config.delayMouse + "");
        properties.setProperty("config.delayNumber", config.delayNumber + "");
        properties.setProperty("config.delayOpenKodi", config.delayOpenKodi + "");

        for (ButtonDTO buttonDTO : newButtonList) {
            properties.setProperty(buttonDTO.getButtonEnum().getPropertyName(), buttonDTO.getTextCodeList());
        }

        buttonList = newButtonList;

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
                path = new File(getSoftwareFolderName()).getCanonicalPath();
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

                for (ButtonDTO buttonDTO : buttonList) {
                    buttonDTO.setCodeList(properties.getProperty(buttonDTO.getButtonEnum().getPropertyName()));
                }

                config.password = decryptText(properties.getProperty("config.password"));
                config.urlKODI = properties.getProperty("config.urlKODI");
                config.user = properties.getProperty("config.user");
                config.sistema = properties.getProperty("config.sistema");
                config.showScreen = Boolean.parseBoolean(properties.getProperty("config.showScreen"));
                config.showControllerLOG = Boolean.parseBoolean(properties.getProperty("config.showControllerLOG"));
                config.delayMouse = Integer.parseInt(properties.getProperty("config.delayMouse") == null ? "0" : properties.getProperty("config.delayMouse"));
                config.delayNumber = Integer.parseInt(properties.getProperty("config.delayNumber") == null ? "0" : properties.getProperty("config.delayNumber"));
                config.delayOpenKodi = Integer.parseInt(properties.getProperty("config.delayOpenKodi") == null ? "0" : properties.getProperty("config.delayOpenKodi"));

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
