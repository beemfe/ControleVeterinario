/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Tratamento;
import java.util.List;

public interface ITratamentoDao extends GenericDao<Tratamento> {
    List<Tratamento> readByAnimal(SQLiteDatabase db, int animalId) throws Exception;
    List<Tratamento> readByType(SQLiteDatabase db, String type) throws Exception;
}