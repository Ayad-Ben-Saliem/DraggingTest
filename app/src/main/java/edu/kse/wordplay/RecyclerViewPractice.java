package edu.kse.wordplay;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPractice extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_practice);

        recyclerView = findViewById(R.id.recyclerView);

        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal("جمل", R.drawable.camle));
        animals.add(new Animal("باندا", R.drawable.banda));
        animals.add(new Animal("أسد", R.drawable.lion));
        animals.add(new Animal("سلحفاة", R.drawable.tortoise));

        MyAdapter adapter = new MyAdapter(animals);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyAdapter extends RecyclerView.Adapter{

        final List<Animal> animals;

        MyAdapter(List<Animal> animals){
            this.animals = animals;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.animals, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder)holder).setData(animals.get(position));
        }

        @Override
        public int getItemCount() {
            return animals.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        AppCompatTextView animalTV;
        AppCompatImageView animalIV;

        MyViewHolder(View itemView) {
            super(itemView);

            animalTV = itemView.findViewById(R.id.animalName);
            animalIV = itemView.findViewById(R.id.animalImage);
        }

        public void setData(Animal animal){
            animalTV.setText(animal.getAnimalName());
            animalIV.setImageResource(animal.getAnimalImageRes());
        }
    }

    class Animal {

        private final String animalName;
        private final int animalImageRes;

        Animal(@NonNull String animalName, @DrawableRes int animalImageRes) {
            this.animalImageRes = animalImageRes;
            this.animalName = animalName;
        }

        @NonNull
        public String getAnimalName() {
            return animalName;
        }

        @DrawableRes
        public int getAnimalImageRes() {
            return animalImageRes;
        }
    }
}
