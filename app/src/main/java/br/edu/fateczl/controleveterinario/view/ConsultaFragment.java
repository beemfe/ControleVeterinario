/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import br.edu.fateczl.controleveterinario.R;
import br.edu.fateczl.controleveterinario.controller.ConsultaController;
import br.edu.fateczl.controleveterinario.controller.AnimalController;
import br.edu.fateczl.controleveterinario.controller.VeterinarioController;
import br.edu.fateczl.controleveterinario.model.Consulta;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import br.edu.fateczl.controleveterinario.adapter.ConsultaAdapter;
import java.util.Date;
import java.util.List;

public class ConsultaFragment extends Fragment {
    private ConsultaController consultaController;
    private AnimalController animalController;
    private VeterinarioController veterinarioController;
    private EditText etAnimalId, etVeterinarioId, etMotivo;
    private Button btnRegistrar, btnListar, btnExcluir;
    private ListView lvConsultas;
    private Consulta consultaSelecionada;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulta, container, false);

        consultaController = new ConsultaController(getContext());
        animalController = new AnimalController(getContext());
        veterinarioController = new VeterinarioController(getContext());

        etAnimalId = view.findViewById(R.id.etAnimalId);
        etVeterinarioId = view.findViewById(R.id.etVeterinarioId);
        etMotivo = view.findViewById(R.id.etMotivo);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnListar = view.findViewById(R.id.btnListar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        lvConsultas = view.findViewById(R.id.lvConsultas);

        btnRegistrar.setOnClickListener(v -> registrarOuAtualizarConsulta());
        btnListar.setOnClickListener(v -> listarConsultas());
        btnExcluir.setOnClickListener(v -> excluirConsulta());

        lvConsultas.setOnItemClickListener((parent, view1, position, id) -> {
            consultaSelecionada = (Consulta) parent.getItemAtPosition(position);
            preencherCamposParaEdicao();
        });

        return view;
    }

    private void registrarOuAtualizarConsulta() {
        try {
            int animalId = Integer.parseInt(etAnimalId.getText().toString().trim());
            int veterinarioId = Integer.parseInt(etVeterinarioId.getText().toString().trim());
            String motivo = etMotivo.getText().toString().trim();

            if (motivo.isEmpty()) {
                throw new IllegalArgumentException("O motivo da consulta deve ser preenchido");
            }

            Animal animal = animalController.buscarPorId(animalId);
            Veterinario veterinario = veterinarioController.buscarPorId(veterinarioId);

            if (animal == null || veterinario == null) {
                throw new IllegalArgumentException("Animal ou Veterinário não encontrado");
            }

            if (consultaSelecionada == null) {
                Consulta novaConsulta = new Consulta(animal, veterinario, new Date(), motivo);
                consultaController.adicionar(novaConsulta);
                Toast.makeText(getContext(), "Consulta registrada com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                consultaSelecionada.setAnimal(animal);
                consultaSelecionada.setVeterinario(veterinario);
                consultaSelecionada.setMotivo(motivo);
                consultaController.atualizar(consultaSelecionada);
                Toast.makeText(getContext(), "Consulta atualizada com sucesso", Toast.LENGTH_SHORT).show();
            }

            limparCampos();
            listarConsultas();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "IDs inválidos", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao registrar consulta: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listarConsultas() {
        try {
            List<Consulta> consultas = consultaController.listarTodos();
            ConsultaAdapter adapter = new ConsultaAdapter(getContext(), consultas);
            lvConsultas.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao listar consultas: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void excluirConsulta() {
        if (consultaSelecionada != null) {
            try {
                consultaController.excluir(consultaSelecionada.getId());
                Toast.makeText(getContext(), "Consulta excluída com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
                listarConsultas();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao excluir consulta: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Selecione uma consulta para excluir", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherCamposParaEdicao() {
        if (consultaSelecionada != null) {
            etAnimalId.setText(String.valueOf(consultaSelecionada.getAnimal().getId()));
            etVeterinarioId.setText(String.valueOf(consultaSelecionada.getVeterinario().getId()));
            etMotivo.setText(consultaSelecionada.getMotivo());
            btnRegistrar.setText("Atualizar");
        }
    }

    private void limparCampos() {
        etAnimalId.setText("");
        etVeterinarioId.setText("");
        etMotivo.setText("");
        consultaSelecionada = null;
        btnRegistrar.setText("Registrar");
    }
}