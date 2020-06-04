package com.andy_dev.practicaltask.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.andy_dev.practicaltask.R;
import com.andy_dev.practicaltask.databinding.ActivitySignInBinding;
import com.andy_dev.practicaltask.utils.Utility;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.andy_dev.practicaltask.utils.Utility.*;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignInBinding mBinding;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);

        initData();
    }

    private void initData() {
        mActivity = this;
        mBinding.setHandleClick(this);

        mBinding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
        spannableClickText(mActivity, getString(R.string.dont_have_an_account),
                getString(R.string.sign_up), mBinding.txtSignUp,
                new Utility.OnDialogClick() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(mActivity, SignUpActivity.class));
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_sign_in) {
            hideKeyboard(mActivity);
            if (validate()) {
                if (mBinding.etEmail.getText().toString().equals("test@test.com") ||
                        mBinding.etEmail.getText().toString().equals("user@test.com")) {
                    showSuccess("Logged In successfully!!");
                    mBinding.etEmail.setText("");
                    mBinding.etPassword.setText("");
                } else
                    showError("Default credentials are not matched.", null);
            }
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mBinding.etEmail.getText().toString())) {
            showError(getString(R.string.please_enter_email), mBinding.etEmail);
            return false;
        } else if (!validEmail(mBinding.etEmail.getText().toString())) {
            showError(getString(R.string.please_enter_valid_email), mBinding.etPassword);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etPassword.getText().toString())) {
            showError(getString(R.string.please_enter_password), mBinding.etPassword);
            return false;
        } else if (!validPassword(mBinding.etPassword.getText().toString())) {
            showError(getString(R.string.password_validation_message), mBinding.etPassword);
            return false;
        } else
            return true;
    }

    private void showError(String message, EditText view) {
        if (view != null)
            view.requestFocus();
        showSnackBar(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG, true, mActivity);
    }

    private void showSuccess(String message) {
        showSnackBar(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG, false, mActivity);
    }
}
