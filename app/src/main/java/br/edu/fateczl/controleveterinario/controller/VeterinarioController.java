/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import br.edu.fateczl.controleveterinario.persistence.VeterinarioDao;
import br.edu.fateczl.controleveterinario.persistence.IVeterinarioDao;
import br.edu.fateczl.controleveterinario.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioController implements IController<Veterinario> {
    private IVeterinarioDao veterinarioDao;
    private Context context;
    private DatabaseHelper dbHelper;

    public VeterinarioController(Context context) {
        this.context = context;
        this.veterinarioDao = new VeterinarioDao();
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void adicionar(Veterinario veterinario) {
        try {
            if (veterinario == null) {
                throw new IllegalArgumentException("Veterinário não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            veterinarioDao.create(db, veterinario);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void atualizar(Veterinario veterinario) {
        try {
            if (veterinario == null) {
                throw new IllegalArgumentException("Veterinário não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            veterinarioDao.update(db, veterinario);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void excluir(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            veterinarioDao.delete(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public Veterinario buscarPorId(int id) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return veterinarioDao.read(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return null;
        }
    }

    @Override
    public List<Veterinario> listarTodos() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return veterinarioDao.readAll(db);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return new ArrayList<>();
        }
    }
}