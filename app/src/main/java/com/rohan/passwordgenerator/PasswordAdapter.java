package com.rohan.passwordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.PasswordViewHolder> {
    private final ArrayList<passwordManager> datalist;
    Context context;
    private Mydb data;

    public PasswordAdapter(Context context,ArrayList<passwordManager> datalist) {
        this.context = context;
        this.datalist = new ArrayList<>(datalist);
        this.data  =new Mydb(context);;

    }
    public void setfilteredList(List<passwordManager> newList){
        datalist.clear();
        datalist.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pass,parent,false);
        return new PasswordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        passwordManager item =  datalist.get(position);
        holder.nameText.setText(item.name);
        holder.passText.setText(item.Pass);
        holder.copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password",item.Pass);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(holder.itemView.getContext(), "Password copied to clipboard", Toast.LENGTH_SHORT).show();

            }
        });

        holder.row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("Do you want to delete password");
                builder.setMessage("Do you want to delete "+holder.nameText.getText().toString()+" password");
                builder.setIcon(R.drawable.baseline_delete_24);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datalist.remove(position);
                        notifyItemRemoved(position);
                        notifyItemChanged(position,datalist.size());
                        data.deletePass(item.id);

                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class PasswordViewHolder extends RecyclerView.ViewHolder {
        TextView nameText,passText;
        LinearLayout row;
        ImageView copy;
        public PasswordViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.itm_name);
            passText = itemView.findViewById(R.id.itm_pass);
            copy = itemView.findViewById(R.id.copy);
            row = itemView.findViewById(R.id.row);

        }
    }
}
