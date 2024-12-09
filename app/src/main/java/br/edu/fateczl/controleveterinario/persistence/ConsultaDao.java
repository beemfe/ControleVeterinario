/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.model.Consulta;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ConsultaDao implements IConsultaDao {
    private static final String TABLE_NAME = "consultas";

    @Override
    public void create(SQLiteDatabase db, Consulta consulta) throws Exception {
        ContentValues values = new ContentValues();
        values.put("animal_id", consulta.getAnimal().getId());
        values.put("veterinario_id", consulta.getVeterinario().getId());
        values.put("data_consulta", consulta.getDataConsulta().getTime());
        values.put("motivo", consulta.getMotivo());
        values.put("diagnostico", consulta.getDiagnostico());
        values.put("tratamento", consulta.getTratamento());

        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            throw new Exception("Erro ao inserir consulta no banco de dados");
        }
        consulta.setId((int) id);
    }

    @Override
    public Consulta read(SQLiteDatabase db, int id) throws Exception {
        String[] columns = {"id", "animal_id", "veterinario_id", "data_consulta", "motivo", "diagnostico", "tratamento"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Consulta consulta = cursorToConsulta(cursor, db);
            cursor.close();
            return consulta;
        }
        throw new Exception("Consulta não encontrada");
    }

    @Override
    public void update(SQLiteDatabase db, Consulta consulta) throws Exception {
        ContentValues values = new ContentValues();
        values.put("animal_id", consulta.getAnimal().getId());
        values.put("veterinario_id", consulta.getVeterinario().getId());
        values.put("data_consulta", consulta.getDataConsulta().getTime());
        values.put("motivo", consulta.getMotivo());
        values.put("diagnostico", consulta.getDiagnostico());
        values.put("tratamento", consulta.getTratamento());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(consulta.getId())};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhuma consulta atualizada");
        }
    }

    @Override
    public void delete(SQLiteDatabase db, int id) throws Exception {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhuma consulta excluída");
        }
    }

    @Override
    public List<Consulta> readAll(SQLiteDatabase db) throws Exception {
        List<Consulta> consultas = new ArrayList<>();
        String[] columns = {"id", "animal_id", "veterinario_id", "data_consulta", "motivo", "diagnostico", "tratamento"};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                consultas.add(cursorToConsulta(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return consultas;
    }

    @Override
    public List<Consulta> readByAnimal(SQLiteDatabase db, int animalId) throws Exception {
        List<Consulta> consultas = new ArrayList<>();
        String[] columns = {"id", "animal_id", "veterinario_id", "data_consulta", "motivo", "diagnostico", "tratamento"};
        String selection = "animal_id = ?";
        String[] selectionArgs = {String.valueOf(animalId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                consultas.add(cursorToConsulta(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return consultas;
    }

    @Override
    public List<Consulta> readByVeterinarian(SQLiteDatabase db, int veterinarianId) throws Exception {
        List<Consulta> consultas = new ArrayList<>();
        String[] columns = {"id", "animal_id", "veterinario_id", "data_consulta", "motivo", "diagnostico", "tratamento"};
        String selection = "veterinario_id = ?";
        String[] selectionArgs = {String.valueOf(veterinarianId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                consultas.add(cursorToConsulta(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return consultas;
    }

    @Override
    public List<Consulta> readByDate(SQLiteDatabase db, Date date) throws Exception {
        List<Consulta> consultas = new ArrayList<>();
        String[] columns = {"id", "animal_id", "veterinario_id", "data_consulta", "motivo", "diagnostico", "tratamento"};
        String selection = "date(data_consulta/1000, 'unixepoch') = date(?/1000, 'unixepoch')";
        String[] selectionArgs = {String.valueOf(date.getTime())};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                consultas.add(cursorToConsulta(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return consultas;
    }

    private Consulta cursorToConsulta(Cursor cursor, SQLiteDatabase db) throws Exception {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        int animalId = cursor.getInt(cursor.getColumnIndexOrThrow("animal_id"));
        int veterinarioId = cursor.getInt(cursor.getColumnIndexOrThrow("veterinario_id"));
        long dataConsulta = cursor.getLong(cursor.getColumnIndexOrThrow("data_consulta"));
        String motivo = cursor.getString(cursor.getColumnIndexOrThrow("motivo"));
        String diagnostico = cursor.getString(cursor.getColumnIndexOrThrow("diagnostico"));
        String tratamento = cursor.getString(cursor.getColumnIndexOrThrow("tratamento"));

        Animal animal = new AnimalDao().read(db, animalId);
        Veterinario veterinario = new VeterinarioDao().read(db, veterinarioId);

        Consulta consulta = new Consulta(animal, veterinario, new Date(dataConsulta), motivo);
        consulta.setId(id);
        consulta.setDiagnostico(diagnostico);
        consulta.setTratamento(tratamento);
        return consulta;
    }
}