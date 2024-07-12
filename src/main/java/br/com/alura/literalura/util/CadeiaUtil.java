package br.com.alura.literalura.util;

public class CadeiaUtil {

    public static String limitarLongitud(String cadeia, int longitudMaxima) {
        if (cadeia.length() <= longitudMaxima) {
            return cadeia;
        } else {
            return cadeia.substring(0, longitudMaxima);
        }
    }

}
