/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Tratamento;
import br.edu.fateczl.controleveterinario.persistence.TratamentoDao;
import br.edu.fateczl.controleveterinario.persistence.ITratamentoDao;
import br.edu.fateczl.controleveterinario.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class TratamentoController implements IController<Tratamento> {
    private ITratamentoDao tratamentoDao;
    private Context context;
    private DatabaseHelper dbHelper;

    public TratamentoController(Context context) {
        this.context = context;
        this.tratamentoDao = new TratamentoDao();
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void adicionar(Tratamento tratamento) {
        try {
            if (tratamento == null) {
                throw new IllegalArgumentException("Tratamento não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            tratamentoDao.create(db, tratamento);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void atualizar(Tratamento tratamento) {
        try {
            if (tratamento == null) {
                throw new IllegalArgumentException("Tratamento não pode ser nulo");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            tratamentoDao.update(db, tratamento);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void excluir(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            tratamentoDao.delete(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public Tratamento buscarPorId(int id) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return tratamentoDao.read(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return null;
        }
    }

    @Override
    public List<Tratamento> listarTodos() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return tratamentoDao.readAll(db);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return new ArrayList<>();
        }
    }
}