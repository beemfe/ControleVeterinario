/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public class Cachorro extends Animal {
    private String porte;

    public Cachorro(String nome, Date dataNascimento, String sexo, Proprietario proprietario, String raca, String porte) {
        super(nome, dataNascimento, sexo, proprietario, "Cachorro", raca);
        this.porte = porte;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    @Override
    public int calcularIdade() {
        int idadeHumana = super.calcularIdade();
        return idadeHumana <= 2 ? idadeHumana * 10 : 20 + ((idadeHumana - 2) * 4);
    }

    @Override
    public String toString() {
        return super.toString() + " Cachorro{" +
                "porte='" + porte + '\'' +
                '}';
    }
}