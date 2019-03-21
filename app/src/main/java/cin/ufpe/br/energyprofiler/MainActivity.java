package cin.ufpe.br.energyprofiler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cin.ufpe.br.energyprofiler.enums.EnumDashboard;
import cin.ufpe.br.energyprofiler.listeners.WhatNowListener;

public class MainActivity extends AppCompatActivity {

    public RequestQueue queue;
    public ModeOperator modeOperator;
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText = (TextView) findViewById(R.id.text);

        Options.createRandomNumbers();
        modeOperator = new ModeOperator(this);

        // this parameter needs to be passed
        // or using the dashboard or using the adb
        String collecParam = getIntent().getStringExtra("param");
        setEndpointAddress();
        setWorkload();

        // submitting the request to the dashboard.
        queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EnumDashboard.WHAT_NOW.toString(),
                new WhatNowListener(modeOperator,collecParam),
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //An error will occur if the dashboard isn't found.
                        //In that case, the app will enter the offline modeOperator.
                        error.printStackTrace();
                        mainText.setText(mainText.getText()+ "\n ERROR:\n webservice not found.");

                        //Options.offlineMode = true;
                        //startOfflineMode(collecParam);
                    }
                });
        queue.add(stringRequest);
    }

    private void setEndpointAddress() {
        String ipAddress = getIntent().getStringExtra("ipAddress");
        String port = getIntent().getStringExtra("port");
        if( (ipAddress!=null) && (!"".equals(ipAddress)) ){
            IpAddress.ip=ipAddress;
        }
        if( (port!=null) && (!"".equals(port)) ){
            IpAddress.port=port;
        }
    }

    private void setWorkload() {
        String workload = getIntent().getStringExtra("workload");
        String nThreads = getIntent().getStringExtra("nThreads");
        if( (workload!=null) && (!"".equals(workload)) ){
            Options.capacity=Integer.parseInt(workload);
        }
        if( (nThreads!=null) && (!"".equals(nThreads)) ){
            Options.nThreads=Short.parseShort(nThreads);
        }
    }

//    /**
//     * This method starts the offline modeOperator.
//     * This modeOperator does not use the dashboard and its mainly used for
//     * debugging. No energy data will be collected using the
//     * offline modeOperator and the only output will be the time of each
//     * action. Currently just work for LISTS
//     * @param collecParam a valid NameAction value.
//     */
//    private void startOfflineMode(String collecParam) {
//        initExecutor(collecParam);
//        TextView offlineText = (TextView) findViewById(R.id.offlineText);
//        offlineText.setVisibility(View.VISIBLE);
//        if(isReady()){
//            for (Actions action : Actions.values()) {
//                if(action != Actions.NOT_FOUND){
//                    execute(action);
//                }
//            }
//        }
//    }
}
