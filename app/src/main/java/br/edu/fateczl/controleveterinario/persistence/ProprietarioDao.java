/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Proprietario;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDao implements IProprietarioDao {

    private static final String TABLE_NAME = "proprietarios";

    @Override
    public void create(SQLiteDatabase db, Proprietario proprietario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", proprietario.getNome());
        values.put("endereco", proprietario.getEndereco());
        values.put("telefone", proprietario.getTelefone());
        values.put("email", proprietario.getEmail());

        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            throw new Exception("Erro ao inserir proprietário no banco de dados");
        }
        proprietario.setId((int) id);
    }

    @Override
    public Proprietario read(SQLiteDatabase db, int id) throws Exception {
        String[] columns = {"id", "nome", "endereco", "telefone", "email"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Proprietario proprietario = cursorToProprietario(cursor);
            cursor.close();
            return proprietario;
        }

        throw new Exception("Proprietário não encontrado");
    }

    @Override
    public void update(SQLiteDatabase db, Proprietario proprietario) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", proprietario.getNome());
        values.put("endereco", proprietario.getEndereco());
        values.put("telefone", proprietario.getTelefone());
        values.put("email", proprietario.getEmail());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(proprietario.getId())};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum proprietário atualizado");
        }
    }

    @Override
    public void delete(SQLiteDatabase db, int id) throws Exception {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum proprietário excluído");
        }
    }

    @Override
    public List<Proprietario> readAll(SQLiteDatabase db) throws Exception {
        List<Proprietario> proprietarios = new ArrayList<>();
        String[] columns = {"id", "nome", "endereco", "telefone", "email"};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                proprietarios.add(cursorToProprietario(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return proprietarios;
    }

    @Override
    public List<Proprietario> readByName(SQLiteDatabase db, String name) throws Exception {
        List<Proprietario> proprietarios = new ArrayList<>();
        String[] columns = {"id", "nome", "endereco", "telefone", "email"};
        String selection = "nome LIKE ?";
        String[] selectionArgs = {"%" + name + "%"};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                proprietarios.add(cursorToProprietario(cursor));
            } while (cursor.moveToNext());
            cursor.close();
        }

        return proprietarios;
    }

    private Proprietario cursorToProprietario(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        String endereco = cursor.getString(cursor.getColumnIndexOrThrow("endereco"));
        String telefone = cursor.getString(cursor.getColumnIndexOrThrow("telefone"));
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));

        Proprietario proprietario = new Proprietario(nome, endereco, telefone, email);
        proprietario.setId(id);
        return proprietario;
    }
}