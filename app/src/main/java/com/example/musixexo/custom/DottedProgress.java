package com.example.musixexo.custom;

import android.app.AlertDialog;
import android.content.Context;

import com.example.musixexo.R;

import dmax.dialog.SpotsDialog;

public class DottedProgress {
    AlertDialog alertDialog;

    public DottedProgress(Context context) {
        alertDialog = new SpotsDialog.Builder().setContext(context).setMessage("Loading").setCancelable(false)
                .build();

    }

    public void show() {
        alertDialog.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }
}