package com.example.cardoso_brach_projectiut.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardoso_brach_projectiut.R;
import com.example.cardoso_brach_projectiut.model.Team;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class AdapterForTeams extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Team> teams;

    public AdapterForTeams(Context context, List<Team> teams) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.teams = teams;
    }

    @Override
    public int getCount() {
        return this.teams.size();
    }

    @Override
    public Object getItem(int position) {
        return this.teams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = this.layoutInflater.inflate(R.layout.team_list_item, parent, false);

        ImageView teamBadge = (ImageView) itemView.findViewById(R.id.team_badge);
        TextView teamName = (TextView) itemView.findViewById(R.id.team_name);
        TextView teamLeague = (TextView) itemView.findViewById(R.id.team_league);

        ImageLoader.getInstance().displayImage(this.teams.get(position).getBadge(), teamBadge);

        teamName.setText(this.teams.get(position).getName());
        teamLeague.setText(this.teams.get(position).getLeague());

        return itemView;
    }
}
