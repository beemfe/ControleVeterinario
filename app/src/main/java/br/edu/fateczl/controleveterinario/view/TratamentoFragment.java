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
import br.edu.fateczl.controleveterinario.controller.TratamentoController;
import br.edu.fateczl.controleveterinario.controller.AnimalController;
import br.edu.fateczl.controleveterinario.model.Tratamento;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.adapter.TratamentoAdapter;
import java.util.Date;
import java.util.List;

public class TratamentoFragment extends Fragment {
    private TratamentoController tratamentoController;
    private AnimalController animalController;
    private EditText etAnimalId, etDescricao;
    private Button btnRegistrar, btnListar, btnExcluir;
    private ListView lvTratamentos;
    private Tratamento tratamentoSelecionado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tratamento, container, false);

        tratamentoController = new TratamentoController(getContext());
        animalController = new AnimalController(getContext());

        etAnimalId = view.findViewById(R.id.etAnimalId);
        etDescricao = view.findViewById(R.id.etDescricao);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnListar = view.findViewById(R.id.btnListar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        lvTratamentos = view.findViewById(R.id.lvTratamentos);

        btnRegistrar.setOnClickListener(v -> registrarOuAtualizarTratamento());
        btnListar.setOnClickListener(v -> listarTratamentos());
        btnExcluir.setOnClickListener(v -> excluirTratamento());

        lvTratamentos.setOnItemClickListener((parent, view1, position, id) -> {
            tratamentoSelecionado = (Tratamento) parent.getItemAtPosition(position);
            preencherCamposParaEdicao();
        });

        return view;
    }

    private void registrarOuAtualizarTratamento() {
        try {
            int animalId = Integer.parseInt(etAnimalId.getText().toString().trim());
            String descricao = etDescricao.getText().toString().trim();

            if (descricao.isEmpty()) {
                throw new IllegalArgumentException("A descrição do tratamento deve ser preenchida");
            }

            Animal animal = animalController.buscarPorId(animalId);

            if (animal == null) {
                throw new IllegalArgumentException("Animal não encontrado");
            }

            if (tratamentoSelecionado == null) {
                Tratamento novoTratamento = new Tratamento(animal, new Date(), descricao) {
                    @Override
                    public boolean isCompleto() {
                        return false;
                    }
                };
                tratamentoController.adicionar(novoTratamento);
                Toast.makeText(getContext(), "Tratamento registrado com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                tratamentoSelecionado.setAnimal(animal);
                tratamentoSelecionado.setDescricao(descricao);
                tratamentoController.atualizar(tratamentoSelecionado);
                Toast.makeText(getContext(), "Tratamento atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }

            limparCampos();
            listarTratamentos();
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "ID do animal inválido", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao registrar tratamento: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listarTratamentos() {
        try {
            List<Tratamento> tratamentos = tratamentoController.listarTodos();
            TratamentoAdapter adapter = new TratamentoAdapter(getContext(), tratamentos);
            lvTratamentos.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao listar tratamentos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void excluirTratamento() {
        if (tratamentoSelecionado != null) {
            try {
                tratamentoController.excluir(tratamentoSelecionado.getId());
                Toast.makeText(getContext(), "Tratamento excluído com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
                listarTratamentos();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao excluir tratamento: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Selecione um tratamento para excluir", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherCamposParaEdicao() {
        if (tratamentoSelecionado != null) {
            etAnimalId.setText(String.valueOf(tratamentoSelecionado.getAnimal().getId()));
            etDescricao.setText(tratamentoSelecionado.getDescricao());
            btnRegistrar.setText("Atualizar");
        }
    }

    private void limparCampos() {
        etAnimalId.setText("");
        etDescricao.setText("");
        tratamentoSelecionado = null;
        btnRegistrar.setText("Registrar");
    }
}