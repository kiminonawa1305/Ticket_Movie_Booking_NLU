package com.lamnguyen.ticket_movie_nlu.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.Scope;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lamnguyen.ticket_movie_nlu.R;
import com.lamnguyen.ticket_movie_nlu.bean.User;
import com.lamnguyen.ticket_movie_nlu.utils.DialogLoading;
import com.lamnguyen.ticket_movie_nlu.service.auth.ThreadCallBackSign;
import com.lamnguyen.ticket_movie_nlu.service.user.UserService;
import com.lamnguyen.ticket_movie_nlu.service.user.impl.UserServiceImpl;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.ChangePasswordService;
import com.lamnguyen.ticket_movie_nlu.service.auth.change_password.impl.ChangePasswordServiceImpl;
import com.lamnguyen.ticket_movie_nlu.utils.SharedPreferencesUtils;
import com.lamnguyen.ticket_movie_nlu.view.activities.IntroductionActivity;
import com.lamnguyen.ticket_movie_nlu.view.activities.SignActivity;

import java.io.IOException;

public class ProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button btnChangePassword, btnSetting, btnInformation, btnSignOut, btnSubmitChangePassword;
    private TextView edtNewPassword, edtReNewPassword;
    private Dialog dlgChangePassword, dialogLoading;
    private ShapeableImageView sivAvatarUser;
    private ImageView imgvBackgroundUser;
    private ActivityResultLauncher<Intent> chooseAvatar, chooseBackground;
    private Intent intent_pick_image;
    private User user;
    private boolean googleSignIn;
    private FirebaseStorage firebaseStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        this.init(view);
        this.event();
        this.setUpAvatarAndBackgroundUser();
        this.registerImageChooser();
        return view;
    }

    private void init(View view) {
        btnInformation = view.findViewById(R.id.button_infomaton);
        btnChangePassword = view.findViewById(R.id.button_change_pasword);
        btnSetting = view.findViewById(R.id.button_setting);
        btnSignOut = view.findViewById(R.id.button_sign_out);
        sivAvatarUser = view.findViewById(R.id.shapeable_image_view_avatar_user);
        imgvBackgroundUser = view.findViewById(R.id.image_view_background_user);

        dialogLoading = new Dialog(getContext());
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);

        googleSignIn = SharedPreferencesUtils.isGoogleSignIn(getContext());
        user = SharedPreferencesUtils.getUser(getContext());

        if (googleSignIn) {
            btnChangePassword.setVisibility(View.GONE);
        }

        firebaseStorage = FirebaseStorage.getInstance();
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
            chooseAvatar.launch(intent_pick_image);
        });

        imgvBackgroundUser.setOnClickListener(view -> {
            chooseBackground.launch(intent_pick_image);
        });

        btnInformation.setOnClickListener(view -> {
            eventShowIntroduction();
        });
    }

    private void eventShowIntroduction() {
        Intent intent = new Intent(getActivity(), IntroductionActivity.class);
        this.startActivity(intent);
    }

    private void eventSignOut() {
        SharedPreferencesUtils.logOut(getContext());
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
        btnSubmitChangePassword = dlgChangePassword.findViewById(R.id.button_submit_change_password);
    }

    // Phương thức để mở bộ chọn hình ảnh
    @SuppressLint("IntentReset")
    private void registerImageChooser() {
        ActivityResultRegistry register = this.getActivity().getActivityResultRegistry();

        chooseAvatar = register.register("CHOOSE_AVATAR", new ActivityResultContracts.StartActivityForResult(), result -> {
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

        chooseBackground = register.register("CHOOSE_BACKGROUND", new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
                    try {
                        handleSelectBackgroundUser(selectedImageUri);
                    } catch (IOException e) {
                        Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(this.getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        intent_pick_image = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent_pick_image.setType("image/*");
    }

    private void handleSelectAvatarUser(Uri imageUri) throws IOException {
        StorageReference storageReference = firebaseStorage.getReference().child(user.getEmail());
        storageReference.child("avatar").putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialogLoading.dismiss();
                sivAvatarUser.setImageURI(imageUri);
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
            } else {
                dialogLoading.dismiss();
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSelectBackgroundUser(Uri imageUri) throws IOException {
        StorageReference storageReference = firebaseStorage.getReference().child(user.getEmail());
        storageReference.child("background").putFile(imageUri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialogLoading.dismiss();
                imgvBackgroundUser.setImageURI(imageUri);
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.success), Toast.LENGTH_SHORT).show();
            } else {
                dialogLoading.dismiss();
                Toast.makeText(ProfileFragment.this.getContext(), getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpAvatarAndBackgroundUser() {
        DialogLoading.showDialogLoading(dialogLoading, getString(R.string.loading));
        StorageReference avatarStrRef = firebaseStorage.getReferenceFromUrl(getString(R.string.url_firebase_storage)).child(user.getEmail()).child("avatar");
        StorageReference backgroundStrRef = firebaseStorage.getReferenceFromUrl(getString(R.string.url_firebase_storage)).child(user.getEmail()).child("background");
        avatarStrRef.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri uriAvatar = task.getResult();
                Glide.with(sivAvatarUser).load(uriAvatar).into(sivAvatarUser);
                dialogLoading.dismiss();
            } else {
                dialogLoading.dismiss();
            }
        });

        backgroundStrRef.getDownloadUrl().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri uri = task.getResult();
                Glide.with(imgvBackgroundUser).load(uri).into(imgvBackgroundUser);
                dialogLoading.dismiss();
            } else {
                dialogLoading.dismiss();
            }
        });
    }

}