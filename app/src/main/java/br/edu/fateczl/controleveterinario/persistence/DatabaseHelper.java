/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "controle_veterinario.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE animals (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, especie TEXT, raca TEXT, data_nascimento INTEGER, sexo TEXT, proprietario_id INTEGER)");
        db.execSQL("CREATE TABLE veterinarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, crmv TEXT, especialidade TEXT)");
        db.execSQL("CREATE TABLE consultas (id INTEGER PRIMARY KEY AUTOINCREMENT, animal_id INTEGER, veterinario_id INTEGER, data_consulta INTEGER, motivo TEXT, diagnostico TEXT, tratamento TEXT)");
        db.execSQL("CREATE TABLE proprietarios (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, endereco TEXT, telefone TEXT, email TEXT)");
        db.execSQL("CREATE TABLE tratamentos (id INTEGER PRIMARY KEY AUTOINCREMENT, animal_id INTEGER, data_inicio INTEGER, data_fim INTEGER, descricao TEXT, tipo TEXT, tipo_vacina TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}