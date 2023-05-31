package com.androidexam.stayfinder.ui.client.addpost;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.AddPostClientClass;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddPostClientFragment extends BaseFragment<AddPostClientClass> {
    private final String[] Options = {"Take photo", "Choose photo"};
    AddPostClientViewModel addPostClientViewModel;
    ImageAdapter adapter;
    private ArrayList<Bitmap> bitmaps;
    private Bitmap bitmapAvatar;
    private AlertDialog.Builder window;

    public AddPostClientFragment() {
        super(AddPostClientClass::inflate);
    }
    private int roomTypeId = 2;

    @Override
    public void initView() {
        addPostClientViewModel = new ViewModelProvider(this).get(AddPostClientViewModel.class);
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
        dataBinding.rvImage.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void initListeners() {
        dataBinding.btnAddImage.setOnClickListener(view -> {
            try {
                popupDialog();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });
        dataBinding.btnAddCapacity.setOnClickListener(v -> {
            int capacity = Integer.parseInt(dataBinding.etCapacity.getText().toString());
            dataBinding.etCapacity.setText(String.valueOf(++capacity));
        });
        dataBinding.btnSubCapacity.setOnClickListener(v -> {
            int capacity = Integer.parseInt(dataBinding.etCapacity.getText().toString());
            dataBinding.etCapacity.setText(String.valueOf(--capacity));
        });
        dataBinding.btnBack.setOnClickListener(view -> {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
            bottomNavigationView.setVisibility(View.VISIBLE);
            Navigation.findNavController(dataBinding.getRoot()).popBackStack();
        });
        dataBinding.rgRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (dataBinding.rbChungCu.isChecked()) {
                    roomTypeId = 1;
                } else if (dataBinding.rbKtx.isChecked()) {
                    roomTypeId = 3;
                } else {
                    roomTypeId = 2;
                }
            }
        });
        dataBinding.btnAddPost.setOnClickListener(view -> {
            if (!dataBinding.etTitle.getText().toString().equals("")
                    && !dataBinding.etName.getText().toString().equals("")
                    && !dataBinding.etAddress.getText().toString().equals("")
                    && !dataBinding.etArea.getText().toString().equals("")
                    && !dataBinding.etCapacity.getText().toString().equals("")
                    && !dataBinding.etRentPrice.getText().toString().equals("")
                    && !dataBinding.etElectricPrice.getText().toString().equals("")
                    && !dataBinding.etDepositPrice.getText().toString().equals("")
                    && !dataBinding.etWaterPrice.getText().toString().equals("")) {
                String title = dataBinding.etTitle.getText().toString();
                String name = dataBinding.etName.getText().toString();
                String address = dataBinding.etAddress.getText().toString();
                double area = Double.parseDouble(dataBinding.etArea.getText().toString());
                int capacity = Integer.parseInt(dataBinding.etCapacity.getText().toString());
                double rentPrice = Double.parseDouble(dataBinding.etRentPrice.getText().toString());
                double electricPrice = Double.parseDouble(dataBinding.etElectricPrice.getText().toString());
                double depositPrice = Double.parseDouble(dataBinding.etDepositPrice.getText().toString());
                double waterPrice = Double.parseDouble(dataBinding.etWaterPrice.getText().toString());
                String description = dataBinding.etDescription.getText().toString();
                String content = dataBinding.etContent.getText().toString();
                int status = 1;
                int accountId = mainActivity.account.getId();
                addPostClientViewModel.addHostelAndPost(name, capacity, area, address, rentPrice, depositPrice, status, description, roomTypeId, electricPrice, waterPrice,accountId, title, content)
                        .observe(getViewLifecycleOwner(), data -> {
                            if (data != null) {
                                ArrayList<File> files = new ArrayList<>();
                                for(Bitmap bitmap : bitmaps){
                                    File file = new File(getContext().getCacheDir(), "image.jpg");
                                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
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
                                    files.add(file);
                                }
                                addPostClientViewModel.addImages(data.getId(),files)
                                        .observe(getViewLifecycleOwner(),dataImage->{
                                            if(dataImage != null){
                                                Toast.makeText(this.getContext().getApplicationContext(), "Add post success", Toast.LENGTH_SHORT).show();
                                                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
                                                bottomNavigationView.setVisibility(View.VISIBLE);
                                                Navigation.findNavController(dataBinding.getRoot()).popBackStack();
                                            }else{
                                                Toast.makeText(this.getContext().getApplicationContext(), "Add post image failure", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                Toast.makeText(this.getContext().getApplicationContext(), "Add post failure", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this.getContext().getApplicationContext(), "Nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        setAdapter();
    }

    private void setAdapter() {
        bitmaps = new ArrayList<>();
        adapter = new ImageAdapter(bitmaps);
        dataBinding.rvImage.setAdapter(adapter);
    }

    private void popupDialog() {
        window = new AlertDialog.Builder(this.getContext());
        window.setTitle("Change photo");
        window.setItems(Options, (dialog, which) -> {
            if (which == 0) {
                //first option clicked, do this...
                Intent cam = getCamera();
                launcher.launch(cam);
            } else if (which == 1) {
                //second option clicked, do this...
                Intent cam = getGallery();
                launcher.launch(cam);
            } else {
                //theres an error in what was selected
                Toast.makeText(getActivity().getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
            }
        });

        window.show();
    }
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    // Use the uri to load the image
                    try {
                        bitmapAvatar = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), uri);
                        bitmaps.add(bitmapAvatar);
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    Log.e(TAG, "camera: error occurred");
                }
            });

    public Intent getCamera() {
        return ImagePicker.with(this.mainActivity)
                .cameraOnly()    //User can only capture image using Camera
                .createIntent();
    }

    public Intent getGallery() {
        return ImagePicker.with(this.mainActivity)
                .galleryOnly()    //User can only select image from Gallery
                .createIntent();
    }
}
