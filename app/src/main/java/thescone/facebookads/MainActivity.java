package thescone.facebookads;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;
    private Button showInterstitialButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate an InterstitialAd object
        interstitialAd = new InterstitialAd(this, "PLACEMENT_ID");

        showInterstitialButton = (Button) findViewById(R.id.btn_show_interstitial);
        showInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButton();
                // Display the Interstitial Ad
                interstitialAd.loadAd();
            }
        });

        // Set listeners for the Interstitial Ad
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Toast.makeText(MainActivity.this, "Interstitial Ad displayed!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Toast.makeText(MainActivity.this, "Interstitial Ad dismissed!",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Toast.makeText(MainActivity.this, "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG).show();
                enableButton();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Show the ad when it's done loading.
                interstitialAd.show();
                enableButton();
            }

            @Override
            public void onAdClicked(Ad ad) {
                Toast.makeText(MainActivity.this, "Interstitial Ad clicked!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void enableButton() {
        showInterstitialButton.setEnabled(true);
        showInterstitialButton.setText("Show Interstitial");
    }

    public void disableButton() {
        showInterstitialButton.setEnabled(false);
        showInterstitialButton.setText("Ad Loading...");
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
