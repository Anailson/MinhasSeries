package ex_tep.minhasseries.tratamentos;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Anailson on 04/08/2015.
 */
public class TratamentoAlarme extends BroadcastReceiver {

    private static final int REPETICAO = 10000;
    private static int count = 0;
    private static List<PendingIntent> intents = new ArrayList<>();
    private static AlarmManager alarmManager;
    private static MediaPlayer player;

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "ALARME ATIVADO", Toast.LENGTH_SHORT).show();
        if(intents.size() == 0) {
            tocarAlarme(context);
        }
    }

    public static void novoAlarme(Context context, Calendar calendar) {

        PendingIntent pendingIntent;

        Intent intent = new Intent(context, TratamentoAlarme.class);
        pendingIntent = PendingIntent.getBroadcast(context, count++, intent, PendingIntent.FLAG_ONE_SHOT);

        alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), REPETICAO, pendingIntent);

        intents.add(pendingIntent);
        Log.e("SIZE", intents.size() + "");
    }

    public static void cancelarAlarme() {

        if (intents.size() > 0){
            int index =intents.size() -1;
            alarmManager.cancel(intents.get(index));
            intents.remove(index);

            Log.e("SIZE", intents.size() + "");
        }
        if(intents.size() == 0 ) {
            player.stop();
        }
    }

    private void tocarAlarme(Context context) {

        player = new MediaPlayer();
        Uri alarmUri = getAlarmURI();

        try {

            player.setDataSource(context, alarmUri);
            AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if (manager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
                player.setAudioStreamType(AudioManager.STREAM_ALARM);
                player.prepare();
                player.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Uri getAlarmURI() {

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        /**
         if (uri != null) {
         uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         if (uri != null) {
         uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
         }
         }
         */
        return uri;
    }
}