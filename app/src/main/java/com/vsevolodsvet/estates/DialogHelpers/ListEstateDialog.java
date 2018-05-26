package com.vsevolodsvet.estates.DialogHelpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;

public class ListEstateDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Объект недвижимости №" + bundle.getLong("estateId"));

        String message;
        // задание текста для элемента списка (message)
        //region
        message = "<strong>Адрес:</strong><br>" + bundle.getString("estateAdress") + "<br><br>";
        if (!Integer.valueOf(bundle.getInt("estateLevel")).equals(null) && bundle.getInt("estateLevel") != 0) {
            message += "<strong>Этаж: </strong>" + bundle.getInt("estateLevel");
            if (!Integer.valueOf(bundle.getInt("estateLevelAmount")).equals(null) && bundle.getInt("estateLevelAmount") != 0) {
                message +=  " из " + bundle.getInt("estateLevelAmount") + "<br><br>";
            } else {
                message += "<br><br>";
            }
        }
        if (!Integer.valueOf(bundle.getInt("estateRooms")).equals(null)){
            message += "<strong>Количество комнат: </strong>" + bundle.getInt("estateRooms") + "<br><br>";
        }
        if (!Double.isNaN(bundle.getDouble("estateSLive")) ||
            !Double.isNaN(bundle.getDouble("estateSAll")) ||
            !Double.isNaN(bundle.getDouble("estateSR"))) {
            message += "<strong>Площадь квартиры: </strong><br>";
            if (!Double.isNaN(bundle.getDouble("estateSLive")) && bundle.getDouble("estateSLive") != 0) {
                message += "<em>- Жилая - </em>" +
                        BigDecimal.valueOf(bundle.getDouble("estateSLive")).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue()
                        + "<br>";
            }
            if (!Double.isNaN(bundle.getDouble("estateSAll")) && bundle.getDouble("estateSAll") != 0) {
                message += "<em>- Общая - </em>" +
                        BigDecimal.valueOf(bundle.getDouble("estateSAll")).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue()
                        + "<br>";
            }
            if (!Double.isNaN(bundle.getDouble("estateSR")) && bundle.getDouble("estateSR") != 0) {
                message += "<em>- Комнат - </em>" +
                        BigDecimal.valueOf(bundle.getDouble("estateSR")).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue()
                        + "<br>";
            }
            message += "<br>";
        }
        if (!Integer.valueOf(bundle.getInt("estateBalcony")).equals(null)){
            Integer balc = bundle.getInt("estateBalcony");
            if (balc == 0) {
                message += "<strong>Балконы отсутствуют</strong><br><br>";
            } else if (balc > 0){
                message += "<strong>Количество балконов: </strong>"+ balc +"<br><br>";
            }
        }
        if (!String.valueOf(bundle.getString("estateYear")).equals(null)){
            String year = bundle.getString("estateYear");
            message += "<strong>Год постройки: </strong>" +
                    year.substring(year.length()-4, year.length())
                    + "<br><br>";
        }
        if (!Double.isNaN(bundle.getDouble("estatePriceM")) && !Double.isNaN(bundle.getDouble("estatePriceR"))){
            message += "<h3><strong>Стоимость:</strong></h3>";
            if (!Double.isNaN(bundle.getDouble("estatePriceR")) && bundle.getDouble("estatePriceR") != 0) {
                message += "<em>- Полная - </em>" +
                        BigDecimal.valueOf(bundle.getDouble("estatePriceR")).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue()
                        + " руб.<br>";
            }
            if (!Double.isNaN(bundle.getDouble("estatePriceM")) && bundle.getDouble("estatePriceM") != 0) {
                message += "<em>- За м<sup>2</sup> - </em>" +
                        BigDecimal.valueOf(bundle.getDouble("estatePriceM")).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue()
                        + " руб.<br>";
            }
        }
        //endregion

        builder.setMessage(Html.fromHtml(message));

        final TextView textView = new TextView(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5, 10, 5, 10);

        textView.setLayoutParams(params);
        textView.setGravity(Gravity.TOP);
        textView.setLines(5);

        builder.setView(textView);

        builder.setPositiveButton("Посмотреть на карте",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        String geoURI;
                        if (!Double.isNaN(bundle.getDouble("estateXcoord")) &&
                            !Double.isNaN(bundle.getDouble("estateYcoord"))) {
                            geoURI = "google.navigation:?z=10q="+bundle.getDouble("estateXcoord")+","+bundle.getDouble("estateYcoord");
                        } else {
                            geoURI = "google.navigation:?z=10&q=" + bundle.getString("estateAdress");
                        }
                        Uri geo = Uri.parse(geoURI);
                        Intent geoIntent = new Intent(Intent.ACTION_VIEW, geo);
                        startActivity(geoIntent);
                    }
                });

        builder.setNegativeButton("Отмена", null);

        return builder.create();
    }

}