package com.vsevolodsvet.estates.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ListEstateDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        final int estateId = bundle.getInt("_id");
        int position = bundle.getInt("position");
        String message = "";

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Объект недвижимости №" + position);
        builder.setMessage(Html.fromHtml(message));

        final EditText editText = new EditText(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 10, 5, 10);

        editText.setLayoutParams(params);
        editText.setGravity(Gravity.TOP);
        editText.setLines(5);

        builder.setView(editText);

        builder.setPositiveButton("Посмотреть на карте",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        /*String geoURI = "google.navigation:?z=10&q="+estate.getAddress();
                        Uri geo = Uri.parse(geoURI);
                        Intent geoIntent = new Intent(Intent.ACTION_VIEW, geo);
                        startActivity(geoIntent);*/
                    }
                });

        builder.setNegativeButton("Отмена", null);

        return builder.create();
    }

}