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
import br.edu.fateczl.controleveterinario.model.Tratamento;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TratamentoAdapter extends ArrayAdapter<Tratamento> {
    private Context context;
    private List<Tratamento> tratamentos;
    private SimpleDateFormat dateFormat;

    public TratamentoAdapter(Context context, List<Tratamento> tratamentos) {
        super(context, 0, tratamentos);
        this.context = context;
        this.tratamentos = tratamentos;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tratamento tratamento = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tratamento, parent, false);
        }

        TextView tvAnimal = convertView.findViewById(R.id.tv_nome_animal_tratamento);
        TextView tvDataInicio = convertView.findViewById(R.id.tv_data_inicio_tratamento);
        TextView tvDescricao = convertView.findViewById(R.id.tv_descricao_tratamento);
        TextView tvStatus = convertView.findViewById(R.id.tv_status_tratamento);

        tvAnimal.setText(tratamento.getAnimal().getNome());
        tvDataInicio.setText("Início: " + dateFormat.format(tratamento.getDataInicio()));
        tvDescricao.setText(tratamento.getDescricao());
        tvStatus.setText(tratamento.isCompleto() ? "Concluído" : "Em andamento");

        return convertView;
    }
}