package com.andy_dev.practicaltask.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.SpinnerAdapter;

import com.andy_dev.practicaltask.R;
import com.andy_dev.practicaltask.adapter.CustomSpinnerAdapter;
import com.andy_dev.practicaltask.databinding.ActivitySignUpBinding;
import com.andy_dev.practicaltask.utils.Utility;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import static com.andy_dev.practicaltask.utils.Utility.hideKeyboard;
import static com.andy_dev.practicaltask.utils.Utility.showSnackBar;
import static com.andy_dev.practicaltask.utils.Utility.spannableClickText;
import static com.andy_dev.practicaltask.utils.Utility.validEmail;
import static com.andy_dev.practicaltask.utils.Utility.validPassword;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySignUpBinding mBinding;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        initData();
    }

    private void initData() {
        mActivity = this;
        mBinding.setHandleClick(this);

        mBinding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
        mBinding.etVerifyPassword.setTransformationMethod(new PasswordTransformationMethod());

        SpinnerAdapter adapter = new CustomSpinnerAdapter(mActivity, android.R.layout.simple_list_item_1, getCountryList());
        mBinding.spnCountry.setAdapter(adapter);

        spannableClickText(mActivity, getString(R.string.have_an_account),
                getString(R.string.sign_in), mBinding.txtSignIn,
                new Utility.OnDialogClick() {
                    @Override
                    public void onClick() {
                        onBackPressed();
                    }
                });
    }

    private ArrayList<String> getCountryList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Select Country");
        list.add("India");
        list.add("USA");
        list.add("Canada");
        list.add("Australia");
        list.add("United Kingdom");
        return list;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_sign_up) {
            hideKeyboard(mActivity);
            if (validate()) {
                showSuccess("Congratulations, You have successfully register for our application!!");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                }, 500);
            }
        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mBinding.etUserName.getText().toString())) {
            showError(getString(R.string.please_enter_user_name), mBinding.etUserName);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etFullName.getText().toString())) {
            showError(getString(R.string.please_enter_full_name), mBinding.etFullName);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etEmail.getText().toString())) {
            showError(getString(R.string.please_enter_email), mBinding.etEmail);
            return false;
        } else if (!validEmail(mBinding.etEmail.getText().toString())) {
            showError(getString(R.string.please_enter_valid_email), mBinding.etPassword);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etAddress.getText().toString())) {
            showError(getString(R.string.please_enter_address), mBinding.etAddress);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etCity.getText().toString())) {
            showError(getString(R.string.please_enter_city), mBinding.etCity);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etState.getText().toString())) {
            showError(getString(R.string.please_enter_state), mBinding.etState);
            return false;
        } else if (mBinding.spnCountry.getSelectedItemPosition() == 0) {
            showError(getString(R.string.please_select_country), null);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etPassword.getText().toString())) {
            showError(getString(R.string.please_enter_password), mBinding.etPassword);
            return false;
        } else if (!validPassword(mBinding.etPassword.getText().toString())) {
            showError(getString(R.string.password_validation_message), mBinding.etPassword);
            return false;
        } else if (TextUtils.isEmpty(mBinding.etVerifyPassword.getText().toString())) {
            showError(getString(R.string.please_enter_verify_password), mBinding.etVerifyPassword);
            return false;
        } else if (!TextUtils.equals(mBinding.etPassword.getText().toString(), mBinding.etVerifyPassword.getText().toString())) {
            showError(getString(R.string.passwords_must_be_same), mBinding.etVerifyPassword);
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
