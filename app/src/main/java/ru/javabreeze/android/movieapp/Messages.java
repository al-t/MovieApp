package ru.javabreeze.android.movieapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Алексей on 17.06.2016.
 */
public class Messages {
    private static AlertDialog dialog;
    private static ProgressDialog pdBuff;

    public static void ShowMessage(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                //.setIcon(R.drawable.icon)
                .setCancelable(false)
                .setNegativeButton(context.getResources().getString(R.string.OK),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        dialog = alert;
        alert.show();
    }

    static AlertDialog getDialog() {
        return dialog;
    }

    static void sendNetworkErrorMessage(Context context) {
        ShowMessage(context, context.getResources()
                        .getString(R.string.network_not_connected_title),
                context.getResources().getString(R.string.network_not_connected_message));
    }



}