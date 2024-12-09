/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ExceptionHandler {
    private static final String TAG = "ControleVeterinario";

    public static void handleException(Context context, Exception e) {
        Log.e(TAG, "Erro: " + e.getMessage(), e);
        String mensagemUsuario = getMensagemUsuario(e);
        Toast.makeText(context, mensagemUsuario, Toast.LENGTH_LONG).show();
    }

    private static String getMensagemUsuario(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return "Dados inválidos. Por favor, verifique as informações inseridas.";
        } else if (e instanceof NullPointerException) {
            return "Ocorreu um erro inesperado. Por favor, tente novamente.";
        } else if (e instanceof android.database.sqlite.SQLiteException) {
            return "Erro ao acessar o banco de dados. Por favor, tente novamente mais tarde.";
        } else {
            return "Ocorreu um erro. Por favor, entre em contato com o suporte.";
        }
    }
}