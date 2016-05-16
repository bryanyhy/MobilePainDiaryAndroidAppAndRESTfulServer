package bryan.mobilepaindiary.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 28/4/16.
 */
// drawing a pie chart
public class PieChartFragment extends Fragment {
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<Integer> count = new ArrayList<>();
    ArrayList<String> labels = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i("System.out", "Start drawing");
        // setting up the view for drawing piechart
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        PieChart pieChart = (PieChart) view.findViewById(R.id.chart);

        // get the bundle from graph fragment, which contains the pain locations and the number of counts
        Bundle bundle = this.getArguments();
        count = bundle.getIntegerArrayList("count");
        labels = bundle.getStringArrayList("labels");

        // setting up Entry, which is essential for drawing the pie chart
        for(int i = 0; i < labels.size(); i++)
        {
            // add the count number and index
            entries.add(new Entry(count.get(i), i));
        }
        // create dataset by entries
        PieDataSet dataset = new PieDataSet(entries, "Pain Location");
        // label for the data
        PieData data = new PieData(labels, dataset);
        pieChart.setDescription("Pie Chart on Pain Location and Percentage of counts");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.animateY(5000);
        pieChart.setUsePercentValues(true);
        Log.i("System.out", "Finish drawing");
        return view;
    }
}
