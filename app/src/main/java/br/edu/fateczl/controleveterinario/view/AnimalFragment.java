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
import br.edu.fateczl.controleveterinario.controller.AnimalController;
import br.edu.fateczl.controleveterinario.model.Animal;
import br.edu.fateczl.controleveterinario.adapter.AnimalAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class AnimalFragment extends Fragment {
    private AnimalController animalController;
    private EditText etNome, etEspecie, etRaca, etDataNascimento;
    private Button btnSalvar, btnListar, btnExcluir;
    private ListView lvAnimais;
    private Animal animalSelecionado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_animal, container, false);

        animalController = new AnimalController(getContext());

        etNome = view.findViewById(R.id.etNome);
        etEspecie = view.findViewById(R.id.etEspecie);
        etRaca = view.findViewById(R.id.etRaca);
        etDataNascimento = view.findViewById(R.id.etDataNascimento);
        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnListar = view.findViewById(R.id.btnListar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        lvAnimais = view.findViewById(R.id.lvAnimais);

        btnSalvar.setOnClickListener(v -> salvarOuAtualizarAnimal());
        btnListar.setOnClickListener(v -> listarAnimais());
        btnExcluir.setOnClickListener(v -> excluirAnimal());

        lvAnimais.setOnItemClickListener((parent, view1, position, id) -> {
            animalSelecionado = (Animal) parent.getItemAtPosition(position);
            preencherCamposParaEdicao();
        });

        return view;
    }

    private void salvarOuAtualizarAnimal() {
        try {
            String nome = etNome.getText().toString().trim();
            String especie = etEspecie.getText().toString().trim();
            String raca = etRaca.getText().toString().trim();
            String dataNascimentoStr = etDataNascimento.getText().toString().trim();

            if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || dataNascimentoStr.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date dataNascimento = sdf.parse(dataNascimentoStr);

            if (animalSelecionado == null) {
                Animal novoAnimal = new Animal(nome, dataNascimento, "Não informado", null, especie, raca);
                animalController.adicionar(novoAnimal);
                Toast.makeText(getContext(), "Animal salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                animalSelecionado.setNome(nome);
                animalSelecionado.setEspecie(especie);
                animalSelecionado.setRaca(raca);
                animalSelecionado.setDataNascimento(dataNascimento);
                animalController.atualizar(animalSelecionado);
                Toast.makeText(getContext(), "Animal atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }

            limparCampos();
            listarAnimais();
        } catch (ParseException e) {
            Toast.makeText(getContext(), "Data de nascimento inválida. Use o formato dd/MM/yyyy", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao salvar animal: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void listarAnimais() {
        try {
            List<Animal> animais = animalController.listarTodos();
            AnimalAdapter adapter = new AnimalAdapter(getContext(), animais);
            lvAnimais.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao listar animais: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void excluirAnimal() {
        if (animalSelecionado != null) {
            try {
                animalController.excluir(animalSelecionado.getId());
                Toast.makeText(getContext(), "Animal excluído com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
                listarAnimais();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao excluir animal: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Selecione um animal para excluir", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherCamposParaEdicao() {
        if (animalSelecionado != null) {
            etNome.setText(animalSelecionado.getNome());
            etEspecie.setText(animalSelecionado.getEspecie());
            etRaca.setText(animalSelecionado.getRaca());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            etDataNascimento.setText(sdf.format(animalSelecionado.getDataNascimento()));
            btnSalvar.setText("Atualizar");
        }
    }

    private void limparCampos() {
        etNome.setText("");
        etEspecie.setText("");
        etRaca.setText("");
        etDataNascimento.setText("");
        animalSelecionado = null;
        btnSalvar.setText("Salvar");
    }
}