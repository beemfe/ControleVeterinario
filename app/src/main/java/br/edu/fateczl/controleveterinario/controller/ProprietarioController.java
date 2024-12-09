/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Proprietario;
import br.edu.fateczl.controleveterinario.persistence.ProprietarioDao;
import br.edu.fateczl.controleveterinario.persistence.IProprietarioDao;
import br.edu.fateczl.controleveterinario.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioController implements IController<Proprietario> {
    private IProprietarioDao proprietarioDao;
    private Context context;
    private DatabaseHelper dbHelper;

    public ProprietarioController(Context context) {
        this.context = context;
        this.proprietarioDao = new ProprietarioDao();
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void adicionar(Proprietario proprietario) {
        try {
            if (proprietario == null) {
                throw new IllegalArgumentException("Proprietário não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            proprietarioDao.create(db, proprietario);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void atualizar(Proprietario proprietario) {
        try {
            if (proprietario == null) {
                throw new IllegalArgumentException("Proprietário não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            proprietarioDao.update(db, proprietario);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void excluir(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            proprietarioDao.delete(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public Proprietario buscarPorId(int id) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return proprietarioDao.read(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return null;
        }
    }

    @Override
    public List<Proprietario> listarTodos() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return proprietarioDao.readAll(db);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return new ArrayList<>();
        }
    }
}