package bryan.mobilepaindiary.Fragment;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import bryan.mobilepaindiary.R;

/**
 * Created by Bryanyhy on 25/4/16.
 */
public class MainFragment extends Fragment {
    View vMain;

    private TextView fnameText;
    private TextView dateText;
    private String firstname;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);

        fnameText = (TextView) vMain.findViewById(R.id.fnameText);
        dateText = (TextView) vMain.findViewById(R.id.dateText);
        imageView = (ImageView) vMain.findViewById(R.id.imageView);

        // show firstname of user
        firstname = getArguments().getString("firstname");
        fnameText.setText(firstname);
        // show today's date
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        dateText.setText(currentDateTimeString);

        // setting up health icon
        imageView.setImageResource(R.mipmap.health_icon);

        return vMain;
    }
}
