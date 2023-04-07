package com.androidexam.stayfinder.ui.profile;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.databinding.ProfileClass;

import java.util.ArrayList;

import io.paperdb.Paper;

public class ProfileFragment extends BaseFragment<ProfileClass> {
    private ArrayList<Post> waitApprovalPosts;
    private ArrayList<Post> approvedPosts;
    private PostAdapter postAdapter;
    private ProfileViewModel profileViewModel;

    public ProfileFragment() {
        super(ProfileClass::inflate);
    }
    @Override
    public void onStart() {
        super.onStart();
//        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
            //check is new login???
            profileViewModel.GetAllPostByAccountName(mainActivity.account.getAccountName());
            profileViewModel.loadListPost().observe(getViewLifecycleOwner(),lst ->{
                dataBinding.tvNumberPosts.setText(lst.size());
                for(Post post : lst){
                    waitApprovalPosts.clear();
                    approvedPosts.clear();
                    if(post.getStatus() == 2){
                        waitApprovalPosts.add(post);
                    }
                    else if(post.getStatus() == 1){
                        approvedPosts.add(post);
                    }
                }
            });
            profileViewModel.GetAllScheduleByAccountName(mainActivity.account.getAccountName());
            profileViewModel.loadListSchedule().observe(getViewLifecycleOwner(),lst ->{
                dataBinding.tvNumberSchedules.setText(lst.size());
            });
//        }
    }
    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvProfilePost.setLayoutManager(linearLayoutManager);

        waitApprovalPosts = new ArrayList<>();
        postAdapter = new PostAdapter(waitApprovalPosts);
        dataBinding.rvProfilePost.setAdapter(postAdapter);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public void initListeners() {
        dataBinding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment());
            }
        });
        dataBinding.imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).navigate(ProfileFragmentDirections.actionProfileFragmentToScheduleFragment());
            }
        });
        dataBinding.btnWaitApprovalPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAdapter.updateList(waitApprovalPosts);
            }
        });
        dataBinding.btnApprovedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAdapter.updateList(approvedPosts);
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }

}
