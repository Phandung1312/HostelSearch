package com.androidexam.stayfinder.ui.profile;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.databinding.ProfileClass;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.Provides;
import dagger.hilt.android.AndroidEntryPoint;
import io.paperdb.Paper;

@AndroidEntryPoint
public class ProfileFragment extends BaseFragment<ProfileClass> {
    private ArrayList<Post> waitApprovalPosts;
    private ArrayList<Post> approvedPosts;
    private ArrayList<Post> notApprovedPosts;
    private PostAdapter postAdapter;
    ProfileViewModel profileViewModel;

    public ProfileFragment() {
        super(ProfileClass::inflate);
    }

    @Override
    public void onStart() {
        super.onStart();
        // if (Paper.book().read("email") != null && Paper.book().read("password") !=
        // null){
        // check is new login???
        profileViewModel.GetAllPostByAccountName(mainActivity.account.getAccountName());
        profileViewModel.loadListPost().observe(getViewLifecycleOwner(), lst -> {
            waitApprovalPosts.clear();
            approvedPosts.clear();
            notApprovedPosts.clear();
            for (Post post : lst) {
                if (post.getStatus() == 2) {
                    waitApprovalPosts.add(post);

                } else if (post.getStatus() == 1) {
                    approvedPosts.add(post);

                } else{
                    notApprovedPosts.add(post);

                }
            }
            dataBinding.tvNumberPosts.setText(String.valueOf(approvedPosts.size()));
            dataBinding.btnWaitApprovalPost.setText(String.format("Wait-approval\npost (%d)",waitApprovalPosts.size()));
            dataBinding.btnApprovedPost.setText(String.format("Approved\npost (%d)",approvedPosts.size()));
            dataBinding.btnNotApprovedPost.setText(String.format("Not approved\npost (%d)",notApprovedPosts.size()));
            postAdapter.notifyDataSetChanged();
        });
        profileViewModel.GetAllScheduleByAccountName(mainActivity.account.getAccountName());
        profileViewModel.loadListOwnerSchedule().observe(getViewLifecycleOwner(), ownerList -> {
            profileViewModel.loadListRenterSchedule().observe(getViewLifecycleOwner(), renterList ->{
                dataBinding.tvNumberSchedules.setText(String.valueOf(ownerList.size() + renterList.size()));
            });
        });
        // }
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        dataBinding.rvProfilePost.setLayoutManager(linearLayoutManager);

        waitApprovalPosts = new ArrayList<>();
        approvedPosts = new ArrayList<>();
        notApprovedPosts = new ArrayList<>();

        postAdapter = new PostAdapter(waitApprovalPosts);
        dataBinding.rvProfilePost.setAdapter(postAdapter);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    public void initListeners() {
        dataBinding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot())
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment());
            }
        });
        dataBinding.imgSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot())
                        .navigate(ProfileFragmentDirections.actionProfileFragmentToScheduleFragment());
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
        dataBinding.btnNotApprovedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postAdapter.updateList(notApprovedPosts);
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }

}
