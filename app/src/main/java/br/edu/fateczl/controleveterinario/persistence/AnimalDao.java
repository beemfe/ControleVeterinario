/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.model.Proprietario;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AnimalDao implements IAnimalDao {
    private static final String TABLE_NAME = "animals";

    @Override
    public void create(SQLiteDatabase db, Animal animal) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", animal.getNome());
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("data_nascimento", animal.getDataNascimento().getTime());
        values.put("sexo", animal.getSexo());
        values.put("proprietario_id", animal.getProprietario().getId());

        long id = db.insert(TABLE_NAME, null, values);
        if (id == -1) {
            throw new Exception("Erro ao inserir animal no banco de dados");
        }
        animal.setId((int) id);
    }

    @Override
    public Animal read(SQLiteDatabase db, int id) throws Exception {
        String[] columns = {"id", "nome", "especie", "raca", "data_nascimento", "sexo", "proprietario_id"};
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Animal animal = cursorToAnimal(cursor, db);
            cursor.close();
            return animal;
        }
        throw new Exception("Animal não encontrado");
    }

    @Override
    public void update(SQLiteDatabase db, Animal animal) throws Exception {
        ContentValues values = new ContentValues();
        values.put("nome", animal.getNome());
        values.put("especie", animal.getEspecie());
        values.put("raca", animal.getRaca());
        values.put("data_nascimento", animal.getDataNascimento().getTime());
        values.put("sexo", animal.getSexo());
        values.put("proprietario_id", animal.getProprietario().getId());

        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(animal.getId())};

        int rowsAffected = db.update(TABLE_NAME, values, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum animal atualizado");
        }
    }

    @Override
    public void delete(SQLiteDatabase db, int id) throws Exception {
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(TABLE_NAME, whereClause, whereArgs);
        if (rowsAffected == 0) {
            throw new Exception("Nenhum animal excluído");
        }
    }

    @Override
    public List<Animal> readAll(SQLiteDatabase db) throws Exception {
        List<Animal> animals = new ArrayList<>();
        String[] columns = {"id", "nome", "especie", "raca", "data_nascimento", "sexo", "proprietario_id"};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                animals.add(cursorToAnimal(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return animals;
    }

    @Override
    public List<Animal> readBySpecies(SQLiteDatabase db, String species) throws Exception {
        List<Animal> animals = new ArrayList<>();
        String[] columns = {"id", "nome", "especie", "raca", "data_nascimento", "sexo", "proprietario_id"};
        String selection = "especie = ?";
        String[] selectionArgs = {species};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                animals.add(cursorToAnimal(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return animals;
    }

    @Override
    public List<Animal> readByOwner(SQLiteDatabase db, int ownerId) throws Exception {
        List<Animal> animals = new ArrayList<>();
        String[] columns = {"id", "nome", "especie", "raca", "data_nascimento", "sexo", "proprietario_id"};
        String selection = "proprietario_id = ?";
        String[] selectionArgs = {String.valueOf(ownerId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                animals.add(cursorToAnimal(cursor, db));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return animals;
    }

    private Animal cursorToAnimal(Cursor cursor, SQLiteDatabase db) throws Exception {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"));
        String especie = cursor.getString(cursor.getColumnIndexOrThrow("especie"));
        String raca = cursor.getString(cursor.getColumnIndexOrThrow("raca"));
        long dataNascimento = cursor.getLong(cursor.getColumnIndexOrThrow("data_nascimento"));
        String sexo = cursor.getString(cursor.getColumnIndexOrThrow("sexo"));
        int proprietarioId = cursor.getInt(cursor.getColumnIndexOrThrow("proprietario_id"));

        Proprietario proprietario = new ProprietarioDao().read(db, proprietarioId);

        Animal animal = new Animal(nome, new Date(dataNascimento), sexo, proprietario, especie, raca);
        animal.setId(id);
        return animal;
    }
}