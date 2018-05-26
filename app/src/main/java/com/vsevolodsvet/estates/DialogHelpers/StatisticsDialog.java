package com.vsevolodsvet.estates.DialogHelpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vsevolodsvet.estates.ExtraHelpers.StatHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class StatisticsDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Bundle bundle = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Статистика по объектам недвижимости");

        String message;

        HashMap<String, List<Double>> regionPrices = new HashMap<String, List<Double>>();
        regionPrices = (HashMap<String, List<Double>>) bundle.getSerializable("estateRegionMPrices");

        // задание текста для элемента списка (message)
        //region
        message = "<strong>Общее число объектов:</strong><br>" + bundle.getInt("estateCount") + "<br><br>";
        if ((!Objects.equals(bundle.getInt("estate1RoomCount"), null) && bundle.getInt("estate1RoomCount") > 0) ||
                (!Objects.equals(bundle.getInt("estate1RoomCount"), null) && bundle.getInt("estate1RoomCount") > 0) ||
                (!Objects.equals(bundle.getInt("estate2RoomCount"), null) && bundle.getInt("estate2RoomCount") > 0) ||
                (!Objects.equals(bundle.getInt("estate3RoomCount"), null) && bundle.getInt("estate3RoomCount") > 0) ||
                (!Objects.equals(bundle.getInt("estateMoreRoomCount"), null) && bundle.getInt("estateMoreRoomCount") > 0)) {
            message += "<strong>Объекты по количеству комнат:</strong><br>";
            if (!Objects.equals(bundle.getInt("estate1RoomCount"), null) && bundle.getInt("estate1RoomCount") > 0) {
                message += "<em><strong>Однокомнатные: </strong></em>" +
                        bundle.getInt("estate1RoomCount")
                        + "<br>";
            }
            if (!Objects.equals(bundle.getInt("estate2RoomCount"), null) && bundle.getInt("estate2RoomCount") > 0) {
                message += "<em><strong>Двухкомнатные: </strong></em>" +
                        bundle.getInt("estate2RoomCount")
                        + "<br>";
            }
            if (!Objects.equals(bundle.getInt("estate3RoomCount"), null) && bundle.getInt("estate3RoomCount") > 0) {
                message += "<em><strong>Трехкомнатные: </strong></em>" +
                        bundle.getInt("estate3RoomCount")
                        + "<br>";
            }

            if (!Objects.equals(bundle.getInt("estateMoreRoomCount"), null) && bundle.getInt("estateMoreRoomCount") > 0) {
                message += "<em><strong>4 и более комнат: </strong></em>" +
                        bundle.getInt("estateMoreRoomCount")
                        + "<br>";
            }
        }
        if (!Objects.equals(bundle.getInt("estateNoRoomCount"), null) && bundle.getInt("estateNoRoomCount") > 0){
            message += "<strong>Нет информации о количестве комнат для "+
                    bundle.getInt("estateNoRoomCount")
                    +" объектов</strong><br>";
        }

        if (!Objects.equals(regionPrices, null)) message += "<br><strong>Статистика цен за метр квадратный метр по районам:</strong><br>";

        for (HashMap.Entry<String, List<Double>> entry : regionPrices.entrySet()){
            message += "<br><strong>"+ entry.getKey() +":</strong><br>";
            message += "<em>Средняя цена:</em> "+ StatHelper.GetAverage(entry.getValue(), 2) +"<br>";
            message += "<em>Мода:</em> "+ StatHelper.GetMode(entry.getValue(), 2) +"<br>";
            message += "<em>Среднеквадратическое отклонение:</em> "+ StatHelper.GetStandardDeviation(entry.getValue(), 2) +"<br>";
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

        builder.setNegativeButton("Закрыть", null);

        return builder.create();
    }

}