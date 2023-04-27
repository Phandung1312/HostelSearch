package com.androidexam.stayfinder.ui.admin.post;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.CommentRequest;
import com.androidexam.stayfinder.databinding.PostDetailAdminClass;
import com.androidexam.stayfinder.ui.chat.listchat.ListChatFragmentDirections;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostDetailAdminFragment extends BaseFragment<PostDetailAdminClass> {
    private int hostelId;
    private int postId;
    private Hostel hostel;
    PostDetailAdminViewModel postDetailAdminViewModel;
    private ArrayList<Comment> comments;
    CommentAdapter adapter;
    private final String[] Options = {"Take photo", "Choose photo"};
    private Bitmap bitmapAvatar;
    private AlertDialog.Builder window;
    private boolean checkFavourite;
    private String url;

    public PostDetailAdminFragment() {
        super(PostDetailAdminClass::inflate);
    }
    @Override
    public void initView() {
        postDetailAdminViewModel = new ViewModelProvider(this).get(PostDetailAdminViewModel.class);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
        if(getArguments() != null){
            postId = (int)getArguments().getSerializable("postId");
            hostelId =(int)getArguments().getSerializable("hostelId");
        }
        dataBinding.rvComment.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        if(mainActivity.account.getPosition().getId() == 1){
            dataBinding.btnAccept.setVisibility(View.GONE);
            dataBinding.btnRemove.setVisibility(View.GONE);
        }
    }
    @Override
    public void initListeners() {
        dataBinding.btnChat.setOnClickListener(view ->{
            String email = hostel.getPost().getAccount().getAccountName();
            postDetailAdminViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(),userFirebase -> {
                Navigation.findNavController(dataBinding.getRoot()).navigate(PostDetailAdminFragmentDirections
                        .actionPostDetailAdminFragmentToChatDetailFragment(userFirebase.getId()));
            });
        });
        dataBinding.btnBack.setOnClickListener(view ->{
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
            bottomNavigationView.setVisibility(View.VISIBLE);
            Navigation.findNavController(dataBinding.getRoot()).popBackStack();
        });
        dataBinding.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    popupDialog();
                    dataBinding.cardViewImageComment.setVisibility(View.VISIBLE);
                } catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
        dataBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentRequest commentRequest = new CommentRequest();
                commentRequest.setPostId(hostel.getPost().getId());
                commentRequest.setUsername(mainActivity.account.getAccountName());
                commentRequest.setContent(dataBinding.etComment.getText().toString());
                if(bitmapAvatar != null){
                    try {
                        File file = new File(getContext().getCacheDir(), "image.jpg");
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        bitmapAvatar.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        byte[] bitmapData = outputStream.toByteArray();
                        FileOutputStream fileOutputStream = null;
                        try {
                            fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(bitmapData);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        commentRequest.setFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                postDetailAdminViewModel.sendComment(commentRequest)
                        .observe(getViewLifecycleOwner(),comment->{
                            if(comment == null){
                                Toast.makeText(v.getContext().getApplicationContext(), "Add comment failure", Toast.LENGTH_SHORT).show();
                            }else{
                                comments.add(comment);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(v.getContext().getApplicationContext(), "Add comment success", Toast.LENGTH_SHORT).show();
                                dataBinding.cardViewImageComment.setVisibility(View.GONE);
                                dataBinding.etComment.setText("");
                            }
                        } );
            }
        });
        dataBinding.btnRemove.setOnClickListener(v->{
            postDetailAdminViewModel.changeStatusPost(hostel.getPost().getId(),0)
                    .observe(getViewLifecycleOwner(), check ->{
                        if(check == true){
                            Toast.makeText(v.getContext().getApplicationContext(), "Change status post success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(v.getContext().getApplicationContext(), "Change status post failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dataBinding.btnAccept.setOnClickListener(v->{
            postDetailAdminViewModel.changeStatusPost(hostel.getPost().getId(),1)
                    .observe(getViewLifecycleOwner(), check ->{
                        if(check == true){
                            Toast.makeText(v.getContext().getApplicationContext(), "Change status post success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(v.getContext().getApplicationContext(), "Change status post failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dataBinding.btnFavorite.setOnClickListener(v->{
            postDetailAdminViewModel.changeStatusFavourite(mainActivity.account.getAccountName(),hostel.getPost().getId(),!checkFavourite)
                    .observe(getViewLifecycleOwner(), check ->{
                        if(check){
                            if(checkFavourite){
                                dataBinding.btnFavorite.setImageResource(R.drawable.ic_favourite_gray);
                                checkFavourite = false;
                            }else {
                                dataBinding.btnFavorite.setImageResource(R.drawable.ic_favourite_red);
                                checkFavourite = true;
                            }
                            Toast.makeText(v.getContext().getApplicationContext(), "Change favourite success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(v.getContext().getApplicationContext(), "Change favourite post failure", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        dataBinding.ivPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dataBinding.ivPost2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public void initData() {
        dataBinding.executePendingBindings();
        postDetailAdminViewModel.getHostelById(hostelId)
                .observe(getViewLifecycleOwner(),data->{
                    hostel = new Hostel(data);
                    dataBinding.setHostel(hostel);
                });

        setAdapter();
        postDetailAdminViewModel.checkFavourite(mainActivity.account.getAccountName(),postId)
                .observe(getViewLifecycleOwner(), check->{
                    if(check){
                        dataBinding.btnFavorite.setImageResource(R.drawable.ic_favourite_red);
                        checkFavourite = true;
                    }else {
                        dataBinding.btnFavorite.setImageResource(R.drawable.ic_favourite_gray);
                        checkFavourite = false;
                    }
                });
    }
    private void setAdapter(){
        comments = new ArrayList<>();
        adapter = new CommentAdapter(comments, mainActivity.account,
                postDetailAdminViewModel,
                getViewLifecycleOwner());
        dataBinding.rvComment.setAdapter(adapter);
        postDetailAdminViewModel.setCommentData(postId);
        postDetailAdminViewModel.loadComment().observe(getViewLifecycleOwner(),commentList ->{
            comments.addAll(commentList);
            adapter.notifyDataSetChanged();
        });
    }

    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();

                    // Use the uri to load the image
                    try {
                        bitmapAvatar = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), uri);
                        dataBinding.ivComment.setImageBitmap(bitmapAvatar);
                    } catch (IOException e) {
                        Log.e(TAG,e.getMessage());
                    }
                    dataBinding.ivComment.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    Log.e(TAG,"camera: error occurred");
                }
            });

    private void popupDialog(){
        window = new AlertDialog.Builder(this.getContext());
        window.setTitle("Change photo");
        window.setItems(Options, (dialog, which) -> {
            if(which == 0){
                //first option clicked, do this...
                Intent cam = getCamera();
                launcher.launch(cam);
            }else if(which == 1){
                //second option clicked, do this...
                Intent cam = getGallery();
                launcher.launch(cam);
            }else{
                //theres an error in what was selected
                Toast.makeText(getActivity().getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
            }
        });

        window.show();
    }
    public Intent getCamera(){
        return ImagePicker.with(this.mainActivity)
                .cameraOnly()	//User can only capture image using Camera
                .createIntent();
    }
    public Intent getGallery(){
        return ImagePicker.with(this.mainActivity)
                .galleryOnly()	//User can only select image from Gallery
                .createIntent();
    }
}
