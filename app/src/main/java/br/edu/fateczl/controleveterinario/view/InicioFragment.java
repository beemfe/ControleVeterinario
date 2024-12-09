/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import br.edu.fateczl.controleveterinario.R;

public class InicioFragment extends Fragment {
    private Button btnAnimais, btnConsultas, btnVeterinarios, btnTratamentos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        btnAnimais = view.findViewById(R.id.btnAnimais);
        btnConsultas = view.findViewById(R.id.btnConsultas);
        btnVeterinarios = view.findViewById(R.id.btnVeterinarios);
        btnTratamentos = view.findViewById(R.id.btnTratamentos);

        btnAnimais.setOnClickListener(v -> navegarParaFragment(new AnimalFragment()));
        btnConsultas.setOnClickListener(v -> navegarParaFragment(new ConsultaFragment()));
        btnVeterinarios.setOnClickListener(v -> navegarParaFragment(new VeterinarioFragment()));
        btnTratamentos.setOnClickListener(v -> navegarParaFragment(new TratamentoFragment()));

        return view;
    }

    private void navegarParaFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}