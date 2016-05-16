package bryan.mobilepaindiary.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bryan.mobilepaindiary.R;


// Drawing the line graph
public class LineGraph extends Fragment {
    // x axis, i.e. date
    ArrayList<String> dataPointXArrayList = new ArrayList<>();
    // y1 axis, i.e. pain level
    ArrayList<String> dataPointYArrayList = new ArrayList<>();
    // y2 axis, i.e. weather attribute user selected
    ArrayList<String> dataPointY2ArrayList = new ArrayList<>();

    private TextView pValueText;
    private TextView signValueText;

    ArrayList<Date> dates = new ArrayList<>();
    ArrayList<Integer> painLevels = new ArrayList<>();
    ArrayList<Double> weathers = new ArrayList<>();

    private String weatherVariable;
    private String correlation;
    private String pValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("System.out", "Start drawing");
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        pValueText = (TextView) view.findViewById(R.id.pValueText);
        signValueText = (TextView) view.findViewById(R.id.signValueText);
        // set title of graph
        graph.setTitle("Line Graph");
        // getting information through bundle from previous fragment
        Bundle bundle = this.getArguments();
        dataPointXArrayList = bundle.getStringArrayList("X");
        dataPointYArrayList = bundle.getStringArrayList("Y1");
        dataPointY2ArrayList = bundle.getStringArrayList("Y2");
        weatherVariable = bundle.getString("weather");
        correlation = bundle.getString("correlation");
        pValue = bundle.getString("pValue");
        pValueText.setText(pValue);
        signValueText.setText(correlation);

        // setting up the content we want to share
        ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse("https://en.wikipedia.org/wiki/Correlation_and_dependence"))
                .setContentTitle("Correlation Finding")
                .setContentDescription(correlation + ", " + pValue)
                .build();
        // setting up the share button
        ShareButton shareButton = (ShareButton) view.findViewById(R.id.fb_share_Button);
        // share content when clicked
        shareButton.setShareContent(content);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try
        {
            for (int i = 0; i < dataPointXArrayList.size(); i++)
            {
                // parse the date into date format from String
                dates.add(formatter.parse(dataPointXArrayList.get(i)));
                // parse the pain level to interger from String
                painLevels.add(Integer.parseInt(dataPointYArrayList.get(i)));
                // parse weather attribute to Double from String
                weathers.add(Double.parseDouble(dataPointY2ArrayList.get(i)));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        // for line on date and pain level
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        // for line on date and weather attribute
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < dates.size(); i++)
        {
            // getting all attributes value we need for the line graph
            Date x = dates.get(i);
            int y1 = painLevels.get(i);
            Double y2 = weathers.get(i);
            // pass them into series and series2 two array
            series.appendData(new DataPoint(x, y1), false, dates.size() + 1);
            series2.appendData(new DataPoint(x, y2), false, dates.size() + 1);
        }

        // add first series
        graph.addSeries(series);
        // add the second extra series
        graph.getSecondScale().addSeries(series2);

        // setting the y axis value for different weather variables
        switch(weatherVariable) {
            case "atmosphericPressure":
                graph.getSecondScale().setMinY(1000);
                graph.getSecondScale().setMaxY(1030);
                break;
            case "humidity":
                graph.getSecondScale().setMinY(0);
                graph.getSecondScale().setMaxY(100);
                break;
            case "temperature":
                graph.getSecondScale().setMinY(0);
                graph.getSecondScale().setMaxY(50);
                break;
            case "windSpeed":
                graph.getSecondScale().setMinY(0);
                graph.getSecondScale().setMaxY(50);
                break;
        }
        series.setDrawDataPoints(true);
        series2.setDrawDataPoints(true);
        // set y axis name
        series.setTitle("Pain Level");
        // set y2 axis name
        series2.setTitle(weatherVariable);
        series.setDataPointsRadius(8);
        series2.setDataPointsRadius(8);
        series2.setColor(Color.RED);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
        Log.i("System.out", "Finish drawing");
        return view;
    }


}