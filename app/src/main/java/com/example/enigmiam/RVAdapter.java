package com.example.enigmiam;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.service.controls.ControlsProviderService.TAG;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    public List liste;
    int size;
    Context context;
    Critique critique;



    onLongItemClickListener mOnLongItemClickListener;

    public void setOnLongItemClickListener(onLongItemClickListener onLongItemClickListener) {
        mOnLongItemClickListener = onLongItemClickListener;
    }

    public interface onLongItemClickListener {
        void ItemLongClicked(View v, int position);
    }


    public RVAdapter(Context ctx, List listCritique){
     liste= listCritique;

    context=ctx;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.critiques_layout,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        critique =(Critique) liste.get(position);
        holder.txtNom.setText(critique.nomResto);
        holder.txtCritique.setText(critique.critique);
        holder.txtNoteDeco.setText(critique.noteDeco);
        holder.txtNoteService.setText(critique.noteService);
        holder.txtId.setText(Integer.toString(critique.idCritique));
        holder.txtDate.setText(critique.dateEtHeure);



    }

    @Override
    public int getItemCount() {
       return liste.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNom, txtDate, txtNoteService, txtNoteDeco, txtCritique,txtId;
        ImageButton btnDelete, btnShare, btnUpdate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNom = itemView.findViewById(R.id.txtDispNomRestaurant);
            txtDate = itemView.findViewById(R.id.txtDispDate);
            txtNoteService = itemView.findViewById(R.id.NoteService);
            txtNoteDeco = itemView.findViewById(R.id.NoteDeco);
            txtCritique= itemView.findViewById(R.id.txtDispDescription);
            txtId = itemView.findViewById(R.id.dispId);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnEdit);
            MyDataBaseHelper db = new MyDataBaseHelper(context);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: "+txtId.getText());
                    db.deleteCritique(txtId.getText().toString());
                    Intent intent =new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    critique = db.getCritique(Integer.parseInt(txtId.getText().toString()));
                    Intent intent = new Intent(context, AddRestaurants.class);
                    intent.putExtra("critique",critique);
                    context.startActivity(intent);
                }
            });

        }


    }
}
