/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public abstract class Tratamento {
    protected int id;
    protected Animal animal;
    protected Date dataInicio;
    protected Date dataFim;
    protected String descricao;

    public Tratamento(Animal animal, Date dataInicio, String descricao) {
        this.animal = animal;
        this.dataInicio = dataInicio;
        this.descricao = descricao;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public abstract boolean isCompleto();

    @Override
    public String toString() {
        return "Tratamento{" +
                "id=" + id +
                ", animal=" + animal +
                ", dataInicio=" + dataInicio +
                ", dataFim=" + dataFim +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}