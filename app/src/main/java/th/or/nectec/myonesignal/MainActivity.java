package th.or.nectec.myonesignal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTokenTextView;
    private TextView mMessageTextView;
    private TextView mPlayerTextView;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private BroadcastReceiver mReceivedNewMsgBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindWidgets();
        initOneSignal();
    }

    private void bindWidgets() {
        mTokenTextView = (TextView) findViewById(R.id.tokenTextView);
        mMessageTextView = (TextView) findViewById(R.id.messageTextView);
        mPlayerTextView = (TextView) findViewById(R.id.playerIDTextView);
    }


    private void initOneSignal() {
        // Define Broadcast for receving token and player id
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String token = intent.getStringExtra(CMConstant.RECEIVED_TOKEN);
                String playerID = intent.getStringExtra(CMConstant.RECEIVED_PLAYERID);
                mTokenTextView.setText(token);
                mPlayerTextView.setText(playerID);
            }
        };

        // Define Broadcast for new message
        mReceivedNewMsgBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String _msg = intent.getStringExtra(CMConstant.RECEIVED_MESSAGE);
                mMessageTextView.setText(_msg);
            }
        };

        // Register Broadcasts
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(CMConstant.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceivedNewMsgBroadcastReceiver,
                new IntentFilter(CMConstant.RECEIVED_NEW_MESSAGE));

    }

}
