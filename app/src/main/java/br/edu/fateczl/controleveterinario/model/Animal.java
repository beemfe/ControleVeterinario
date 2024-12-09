/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public class Animal extends AbstractAnimal {
    private String especie;
    private String raca;

    public Animal(String nome, Date dataNascimento, String sexo, Proprietario proprietario, String especie, String raca) {
        super(nome, dataNascimento, sexo, proprietario);
        this.especie = especie;
        this.raca = raca;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public int calcularIdade() {
        Date hoje = new Date();
        long diferencaEmMilissegundos = hoje.getTime() - dataNascimento.getTime();
        return (int) (diferencaEmMilissegundos / (1000L * 60 * 60 * 24 * 365));
    }

    @Override
    public String toString() {
        return super.toString() + " Animal{" +
                "especie='" + especie + '\'' +
                ", raca='" + raca + '\'' +
                '}';
    }
}