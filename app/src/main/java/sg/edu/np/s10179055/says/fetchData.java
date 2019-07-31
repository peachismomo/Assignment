package sg.edu.np.s10179055.says;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class fetchData extends AsyncTask<Void, Void, Void> {
    String mkp;
    String twentytwo;
    String munch;
    String poolside;
    String line;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //Retrieve number of people at Makanplace
            URL url = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System Campus>Blk 51>Level 2 - Canteen>CoverageArea-B51L02");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                mkp += line;
            }

            //Retrieve number of people at 22
            URL url2 = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System Campus>Blk 22>Level 1>CoverageArea-B22L01");
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
            InputStream inputStream2 = httpURLConnection2.getInputStream();
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
            line = "";
            while (line != null) {
                line = bufferedReader2.readLine();
                twentytwo += line;
            }

            //Retrieve number of people Munch
            URL url3 = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System Campus>Blk 73>Level 1>Munch");
            HttpURLConnection httpURLConnection3 = (HttpURLConnection) url3.openConnection();
            InputStream inputStream3 = httpURLConnection3.getInputStream();
            BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(inputStream3));
            line = "";
            while (line != null) {
                line = bufferedReader3.readLine();
                munch += line;
            }

            //Retrieve number of people at Poolside
            URL url4 = new URL("https://www1.np.edu.sg/npnet/wifiatcanteen/CMXService.asmx/getChartData?Location=System Campus>Blk 18>Level 1 - Canteen>CoverageArea-B18L01");
            HttpURLConnection httpURLConnection4 = (HttpURLConnection) url4.openConnection();
            InputStream inputStream4 = httpURLConnection4.getInputStream();
            BufferedReader bufferedReader4 = new BufferedReader(new InputStreamReader(inputStream4));
            line = "";
            while (line != null) {
                line = bufferedReader4.readLine();
                poolside += line;
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

        //Assign all the variables to be used by the foodPlacesFragment in the custom listView
        mkp = mkp.substring(88, mkp.indexOf(","));
        foodPlacesFragment.currentMKP = Integer.parseInt(this.mkp);

        twentytwo = twentytwo.substring(88, twentytwo.indexOf(","));
        foodPlacesFragment.currentTwentyTwo = Integer.parseInt(this.twentytwo);

        munch = munch.substring(88, munch.indexOf(","));
        foodPlacesFragment.currentMunch = Integer.parseInt(this.munch);

        poolside = poolside.substring(88, poolside.indexOf(","));
        foodPlacesFragment.currentPoolside = Integer.parseInt(this.poolside);
    }
}
