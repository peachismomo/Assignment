package sg.edu.np.s10179055.says;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data;
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //will repeat code for all 4 and parse through to extract number
            URL url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=SystemCampus>Blk 51>Level2-Canteen>CoverageArea-B51L02");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null){
                line = bufferedReader.readLine();
                data += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        foodPlacesFragment.tvDisplay.setText(this.data);
    }
}
