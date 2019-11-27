package com.jonathanfeitosa.getapplicationerror;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static ScheduledExecutorService scheduledExecutorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeLogInfo(this);
        forceCloseApplication2();


    }


    public static void initializeLogInfo(Context context){

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            Log.i("Resultado",  "Teste ");


        }, 1, 2, TimeUnit.SECONDS);
    }

    public static void forceCloseApplication(){
        throw new NullPointerException();
    }

    public static void forceCloseApplication2(){
        int num = 1;

        num = num/0;

        Log.i("ResultadoJFS", "" + num);

    }


    private String getErrorMessgae(Throwable e) {
        StackTraceElement[] stackTrackElementArray = e.getStackTrace();
        String crashLog = e.toString() + "\n\n";
        crashLog += "--------- Stack trace ---------\n\n";
        for (int i = 0; i < stackTrackElementArray.length; i++) {
            crashLog += "    " + stackTrackElementArray[i].toString() + "\n";
        }
        crashLog += "-------------------------------\n\n";

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        crashLog += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            crashLog += cause.toString() + "\n\n";
            stackTrackElementArray = cause.getStackTrace();
            for (int i = 0; i < stackTrackElementArray.length; i++) {
                crashLog += "    " + stackTrackElementArray[i].toString()
                        + "\n";
            }
        }
        crashLog += "-------------------------------\n\n";
        return crashLog;
    }

}
