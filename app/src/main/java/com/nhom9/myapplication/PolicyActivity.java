package com.nhom9.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nhom9.fragment.ChildrenPolicyFragment;
import com.nhom9.fragment.ContactFragment;
import com.nhom9.fragment.InfoCollectionFragment;
import com.nhom9.fragment.RightsFragment;
import com.nhom9.fragment.ShareInfoFragment;
import com.nhom9.fragment.StorageFragment;
import com.nhom9.fragment.UpdateFragment;
import com.nhom9.fragment.UsagePurposeFragment;
import com.nhom9.myapplication.databinding.ActivityPolicyBinding;

public class PolicyActivity extends AppCompatActivity {
    ActivityPolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imvInfor.setOnClickListener(v -> openFragment(new InfoCollectionFragment()));
        binding.imvPurpose.setOnClickListener(v -> openFragment(new UsagePurposeFragment()));
        binding.imvShare.setOnClickListener(v -> openFragment(new ShareInfoFragment()));
        binding.imvStore.setOnClickListener(v -> openFragment(new StorageFragment()));
        binding.imvRights.setOnClickListener(v -> openFragment(new RightsFragment()));
        binding.imvChild.setOnClickListener(v -> openFragment(new ChildrenPolicyFragment()));
        binding.imvUpdate.setOnClickListener(v -> openFragment(new UpdateFragment()));
        binding.imvContact.setOnClickListener(v -> openFragment(new ContactFragment()));
        binding.imvBack.setOnClickListener(v -> onBackPressed());
    }

    private void openFragment(Fragment fragment) {
        binding.defaultContent.setVisibility(View.GONE);
        binding.listButtons.setVisibility(View.GONE);


        binding.fragmentContainer.setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();

            binding.defaultContent.setVisibility(View.VISIBLE);
            binding.listButtons.setVisibility(View.VISIBLE);
            binding.fragmentContainer.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
