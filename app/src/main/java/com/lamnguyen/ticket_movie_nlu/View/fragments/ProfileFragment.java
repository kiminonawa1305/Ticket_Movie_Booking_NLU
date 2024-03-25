package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.model.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.model.utils.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.UserService.UserService;
import com.lamnguyen.ticket_movie_nlu.service.UserService.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.ChangePasswordService;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.impl.ChangePasswordServiceImpl;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChangePassword, btnSetting, btnInformation, btnSignOut, btnSubmitChangePassword;
    private TextView edtNewPassword, edtReNewPassword;
    private Dialog dlgChangePassword, dialogLoading;
    private ShapeableImageView sivAvatarUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        this.init(view);
        this.event();
        this.setUpAvatarUser();
        return view;
    }

    private void init(View view) {
        btnInformation = view.findViewById(R.id.button_infomaton);
        btnChangePassword = view.findViewById(R.id.button_change_pasword);
        btnSetting = view.findViewById(R.id.button_setting);
        btnSignOut = view.findViewById(R.id.button_sign_out);
        sivAvatarUser = view.findViewById(R.id.shapeable_image_view_avatar_user);

        dialogLoading = new Dialog(getContext());
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);
    }

    private Dialog getInstanceDialogChangePassword() {
        if (dlgChangePassword == null) {
            dlgChangePassword = new Dialog(this.getContext());
            dlgChangePassword.setContentView(R.layout.dialog_change_password);
            initViewInDialogChangePassword();
            btnSubmitChangePassword.setOnClickListener(view -> {
                eventChangePassword();
            });
        }

        return dlgChangePassword;
    }

    private void event() {
        btnChangePassword.setOnClickListener(v -> {
            getInstanceDialogChangePassword().show();
        });

        btnSignOut.setOnClickListener(v -> {
            eventSignOut();
        });

        sivAvatarUser.setOnClickListener(view -> {
            openImageChooser();
        });
    }

    private void eventSignOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), SignActivity.class);
        this.startActivity(intent);
        getActivity().finish();
    }

    private void eventChangePassword() {
        String password = edtNewPassword.getText().toString();
        String rePassword = edtNewPassword.getText().toString();

        UserService service = UserServiceImpl.getInstance();
        int result = service.matchPassword(password, rePassword);
        switch (result) {
            case UserService.EMPTY_PASSWORD: {
                edtNewPassword.setError(getString(R.string.request_password));
                break;
            }
            case UserService.EMPTY_RE_PASSWORD: {
                edtNewPassword.setError(getString(R.string.request_re_password));
                break;
            }
            case UserService.NOT_MATCH: {
                edtNewPassword.setError(getString(R.string.not_match_password));
                edtNewPassword.setError(getString(R.string.not_match_password));
                break;
            }
            case UserService.MATCH: {
                DialogLoading.showDialogLoading(dialogLoading, getString(R.string.change_password_user));
                ChangePasswordService changePasswordService = ChangePasswordServiceImpl.getInstance();

                changePasswordService.changePassword(password, new ThreadCallBackSign() {
                    @Override
                    public void isSuccess() {
                        dialogLoading.dismiss();
                        dlgChangePassword.dismiss();
                        Toast.makeText(getContext(), getString(R.string.change_password_success), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void isFail() {
                        dialogLoading.dismiss();
                        dlgChangePassword.dismiss();
                        Toast.makeText(getContext(), getString(R.string.change_password_fail), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void initViewInDialogChangePassword() {
        edtNewPassword = dlgChangePassword.findViewById(R.id.edit_text_new_password);
        edtReNewPassword = dlgChangePassword.findViewById(R.id.edit_text_re_new_password);
        btnSubmitChangePassword = dlgChangePassword.findViewById(R.id.button_submit_change_pasword);
    }

    // Phương thức để mở bộ chọn hình ảnh
    @SuppressLint("IntentReset")
    private void openImageChooser() {
        ActivityResultRegistry register = this.getActivity().getActivityResultRegistry();

        ActivityResultLauncher<Intent> launcher = register.register("CHOOSE_AVATAR", new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
                    try {
                        handleSelectAvatarUser(selectedImageUri);
                    } catch (IOException e) {
                        Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        launcher.launch(intent);
    }

    private void handleSelectAvatarUser(Uri imageUri) throws IOException {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        storageReference.child("avatar").putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialogLoading.dismiss();
                sivAvatarUser.setImageURI(imageUri);
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
            } else {
                dialogLoading.dismiss();
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
                Log.e(ProfileFragment.class.getName(), "Errro", task.getException());
            }
        });
    }

    private void setUpAvatarUser() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
//        Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.url_firebase_storage) + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/avatar", Toast.LENGTH_SHORT).show();
        StorageReference gsReference = firebaseStorage.getReferenceFromUrl(getString(R.string.url_firebase_storage) + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/avatar");
        gsReference.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ProfileFragment.this.getContext(), task.getResult().toString(), Toast.LENGTH_SHORT).show();
                Uri uri = task.getResult();
                Glide.with(sivAvatarUser).load(uri).into(sivAvatarUser);
                dialogLoading.dismiss();
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
            } else {
                dialogLoading.dismiss();
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

}