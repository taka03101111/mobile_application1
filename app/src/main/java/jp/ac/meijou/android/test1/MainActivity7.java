package jp.ac.meijou.android.test1;

import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.stream.Collectors;

import jp.ac.meijou.android.test1.databinding.ActivityMain6Binding;
import jp.ac.meijou.android.test1.databinding.ActivityMain7Binding;

public class MainActivity7 extends AppCompatActivity {

    private ActivityMain7Binding binding;

    private ConnectivityManager connectivityManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMain7Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        connectivityManager = getSystemService(ConnectivityManager.class);
        var currentNetwork = connectivityManager.getActiveNetwork();
        updateTransport(currentNetwork);
        updateIpAddress(currentNetwork);
    }

    private void updateTransport(Network network) {
        var caps = connectivityManager.getNetworkCapabilities(network);
        if (caps != null) {
            String transport;
            if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                transport = "モバイル通信";
            } else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                transport = "WiFi";
            } else {
                transport = "その他";
            }
            binding.transport.setText(transport);
        }
    }


    private void updateIpAddress(Network network) {
        var linkProperties = connectivityManager.getLinkProperties(network);
        if (linkProperties != null) {
            var addresses = linkProperties.getLinkAddresses().stream()
                    .map(LinkAddress::toString)
                    .collect(Collectors.joining("\n"));
            binding.iptransport.setText(addresses);
        }
    }
    private void registerNetworkCallback() {
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                runOnUiThread(() -> {
                    updateTransport(network);
                    updateIpAddress(network);
                });
            }
        });
    }
}