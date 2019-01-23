package projetoestacionamento.com.br.projetoestacionamento;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe responsável por executar a conversão de texto para hash MD5
 */

public class MD5 {

    /**
     * @param senha
     * @return a senha convertida em hash em caso de sucesso, senão retorna "ERRO"
     */
    public static String convertTextoParaMD5(String senha){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(senha.getBytes(),0,senha.length());
            return new BigInteger(1,messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "ERRO";
    }
}
