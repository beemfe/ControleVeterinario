/*
 * @author: Felipe Bernardes Cisilo
 */
package br.edu.fateczl.controleveterinario.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.edu.fateczl.controleveterinario.R;
import br.edu.fateczl.controleveterinario.model.Veterinario;
import java.util.List;

public class VeterinarioAdapter extends ArrayAdapter<Veterinario> {
    private Context context;
    private List<Veterinario> veterinarios;

    public VeterinarioAdapter(Context context, List<Veterinario> veterinarios) {
        super(context, 0, veterinarios);
        this.context = context;
        this.veterinarios = veterinarios;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Veterinario veterinario = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_veterinario, parent, false);
        }

        TextView tvNome = convertView.findViewById(R.id.tvNome);
        TextView tvCrmv = convertView.findViewById(R.id.tvCrmv);
        TextView tvEspecialidade = convertView.findViewById(R.id.tvEspecialidade);

        tvNome.setText(veterinario.getNome());
        tvCrmv.setText("CRMV: " + veterinario.getCrmv());
        tvEspecialidade.setText(veterinario.getEspecialidade());

        return convertView;
    }
}
