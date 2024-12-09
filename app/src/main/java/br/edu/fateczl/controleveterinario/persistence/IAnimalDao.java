/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Animal;
import java.util.List;

public interface IAnimalDao extends GenericDao<Animal> {
    List<Animal> readBySpecies(SQLiteDatabase db, String species) throws Exception;
    List<Animal> readByOwner(SQLiteDatabase db, int ownerId) throws Exception;
}