/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Proprietario;
import java.util.List;

public interface IProprietarioDao extends GenericDao<Proprietario> {
    List<Proprietario> readByName(SQLiteDatabase db, String name) throws Exception;
}