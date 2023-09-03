package com.vivahlinda.salesmanagement.utils;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class VivahLindaUtils {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public VivahLindaUtils() {
    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"mensagem\":\"" + responseMessage + "\"}", httpStatus);
    }

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, dateFormatter);
    }

    public static String getUUID() {
        Date date = new Date();
        long time = date.getTime();
        return "VIVAHLINDAVENDA-" + time;
    }

    // Converter uma string JSON em um JSONArray
    public static JSONArray getJsonArrayFromString(String dados) throws JSONException {
        JSONArray jsonArray = new JSONArray(dados);
        return jsonArray;
    }

    // Método estático para converter uma string JSON em um Map<String, Object>
    public static Map<String, Object> getMapFromJson(String dados) {
        if (!Strings.isNullOrEmpty(dados))
            // Utiliza a biblioteca Gson para fazer a conversão
            return new Gson().fromJson(dados, new TypeToken<Map<String, Object>>() {
            }.getType());
        // Retorna um HashMap vazio caso a string seja nula ou vazia
        return new HashMap<>();
    }

    public static String getDataHoraAtualFormatada() {
        LocalDateTime dataHoraAtual = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dataHoraAtual.format(formatter);
    }

    public static Boolean isArquivoExiste(String caminhoArquivo) {
        log.info("Verificando se o caminho do arquivo existe {}", caminhoArquivo);
        try {
            File arquivo = new File(caminhoArquivo);
            Boolean temOuNao = (arquivo != null && arquivo.exists()) ? Boolean.TRUE : Boolean.FALSE;
            if (temOuNao) {
                log.info("SIM o arquivo existe {}", temOuNao);
            } else {
                log.info("O arquivo NÃO existe {}", temOuNao);
            }
            return temOuNao;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public static BigDecimal converteValorBigdecimal(String precoString) {
        precoString = precoString.replace(".", "").replace(",", ".");
        return new BigDecimal(precoString);
    }

}
