package com.andy_dev.practicaltask.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.andy_dev.practicaltask.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class Utility {

    private final static Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[a-zA-Z])(?=.*?[0-9]).{6,}$");

    public interface OnDialogClick {
        void onClick();
    }

    public static void spannableClickText(Activity mActivity, String s1, String s2,
                                          TextView textView, final OnDialogClick dialogClick) {
        Spannable word = new SpannableString(s1);
        textView.setText(word);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                dialogClick.onClick();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        Spannable wordTwo = new SpannableString(" " + s2);
        wordTwo.setSpan(clickableSpan, 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwo);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);
        textView.setLinkTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimary));
    }

    public static void hideKeyboard(Activity aContext) {
        if (aContext != null) {
            InputMethodManager im = (InputMethodManager) aContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null)
                im.hideSoftInputFromWindow(aContext.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean validEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean validPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean notNullEmpty(String str) {
        return str != null && str.length() > 0;
    }

    public static void showSnackBar(View view, String message, int time, boolean isTypeError, Context context) {

        Snackbar snackbar = Snackbar.make(view, message, time);
        View snackBarView = snackbar.getView();
        TextView snackTextView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackTextView.setTypeface(ResourcesCompat.getFont(context, R.font.poppins_medium));
        snackTextView.setMaxLines(4);

        snackBarView.setBackgroundColor(ContextCompat.getColor(context, isTypeError ? R.color.red : R.color.green));
        snackbar.show();
    }

}
