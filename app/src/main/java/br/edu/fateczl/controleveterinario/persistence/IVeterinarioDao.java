/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.persistence;

import android.database.sqlite.SQLiteDatabase;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import java.util.List;

public interface IVeterinarioDao extends GenericDao<Veterinario> {
    List<Veterinario> readBySpecialty(SQLiteDatabase db, String specialty) throws Exception;
}