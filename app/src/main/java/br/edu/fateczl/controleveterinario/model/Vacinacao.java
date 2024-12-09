/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public class Vacinacao extends Tratamento implements Notificavel {
    private String tipoVacina;

    public Vacinacao(Animal animal, Date dataInicio, String descricao, String tipoVacina) {
        super(animal, dataInicio, descricao);
        this.tipoVacina = tipoVacina;
    }

    public String getTipoVacina() {
        return tipoVacina;
    }

    public void setTipoVacina(String tipoVacina) {
        this.tipoVacina = tipoVacina;
    }

    @Override
    public boolean isCompleto() {
        return dataFim != null;
    }

    @Override
    public void enviarNotificacao() {
        System.out.println("Enviando notificação de vacinação para " + animal.getProprietario().getNome());
    }

    @Override
    public String toString() {
        return super.toString() + " Vacinacao{" +
                "tipoVacina='" + tipoVacina + '\'' +
                '}';
    }
}