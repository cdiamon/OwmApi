package ru.yksoft.padmitriy.owmapi.model.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.yksoft.padmitriy.owmapi.R;
import ru.yksoft.padmitriy.owmapi.model.RectangTownListResponse;
import ru.yksoft.padmitriy.owmapi.model.Utils;
import ru.yksoft.padmitriy.owmapi.view.MainActivity;

/**
 * Created by padmitriy on 07.08.17.
 * <p>
 * здесь отображаем список и удаляем элементы
 */

public class TownAdapter extends RecyclerView.Adapter<TownAdapter.ViewHolder> {

    private List<RectangTownListResponse.TList> townList;

    private Context context;

    public List<RectangTownListResponse.TList> getTownList() {
        return townList;
    }

    public void setTownList(List<RectangTownListResponse.TList> townList) {
        this.townList = townList;
    }

    public TownAdapter(Context context) {
        this.context = context;
        townList = new ArrayList<>();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardTownView;
        TextView textTownName;
        TextView textTownTemperature;
        TextView textTownWindspeed;
        TextView textTownWinddirection;
        TextView textTownRemove;

        int position;

        ViewHolder(final View itemView) {
            super(itemView);

            cardTownView = itemView.findViewById(R.id.card_town_view);
            textTownName = itemView.findViewById(R.id.text_town_name);
            textTownTemperature = itemView.findViewById(R.id.text_town_temperature);
            textTownWindspeed = itemView.findViewById(R.id.text_town_windspeed);
            textTownWinddirection = itemView.findViewById(R.id.text_town_winddirection);
            textTownRemove = itemView.findViewById(R.id.text_town_remove);

        }
    }

    @Override
    public TownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_town_card, parent, false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final TownAdapter.ViewHolder holder, final int position) {

        holder.position = position;

        holder.textTownName.setText(townList.get(position).getName() + " City");
        holder.textTownTemperature.setText("Средняя температура " + (((townList.get(position).getMain().getTempMax().longValue() + townList.get(position).getMain().getTempMin().longValue()) / 2)) + "\u2103");
        holder.textTownWindspeed.setText("Скорость ветра " + (townList.get(position).getWind().getSpeed()).longValue() + " м/с");
        holder.textTownWinddirection.setText("Направление ветра " + (Utils.degreesToDirections(townList.get(position).getWind().getDeg())));

        holder.textTownRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                townList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return townList.size();
    }
}
