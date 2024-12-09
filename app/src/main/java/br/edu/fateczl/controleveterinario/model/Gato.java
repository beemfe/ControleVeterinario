/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public class Gato extends Animal {
    private boolean castrado;

    public Gato(String nome, Date dataNascimento, String sexo, Proprietario proprietario, String raca, boolean castrado) {
        super(nome, dataNascimento, sexo, proprietario, "Gato", raca);
        this.castrado = castrado;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    @Override
    public int calcularIdade() {
        int idadeHumana = super.calcularIdade();
        return idadeHumana <= 2 ? idadeHumana * 12 : 24 + ((idadeHumana - 2) * 4);
    }

    @Override
    public String toString() {
        return super.toString() + " Gato{" +
                "castrado=" + castrado +
                '}';
    }
}