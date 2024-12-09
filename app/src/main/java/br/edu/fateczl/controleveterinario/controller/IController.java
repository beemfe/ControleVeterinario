/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.controller;

import java.util.List;

public interface IController<T> {
    void adicionar(T objeto) throws Exception;
    void atualizar(T objeto) throws Exception;
    void excluir(int id) throws Exception;
    T buscarPorId(int id) throws Exception;
    List<T> listarTodos() throws Exception;
}