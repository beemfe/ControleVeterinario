/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.persistence.AnimalDao;
import br.edu.fateczl.controleveterinario.persistence.IAnimalDao;
import br.edu.fateczl.controleveterinario.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class AnimalController implements IController<Animal> {
    private IAnimalDao animalDao;
    private Context context;
    private DatabaseHelper dbHelper;

    public AnimalController(Context context) {
        this.context = context;
        this.animalDao = new AnimalDao();
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void adicionar(Animal animal) {
        try {
            if (animal == null) {
                throw new IllegalArgumentException("Animal não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            animalDao.create(db, animal);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void atualizar(Animal animal) {
        try {
            if (animal == null) {
                throw new IllegalArgumentException("Animal não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            animalDao.update(db, animal);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void excluir(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            animalDao.delete(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public Animal buscarPorId(int id) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return animalDao.read(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return null;
        }
    }

    @Override
    public List<Animal> listarTodos() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return animalDao.readAll(db);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return new ArrayList<>();
        }
    }
}