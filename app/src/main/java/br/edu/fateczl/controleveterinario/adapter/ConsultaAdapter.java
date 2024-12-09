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
import br.edu.fateczl.controleveterinario.model.Consulta;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ConsultaAdapter extends ArrayAdapter<Consulta> {
    private Context context;
    private List<Consulta> consultas;
    private SimpleDateFormat dateFormat;

    public ConsultaAdapter(Context context, List<Consulta> consultas) {
        super(context, 0, consultas);
        this.context = context;
        this.consultas = consultas;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Consulta consulta = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_consulta, parent, false);
        }

        TextView tvData = convertView.findViewById(R.id.tv_data_consulta);
        TextView tvAnimal = convertView.findViewById(R.id.tv_nome_animal_consulta);
        TextView tvVeterinario = convertView.findViewById(R.id.tv_nome_veterinario_consulta);
        TextView tvMotivo = convertView.findViewById(R.id.tv_motivo_consulta);

        tvData.setText(dateFormat.format(consulta.getDataConsulta()));
        tvAnimal.setText(consulta.getAnimal().getNome());
        tvVeterinario.setText(consulta.getVeterinario().getNome());
        tvMotivo.setText(consulta.getMotivo());

        return convertView;
    }
}