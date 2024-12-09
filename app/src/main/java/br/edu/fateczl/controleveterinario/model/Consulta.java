/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.model;

import java.util.Date;

public class Consulta implements Notificavel {
    private int id;
    private Animal animal;
    private Veterinario veterinario;
    private Date dataConsulta;
    private String motivo;
    private String diagnostico;
    private String tratamento;

    public Consulta(Animal animal, Veterinario veterinario, Date dataConsulta, String motivo) {
        this.animal = animal;
        this.veterinario = veterinario;
        this.dataConsulta = dataConsulta;
        this.motivo = motivo;
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

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    @Override
    public void enviarNotificacao() {
        // Implementação da lógica de notificação
        System.out.println("Enviando notificação de consulta para " + animal.getProprietario().getNome());
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "id=" + id +
                ", animal=" + animal +
                ", veterinario=" + veterinario +
                ", dataConsulta=" + dataConsulta +
                ", motivo='" + motivo + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamento='" + tratamento + '\'' +
                '}';
    }
}