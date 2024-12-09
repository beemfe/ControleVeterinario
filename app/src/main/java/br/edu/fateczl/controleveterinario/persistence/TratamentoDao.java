/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.model.Tratamento;
import br.edu.fateczl.controleveterinario.model.Vacinacao;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TratamentoDao implements ITratamentoDao {
    private static final String TABLE_NAME = "tratamentos";

    @Override
    public void create(SQLiteDatabase db, Tratamento tratamento) throws Exception {
        ContentValues values = new ContentValues();
        values.put("animal_id", tratamento.getAnimal().getId());
        values.put("data_inicio", tratamento.getDataInicio().getTime());
        values.put("data_fim", tratamento.getDataFim() != null ? tratamento.getDataFim().getTime() : null);
        values.put("descricao", tratamento.getDescricao());
        values.put("tipo", tratamento instanceof Vacinacao ? "Vacinacao" : "Tratamento");

        if (tratamento instanceof Vacinacao) {
            values.put("tipo_vacina", ((Vacinacao) tratamento).getTipoVacina());
        }

        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            throw new Exception("Erro ao inserir tratamento no banco de dados");
        }
        tratamento.setId((int) id);
    }

    @Override
    public Tratamento read(SQLiteDatabase db, int id) throws Exception {
        String[] columns = {"id", "animal_id", "data_inicio", "data_fim", "descricao", "tipo", "tipo_vacina"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Tratamento tratamento = cursorToTratamento(cursor, db);
            cursor.close();
            return tratamento;
        }
        throw new Exception("Tratamento não encontrado");
    }

    @Override
    public void update(SQLiteDatabase db, Tratamento tratamento) throws Exception {
        ContentValues values = new ContentValues();
        values.put("animal_id", tratamento.getAnimal().getId());
        values.put("data_inicio", tratamento.getDataInicio().getTime());
        values.put("data_fim", tratamento.getDataFim() != null ? tratamento.getDataFim().getTime() : null);
        values.put("descricao", tratamento.getDescricao());
        values.put("tipo", tratamento instanceof Vacinacao ? "Vacinacao" : "Tratamento");

        if (tratamento instanceof Vacinacao) {
            values.put("tipo_vacina", ((Vacinacao) tratamento).getTipoVacina());
        }

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(tratamento.getId())};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum tratamento atualizado");
        }
    }

    @Override
    public void delete(SQLiteDatabase db, int id) throws Exception {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum tratamento excluído");
        }
    }

    @Override
    public List<Tratamento> readAll(SQLiteDatabase db) throws Exception {
        List<Tratamento> tratamentos = new ArrayList<>();
        String[] columns = {"id", "animal_id", "data_inicio", "data_fim", "descricao", "tipo", "tipo_vacina"};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                tratamentos.add(cursorToTratamento(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tratamentos;
    }

    @Override
    public List<Tratamento> readByAnimal(SQLiteDatabase db, int animalId) throws Exception {
        List<Tratamento> tratamentos = new ArrayList<>();
        String[] columns = {"id", "animal_id", "data_inicio", "data_fim", "descricao", "tipo", "tipo_vacina"};
        String selection = "animal_id = ?";
        String[] selectionArgs = {String.valueOf(animalId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                tratamentos.add(cursorToTratamento(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tratamentos;
    }

    @Override
    public List<Tratamento> readByType(SQLiteDatabase db, String type) throws Exception {
        List<Tratamento> tratamentos = new ArrayList<>();
        String[] columns = {"id", "animal_id", "data_inicio", "data_fim", "descricao", "tipo", "tipo_vacina"};
        String selection = "tipo = ?";
        String[] selectionArgs = {type};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                tratamentos.add(cursorToTratamento(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tratamentos;
    }

    private Tratamento cursorToTratamento(Cursor cursor, SQLiteDatabase db) throws Exception {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        int animalId = cursor.getInt(cursor.getColumnIndexOrThrow("animal_id"));
        long dataInicio = cursor.getLong(cursor.getColumnIndexOrThrow("data_inicio"));
        Long dataFim = cursor.isNull(cursor.getColumnIndexOrThrow("data_fim")) ? null :
                cursor.getLong(cursor.getColumnIndexOrThrow("data_fim"));
        String descricao = cursor.getString(cursor.getColumnIndexOrThrow("descricao"));
        String tipo = cursor.getString(cursor.getColumnIndexOrThrow("tipo"));

        Animal animal = new AnimalDao().read(db, animalId);

        Tratamento tratamento;
        if ("Vacinacao".equals(tipo)) {
            String tipoVacina = cursor.getString(cursor.getColumnIndexOrThrow("tipo_vacina"));
            tratamento = new Vacinacao(animal, new Date(dataInicio), descricao, tipoVacina);
        } else {
            tratamento = new Tratamento(animal, new Date(dataInicio), descricao) {
                @Override
                public boolean isCompleto() {
                    return getDataFim() != null;
                }
            };
        }

        tratamento.setId(id);
        if (dataFim != null) {
            tratamento.setDataFim(new Date(dataFim));
        }

        return tratamento;
    }
}