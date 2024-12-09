/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Consulta;
import br.edu.fateczl.controleveterinario.persistence.ConsultaDao;
import br.edu.fateczl.controleveterinario.persistence.IConsultaDao;
import br.edu.fateczl.controleveterinario.persistence.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;

public class ConsultaController implements IController<Consulta> {
    private IConsultaDao consultaDao;
    private Context context;
    private DatabaseHelper dbHelper;

    public ConsultaController(Context context) {
        this.context = context;
        this.consultaDao = new ConsultaDao();
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public void adicionar(Consulta consulta) {
        try {
            if (consulta == null) {
                throw new IllegalArgumentException("Consulta não pode ser nula");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            consultaDao.create(db, consulta);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void atualizar(Consulta consulta) {
        try {
            if (consulta == null) {
                throw new IllegalArgumentException("Consulta não pode ser nula");
            }
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            consultaDao.update(db, consulta);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public void excluir(int id) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            consultaDao.delete(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
        }
    }

    @Override
    public Consulta buscarPorId(int id) {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return consultaDao.read(db, id);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return null;
        }
    }

    @Override
    public List<Consulta> listarTodos() {
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            return consultaDao.readAll(db);
        } catch (Exception e) {
            ExceptionHandler.handleException(context, e);
            return new ArrayList<>();
        }
    }
}