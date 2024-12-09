/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Consulta;
import java.util.Date;
import java.util.List;

public interface IConsultaDao extends GenericDao<Consulta> {
    List<Consulta> readByAnimal(SQLiteDatabase db, int animalId) throws Exception;
    List<Consulta> readByVeterinarian(SQLiteDatabase db, int veterinarianId) throws Exception;
    List<Consulta> readByDate(SQLiteDatabase db, Date date) throws Exception;
}