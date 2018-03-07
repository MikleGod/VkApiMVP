package com.epam.mikle.vkapimvp.views.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.epam.mikle.vkapimvp.R;
import com.epam.mikle.vkapimvp.contracts.RecyclerViewContract;

import java.util.Random;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {




    private RecyclerViewContract.StudentsPresenter presenter;

    public RVAdapter(RecyclerViewContract.StudentsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.students_card_view, viewGroup, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {

        presenter.onBindViewHolder(position, holder, this);
        //holder.personPhoto.setImageResource(students.get(position).photoId);
    }

    @Override
    public int getItemCount() {
        return presenter.getStudentsCount();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder implements RecyclerViewContract.StudentsView{
        View itemView;
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        int id;

        PersonViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.person_name);
            personAge = (TextView) itemView.findViewById(R.id.person_age);
            personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        }

        @Override
        public void setId(int id) {
            this.id = id;
        }

        @Override
        public void setFullName(String fullName) {
            personName.setText(fullName);
        }

        @Override
        public void setVkDomain(String vkDomain) {
            personAge.setText(vkDomain);
        }

        @Override
        public void setContextMenu(View.OnCreateContextMenuListener listener) {
            itemView.setOnCreateContextMenuListener(listener);
        }

        @Override
        public void setRandomNumber(int randomNumber) {
            personAge.setText("Номер: " + randomNumber);
        }

    }
}
