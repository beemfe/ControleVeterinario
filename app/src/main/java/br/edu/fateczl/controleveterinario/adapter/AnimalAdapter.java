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
import br.edu.fateczl.controleveterinario.model.Animal;
import java.util.List;

public class AnimalAdapter extends ArrayAdapter<Animal> {
    private Context context;
    private List<Animal> animais;

    public AnimalAdapter(Context context, List<Animal> animais) {
        super(context, 0, animais);
        this.context = context;
        this.animais = animais;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal animal = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_animal, parent, false);
        }

        TextView tvNome = convertView.findViewById(R.id.tv_nome_animal);
        TextView tvEspecie = convertView.findViewById(R.id.tv_especie_animal);
        TextView tvIdade = convertView.findViewById(R.id.tv_idade_animal);

        tvNome.setText(animal.getNome());
        tvEspecie.setText(animal.getEspecie());
        tvIdade.setText(String.valueOf(animal.calcularIdade()) + " anos");

        return convertView;
    }
}