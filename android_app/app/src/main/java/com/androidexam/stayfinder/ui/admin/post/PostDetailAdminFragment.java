package com.androidexam.stayfinder.ui.admin.post;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.androidexam.stayfinder.data.models.request.CommentRequest;
import com.androidexam.stayfinder.databinding.PostDetailAdminClass;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostDetailAdminFragment extends BaseFragment<PostDetailAdminClass> {
    private Hostel hostel;
    PostDetailAdminViewModel postDetailAdminViewModel;
    private ArrayList<Comment> comments;
    CommentAdapter adapter;
    private final String[] Options = {"Take photo", "Choose photo"};
    private Bitmap bitmapAvatar;
    private AlertDialog.Builder window;

    public PostDetailAdminFragment() {
        super(PostDetailAdminClass::inflate);
    }
    @Override
    public void initView() {
        postDetailAdminViewModel = new ViewModelProvider(this).get(PostDetailAdminViewModel.class);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
        hostel =(Hostel)getArguments().getSerializable("hostel");
        dataBinding.rvComment.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
    }
    @Override
    public void initListeners() {
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
                commentRequest.setUsername(hostel.getPost().getAccount().getAccountName());
                commentRequest.setContent(dataBinding.etComment.getText().toString());
                if(bitmapAvatar != null){
                    try {
                        FileOutputStream out = new FileOutputStream(commentRequest.getFile());
                        bitmapAvatar.compress(Bitmap.CompressFormat.JPEG, 100, out); // Chọn định dạng ảnh và độ nén
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                postDetailAdminViewModel.sendComment(commentRequest);
                dataBinding.etComment.setText("");
            }
        });
    }
    @Override
    public void initData() {
        dataBinding.setHostel(hostel);
        dataBinding.executePendingBindings();
        setAdapter();
    }
    private void setAdapter(){
        comments = new ArrayList<>();
        adapter = new CommentAdapter(comments, mainActivity.account,
                postDetailAdminViewModel,
                getViewLifecycleOwner());
        dataBinding.rvComment.setAdapter(adapter);
        postDetailAdminViewModel.setCommentData(hostel.getPost().getId());
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
//                    binding.ivAvatar.setImageURI(uri);
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
        window.setItems(Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
