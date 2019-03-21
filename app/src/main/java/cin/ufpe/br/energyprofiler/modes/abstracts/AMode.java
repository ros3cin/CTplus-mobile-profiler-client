package cin.ufpe.br.energyprofiler.modes.abstracts;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import cin.ufpe.br.energyprofiler.MainActivity;
import cin.ufpe.br.energyprofiler.Options;
import cin.ufpe.br.energyprofiler.R;
import cin.ufpe.br.energyprofiler.enums.EnumDashboard;
import cin.ufpe.br.energyprofiler.enums.IActions;
import cin.ufpe.br.energyprofiler.enums.EnumCollections;
import cin.ufpe.br.energyprofiler.enums.exceptions.TimeThresholdException;
import cin.ufpe.br.energyprofiler.listeners.FinishListener;
import cin.ufpe.br.energyprofiler.modes.AppExecutor;

/**
 * Created by welli on 03-Dec-17.
 */

public abstract class AMode {

    protected MainActivity activity;
    protected TextView mainText;
    protected TextView header;
    protected Vibrator v;
    protected AppExecutor executor;
    public IActions action;


    public abstract IActions getNextAction();
    public abstract void initExecutor(String collecParam,IActions action);
    public abstract void execute(IActions str);
    public abstract void finishedLogging();
    public abstract String getModeName();

    protected AMode(MainActivity activity,String initParam){
        this.activity = activity;
        mainText = (TextView) activity.findViewById(R.id.text);
        header = (TextView) activity.findViewById(R.id.header);
        v = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);

        if(initParam != null) {
            header.setVisibility(View.VISIBLE);
            header.setText(getModeName() + ":\n " + EnumCollections.exist(initParam).getName());
            mainText.setText("");
        }
    }

    protected void executeAndDashBoard(IActions action, EnumDashboard dashboardAction){
        try {
            executor.execute(action);
            dashboardMsg(dashboardAction);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showOnScreen(String action, String text) {
        mainText.setText(new StringBuilder(mainText.getText())
                .append(action)
                .append(" done!\n")
                .toString()
        );
    }

    public boolean isReady() {
        return executor!=null;
    }

    public void endApp() {
       // header.setVisibility(View.INVISIBLE);
        if(Options.isVibrating) {
            v.vibrate(1000);
        }
        if(Options.isClosing) {
            activity.finish();
            Runtime.getRuntime().gc();
            // System.exit(0);
        }
    }
    public void dashboardMsg(EnumDashboard action) {
        // in the offlineMode, we can't collect
        // energy data, so the dashboardMsg does nothing
        if(!Options.offlineMode) {

            // submitting a request to iniciate the getNextAction action
            StringRequest stringRequest = new StringRequest(Request.Method.GET, action.toString(),
                    new FinishListener(this),
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            mainText.setText("ERROR end:\n webservice not found");
                        }
                    });
            activity.queue.add(stringRequest);
        }
    }
}
