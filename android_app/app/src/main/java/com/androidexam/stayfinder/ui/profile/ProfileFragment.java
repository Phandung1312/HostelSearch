package com.androidexam.stayfinder.ui.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.activities.MainActivity;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.base.fragment.Inflate;
import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.databinding.ItemsUserBinding;
import com.androidexam.stayfinder.databinding.ProfileClass;
import com.androidexam.stayfinder.ui.chat.ListChatFragment;
import com.androidexam.stayfinder.ui.chat.ListChatFragmentDirections;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class ProfileFragment extends BaseFragment<ProfileClass> {
    public ProfileFragment() {
        super(ProfileClass::inflate);
    }
    private ArrayList<Post> waitApprovalPosts;
    private ArrayList<Post> approvedPosts;
    private PostAdapter postAdapter;
    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvProfilePost.setLayoutManager(linearLayoutManager);

        waitApprovalPosts = new ArrayList<>();
        postAdapter = new PostAdapter(waitApprovalPosts);
        dataBinding.rvProfilePost.setAdapter(postAdapter);
    }

    @Override
    public void initListeners() {
        dataBinding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).navigate(ProfileFragmentDirections.actionProfileFragmentToSettingFragment());
            }
        });
        dataBinding.btnWaitApprovalPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load list post
                postAdapter.updateList(waitApprovalPosts);
            }
        });
        dataBinding.btnApprovedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //load list post
                postAdapter.updateList(approvedPosts);
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
        //get list post for adapter
    }

}
