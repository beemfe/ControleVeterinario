/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao implements IVeterinarioDao {

    private static final String TABLE_NAME = "veterinarios";

    @Override
    public void create(SQLiteDatabase db, Veterinario veterinario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", veterinario.getNome());
        values.put("crmv", veterinario.getCrmv());
        values.put("especialidade", veterinario.getEspecialidade());

        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            throw new Exception("Erro ao inserir veterinário no banco de dados");
        }
        veterinario.setId((int) id);
    }

    @Override
    public Veterinario read(SQLiteDatabase db, int id) throws Exception {
        String[] columns = {"id", "nome", "crmv", "especialidade"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Veterinario veterinario = cursorToVeterinario(cursor);
            cursor.close();
            return veterinario;
        }

        throw new Exception("Veterinário não encontrado");
    }

    @Override
    public void update(SQLiteDatabase db, Veterinario veterinario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", veterinario.getNome());
        values.put("crmv", veterinario.getCrmv());
        values.put("especialidade", veterinario.getEspecialidade());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(veterinario.getId())};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum veterinário atualizado");
        }
    }

    @Override
    public void delete(SQLiteDatabase db, int id) throws Exception {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum veterinário excluído");
        }
    }

    @Override
    public List<Veterinario> readAll(SQLiteDatabase db) throws Exception {
        List<Veterinario> veterinarios = new ArrayList<>();
        String[] columns = {"id", "nome", "crmv", "especialidade"};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                veterinarios.add(cursorToVeterinario(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return veterinarios;
    }

    @Override
    public List<Veterinario> readBySpecialty(SQLiteDatabase db, String specialty) throws Exception {
        List<Veterinario> veterinarios = new ArrayList<>();
        String[] columns = {"id", "nome", "crmv", "especialidade"};
        String selection = "especialidade = ?";
        String[] selectionArgs = {specialty};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                veterinarios.add(cursorToVeterinario(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return veterinarios;
    }

    private Veterinario cursorToVeterinario(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        String crmv = cursor.getString(cursor.getColumnIndexOrThrow("crmv"));
        String especialidade = cursor.getString(cursor.getColumnIndexOrThrow("especialidade"));

        Veterinario veterinario = new Veterinario(nome, crmv, especialidade);
        veterinario.setId(id);
        return veterinario;
    }
}