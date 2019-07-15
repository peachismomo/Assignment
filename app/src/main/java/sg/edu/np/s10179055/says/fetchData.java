package sg.edu.np.s10179055.says;

import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        String url1 = "https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=SystemCampus>Blk 51>Level2-Canteen>CoverageArea-B51L02";
        URL url = new URL(url1);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        return null;
    }
}
