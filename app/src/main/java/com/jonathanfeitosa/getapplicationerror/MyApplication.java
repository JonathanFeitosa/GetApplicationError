package com.jonathanfeitosa.getapplicationerror;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {

    private Thread.UncaughtExceptionHandler defaultUEH;

    public MyApplication() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

        // Manipulador da Exceção Capturada
        Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
    }

    // Ouvinte
    private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {

            Log.i("ResultadoJFS", "ENTROU");
            Log.i("ResultadoJFS", getErrorMessgae(ex));


            // re-throw critical exception further to the os (important) to handle
            defaultUEH.uncaughtException(thread, ex);
        }
    };

    private String getErrorMessgae(Throwable e) {

        StackTraceElement[] stackTrackElementArray = e.getStackTrace();
        String crashLog = e.toString() + "\n\n";

        // Erro Geral
        crashLog += "--------- Rastreio do Erro ---------\n\n";
        for (int i = 0; i < stackTrackElementArray.length; i++) {
            crashLog += "    " + stackTrackElementArray[i].toString() + "\n";
        }
        crashLog += "-------------------------------\n\n";

        // Caso o erro aconteça em uma Thread AsyncTask, a exceção pode ser encontrado em getCause();
        crashLog += "--------- Causa ---------\n\n";
        Throwable cause = e.getCause();

        if (cause != null) {

            // Erro Geral
            crashLog += cause.toString() + "\n\n";

            // Causa
            stackTrackElementArray = cause.getStackTrace();
            for (int j = 0; j < stackTrackElementArray.length; j++) {
                crashLog += "    " + stackTrackElementArray[j].toString()
                        + "\n";
            }
        }
        crashLog += "-------------------------------\n\n";
        return crashLog;
    }
}





