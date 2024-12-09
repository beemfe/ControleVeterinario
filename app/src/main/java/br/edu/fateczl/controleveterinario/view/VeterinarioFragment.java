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
import br.edu.fateczl.controleveterinario.controller.VeterinarioController;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import br.edu.fateczl.controleveterinario.adapter.VeterinarioAdapter;
import java.util.List;

public class VeterinarioFragment extends Fragment {
    private VeterinarioController veterinarioController;
    private EditText etNome, etCrmv, etEspecialidade;
    private Button btnSalvar, btnListar, btnExcluir;
    private ListView lvVeterinarios;
    private Veterinario veterinarioSelecionado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veterinario, container, false);

        veterinarioController = new VeterinarioController(getContext());

        etNome = view.findViewById(R.id.etNome);
        etCrmv = view.findViewById(R.id.etCrmv);
        etEspecialidade = view.findViewById(R.id.etEspecialidade);
        btnSalvar = view.findViewById(R.id.btnSalvar);
        btnListar = view.findViewById(R.id.btnListar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        lvVeterinarios = view.findViewById(R.id.lvVeterinarios);

        btnSalvar.setOnClickListener(v -> salvarOuAtualizarVeterinario());
        btnListar.setOnClickListener(v -> listarVeterinarios());
        btnExcluir.setOnClickListener(v -> excluirVeterinario());

        lvVeterinarios.setOnItemClickListener((parent, view1, position, id) -> {
            veterinarioSelecionado = (Veterinario) parent.getItemAtPosition(position);
            preencherCamposParaEdicao();
        });

        return view;
    }

    private void salvarOuAtualizarVeterinario() {
        try {
            String nome = etNome.getText().toString().trim();
            String crmv = etCrmv.getText().toString().trim();
            String especialidade = etEspecialidade.getText().toString().trim();

            if (nome.isEmpty() || crmv.isEmpty() || especialidade.isEmpty()) {
                throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
            }

            if (veterinarioSelecionado == null) {
                Veterinario novoVeterinario = new Veterinario(nome, crmv, especialidade);
                veterinarioController.adicionar(novoVeterinario);
                Toast.makeText(getContext(), "Veterinário salvo com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                veterinarioSelecionado.setNome(nome);
                veterinarioSelecionado.setCrmv(crmv);
                veterinarioSelecionado.setEspecialidade(especialidade);
                veterinarioController.atualizar(veterinarioSelecionado);
                Toast.makeText(getContext(), "Veterinário atualizado com sucesso", Toast.LENGTH_SHORT).show();
            }

            limparCampos();
            listarVeterinarios();
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao salvar veterinário: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void listarVeterinarios() {
        try {
            List<Veterinario> veterinarios = veterinarioController.listarTodos();
            VeterinarioAdapter adapter = new VeterinarioAdapter(getContext(), veterinarios);
            lvVeterinarios.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro ao listar veterinários: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void excluirVeterinario() {
        if (veterinarioSelecionado != null) {
            try {
                veterinarioController.excluir(veterinarioSelecionado.getId());
                Toast.makeText(getContext(), "Veterinário excluído com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
                listarVeterinarios();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Erro ao excluir veterinário: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), "Selecione um veterinário para excluir", Toast.LENGTH_SHORT).show();
        }
    }

    private void preencherCamposParaEdicao() {
        if (veterinarioSelecionado != null) {
            etNome.setText(veterinarioSelecionado.getNome());
            etCrmv.setText(veterinarioSelecionado.getCrmv());
            etEspecialidade.setText(veterinarioSelecionado.getEspecialidade());
            btnSalvar.setText("Atualizar");
        }
    }

    private void limparCampos() {
        etNome.setText("");
        etCrmv.setText("");
        etEspecialidade.setText("");
        veterinarioSelecionado = null;
        btnSalvar.setText("Salvar");
    }
}