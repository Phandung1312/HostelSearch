package com.androidexam.stayfinder.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.databinding.ItemsHomesBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<Post> posts;
//    private ArrayList<DogBreed> dogBreedsCopy; //C1

    public PostAdapter(ArrayList<Post> posts){
        this.posts = posts;
//        this.dogBreedsCopy = dogBreeds; //C1
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_homes, parent, false);
        ItemsHomesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_homes,
                parent,
                false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        holder.binding.setPost(posts.get(position));
//        Picasso.get().load(dogBreeds.get(position).getUrl()).into(holder.binding.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    //C1
//    public Filter getFilter(){
//        return new Filter(){
//
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String input = charSequence.toString().toLowerCase();
//                List<DogBreed> filteredDog = new ArrayList<DogBreed>();
//                if(input.isEmpty()){
//                    filteredDog.addAll(dogBreedsCopy);
//                }
//                else{
//                    for(DogBreed dog: dogBreedsCopy){
//                        if(dog.getName().toLowerCase().contains(input)){
//                            filteredDog.add(dog);
//                        }
//                    }
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredDog;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                dogBreeds = new ArrayList<>();
//                dogBreeds.addAll((List)filterResults.values);
//                notifyDataSetChanged();
//            }
//        };
//    }

    //C2
    public void updateList(ArrayList<Post> newList) {
        posts = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemsHomesBinding binding;

        public ViewHolder(@NonNull ItemsHomesBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Post dog = posts.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", posts);
                    Navigation.findNavController(view).navigate(R.id.detailClientHomeFragment, bundle);
                }
            });

//            binding.ivLike.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    DogBreed dogBreed = dogBreeds.get(getAdapterPosition());
//                    dogBreed.setHasLike(!dogBreed.isHasLike());
//                    notifyDataSetChanged();
//                }
//            });


        }
    }

}
