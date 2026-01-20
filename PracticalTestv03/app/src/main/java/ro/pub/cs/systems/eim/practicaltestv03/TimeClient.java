package ro.pub.cs.systems.eim.practicaltestv03;

import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class TimeClient extends Thread {
    private Context context;
    private String serverIp;

    public TimeClient(String serverIp, Context context) {
        this.serverIp = serverIp;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverIp, 5000);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            String time = reader.readLine();
            socket.close();

            Intent intent = new Intent("ro.pub.cs.systems.eim.practicaltestv03.TIME_ACTION");
            intent.putExtra("time", time);
            context.sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}