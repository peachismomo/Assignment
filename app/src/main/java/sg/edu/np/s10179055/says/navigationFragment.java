package sg.edu.np.s10179055.says;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class navigationFragment extends Fragment {
    WebView browser;

    public navigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_navigation, container, false);

        browser = RootView.findViewById(R.id.webView);
        browser.setWebViewClient(new WebViewClient());
        WebSettings webSettings = browser.getSettings();
        webSettings.setJavaScriptEnabled(true);
        browser.loadUrl("https://np.conveno.com/npmap/web/app/#/app/home");

        return RootView;
    }

}
