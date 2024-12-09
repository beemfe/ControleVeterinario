/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import java.util.List;

public interface GenericDao<T> {
    void create(SQLiteDatabase db, T entity) throws Exception;
    T read(SQLiteDatabase db, int id) throws Exception;
    void update(SQLiteDatabase db, T entity) throws Exception;
    void delete(SQLiteDatabase db, int id) throws Exception;
    List<T> readAll(SQLiteDatabase db) throws Exception;
}