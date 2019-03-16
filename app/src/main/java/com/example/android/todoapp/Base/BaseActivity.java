package com.example.android.todoapp.Base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

public class BaseActivity extends AppCompatActivity {

    protected AppCompatActivity activity;

    MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }


    public MaterialDialog showConfirmationMessage(int titleResId, int messageResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPosAction) {

        dialog = new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(messageResId)
                .positiveText(posTextResId)
                .onPositive(onPosAction)
                .show();

        return dialog;


    }

    public MaterialDialog showConfirmationMessage(int titleResId, int messageResId,
                                                  int posTextResId,
                                                  MaterialDialog.SingleButtonCallback onPosAction,
                                                  int negTextResId,
                                                  MaterialDialog.SingleButtonCallback onNegAction) {

        dialog = new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(messageResId)
                .positiveText(posTextResId)
                .onPositive(onPosAction)
                .negativeText(negTextResId)
                .onNegative(onNegAction)
                .show();

        return dialog;


    }



    public MaterialDialog showMessage(int titleResId, int messageResId,
                                                  int posTextResId) {

        dialog = new MaterialDialog.Builder(this)
                .title(titleResId)
                .content(messageResId)
                .positiveText(posTextResId)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();

        return dialog;


    }


    //Prefer not to use it
    //there is no longer a progress dialog included in the new version of the library,
    // since they are discouraged by Google, and discouraged by me.
    // You should prefer a non-blocking inline progress indicator.

    public MaterialDialog showProgressBar() {
        dialog = new MaterialDialog.Builder(this)
                .progress(true, 0)
                .cancelable(false)
                .show();

        return dialog;
    }

    public MaterialDialog hideProgressBar() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        return dialog;
    }

}
