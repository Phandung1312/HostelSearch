package com.androidexam.stayfinder.ui.profile;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.PostRequest;
import com.androidexam.stayfinder.data.repositories.PostReposity;
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
    private ArrayList<PostRequest> waitApprovalPosts;
    private ArrayList<PostRequest> approvedPosts;
    private ArrayList<PostRequest> notApprovedPosts;
    private PostAdapter postAdapter;
    ProfileViewModel profileViewModel;

    public ProfileFragment() {
        super(ProfileClass::inflate);
    }

    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
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
        // if (Paper.book().read("email") != null && Paper.book().read("password") !=
        // null){
        // check is new login???
        profileViewModel.GetWaitApprovalPost(mainActivity.account.getAccountName()).observe(getViewLifecycleOwner(), waitLst -> {
            dataBinding.btnWaitApprovalPost.setText(String.format("Bài chờ\nphê duyệt (%d)",waitLst.size()));
            waitApprovalPosts.clear();
            waitApprovalPosts.addAll(waitLst);
            postAdapter.notifyDataSetChanged();
        });
        profileViewModel.GetApprovedPost(mainActivity.account.getAccountName()).observe(getViewLifecycleOwner(), approvedLst -> {
            dataBinding.tvNumberPosts.setText(String.valueOf(approvedLst.size()));
            dataBinding.btnApprovedPost.setText(String.format("Bài được\nchấp nhận (%d)",approvedLst.size()));
            approvedPosts.clear();
            approvedPosts.addAll(approvedLst);
            postAdapter.notifyDataSetChanged();
        });
        profileViewModel.GetNotApprovedPost(mainActivity.account.getAccountName()).observe(getViewLifecycleOwner(), notApprovedLst -> {
            dataBinding.btnNotApprovedPost.setText(String.format("Bài không\nchấp nhận (%d)",notApprovedLst.size()));
            notApprovedPosts.clear();
            notApprovedPosts.addAll(notApprovedLst);
            postAdapter.notifyDataSetChanged();
        });

        profileViewModel.GetAllScheduleByAccountName(mainActivity.account.getAccountName());
        profileViewModel.loadListOwnerSchedule().observe(getViewLifecycleOwner(), ownerList -> {
            profileViewModel.loadListRenterSchedule().observe(getViewLifecycleOwner(), renterList ->{
                dataBinding.tvNumberSchedules.setText(String.valueOf(ownerList.size() + renterList.size()));
            });
        });
        // }
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
