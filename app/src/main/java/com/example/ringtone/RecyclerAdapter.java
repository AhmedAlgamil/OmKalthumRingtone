package com.example.ringtone;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import static butterknife.ButterKnife.bind;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomRecyclerViewHolder> {

    Activity activity;
    MediaPlayer mediaPlayer;
    public static ProgressDialog checkDialog;
    File k;

    int OPERATIONS = 0;

    int position = 0;

    List<RingTone> list;

    public RecyclerAdapter(List<RingTone> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @RequiresApi ( api = Build.VERSION_CODES.M )
    @NonNull
    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        bind(this,view);
        permissionSettings();
        return new CustomRecyclerViewHolder(view);
    }

    @RequiresApi ( api = Build.VERSION_CODES.M )
    public void permissionSettings()
    {
        boolean settingsCanWrite = Settings.System.canWrite(activity.getApplicationContext ());
        if(!settingsCanWrite)
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
            activity.startActivity(intent);
        }
        else
        {

        }

    }

    @RequiresApi ( api = Build.VERSION_CODES.M )
    @Override
    public void onBindViewHolder(@NonNull CustomRecyclerViewHolder holder, int selectedPosition) {
        holder.ringtoneName.setText(list.get(selectedPosition).name);
        holder.startMusic.setOnClickListener( v -> {
            position = selectedPosition;
            makePlayAndDownload2 (holder,list.get ( selectedPosition ).url );
        } );

        holder.setAsRington.setOnClickListener( v ->
        {
            position = selectedPosition;
            OPERATIONS = 1;
            makeDownload (list.get ( selectedPosition ).url);
        } );

        holder.setAsNotification.setOnClickListener ( v->
        {
            position = selectedPosition;
            OPERATIONS = 2;
            makeDownload (list.get ( selectedPosition ).url);
        } );

    }
    
    @RequiresApi ( api = Build.VERSION_CODES.M )
    public void makePlayAndDownload( CustomRecyclerViewHolder holder , String url)
    {
        showProgressDialog(activity,activity.getResources ().getString ( R.string.download_play ));
        FileLoader.with(activity)
                .load(url)
                .fromDirectory("RingTone Downloads",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @RequiresApi ( api = Build.VERSION_CODES.O )
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        k = response.getDownloadedFile ( );

                        if ( k.exists ( ) ) {
                            dismissProgressDialog ( activity );

                            if ( holder.startMusic.getTooltipText ( ).toString ( ).equals ( activity.getResources ().getString ( R.string.play ) ) ) {
                                try {
                                    mediaPlayer = new MediaPlayer ( );
                                    mediaPlayer.setDataSource ( k.getAbsolutePath () );
                                    mediaPlayer.prepare ( );
                                } catch ( IOException e ) {
                                    e.printStackTrace ( );
                                }
                                holder.startMusic.setImageResource ( R.drawable.pause );
                                mediaPlayer.start ( );
                                holder.startMusic.setTooltipText ( activity.getResources ().getString ( R.string.pause ) );
                            } else {
                                holder.startMusic.setImageResource ( R.drawable.play );
                                mediaPlayer.pause ( );
                                holder.startMusic.setTooltipText ( activity.getResources ().getString ( R.string.play ) );
                            }
                        }

                        }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {

                    }
                });

    }

    @RequiresApi ( api = Build.VERSION_CODES.M )
    public void makePlayAndDownload2( CustomRecyclerViewHolder holder , String url)
    {
        showProgressDialog(activity,activity.getResources ().getString ( R.string.download_play ));
        FileLoader.with(activity)
                .load(url)
                .fromDirectory("RingTone Downloads",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile(new FileRequestListener<File>() {
                    @RequiresApi ( api = Build.VERSION_CODES.O )
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        k = response.getDownloadedFile ( );

                        if ( k.exists ( ) ) {
                            dismissProgressDialog ( activity );
                            if(mediaPlayer != null)
                            {
                                mediaPlayer.stop ();
                                mediaPlayer.release ();
                                mediaPlayer = null;
                                holder.startMusic.setImageResource ( R.drawable.play );
                                holder.startMusic.setTooltipText ( activity.getResources ().getString ( R.string.play ) );
                            }
                            else
                            {
                                try {
                                    mediaPlayer = new MediaPlayer ( );
                                    mediaPlayer.setDataSource ( k.getAbsolutePath () );
                                    mediaPlayer.prepare ( );
                                } catch ( IOException e ) {
                                    e.printStackTrace ( );
                                }
                                holder.startMusic.setImageResource ( R.drawable.pause );
                                mediaPlayer.start ( );
                                holder.startMusic.setTooltipText ( activity.getResources ().getString ( R.string.pause ) );

                            }

                        }

                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {

                    }
                });

    }



    public void setOperation(int mediaplayerOpr)
    {
        if(mediaplayerOpr == 1)
        {

        }
    }

    @Override
    public void onBindViewHolder ( @NonNull CustomRecyclerViewHolder holder , int position , @NonNull List < Object > payloads ) {
        super.onBindViewHolder ( holder , position , payloads );
    }

    public void makeDownload( String url)
    {
        showProgressDialog(activity,activity.getResources ().getString ( R.string.download_play ));
        FileLoader.with(activity)
                .load(url)
                .fromDirectory("RingTone Downloads",FileLoader.DIR_EXTERNAL_PUBLIC)
                .asFile ( new FileRequestListener < File > ( ) {
                    @Override
                    public void onLoad ( FileLoadRequest request , FileResponse < File > response ) {
                        File loaded = response.getBody ();
                        if ( loaded.exists () ) {
                            dismissProgressDialog ( activity );
                            if(OPERATIONS == 1)
                            {
                                setRingtone ( activity , loaded.getAbsolutePath () );
                            }
                            else if(OPERATIONS == 2)
                            {
                                setNotificationRingtone ( activity , loaded.getAbsolutePath () );
                            }
                        }
                        else {

                        }
                    }

                    @Override
                    public void onError ( FileLoadRequest request , Throwable t ) {
                        createAlertDialog ( activity.getResources ().getString ( R.string.error_title ) , activity.getResources ().getString ( R.string.error_connection ) , android.R.drawable.stat_notify_error );
                    }
                } );
    }

    private void setRingtone(Context context, String path) {
        if (path == null) {
            return;
        }
        File file = new File(path);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
        contentValues.put(MediaStore.MediaColumns.TITLE, file.getName ());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        contentValues.put(MediaStore.MediaColumns.SIZE, file.length(  ) );
        contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
        Cursor cursor = context.getContentResolver().query(uri, null, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            String id = cursor.getString(0);
            contentValues.put(MediaStore.Audio.Media.IS_RINGTONE, true);
            context.getContentResolver().update(uri, contentValues, MediaStore.MediaColumns.DATA + "=?", new String[]{path});
            Uri newuri = ContentUris.withAppendedId(uri, Long.valueOf(id));
            try {
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newuri);
                Toast.makeText(context, activity.getResources ().getString ( R.string.ringtone_successful ) + list.get ( position ).name , Toast.LENGTH_SHORT).show();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            cursor.close();
        }
    }
    private void setNotificationRingtone( Context context, String path) {
        if (path == null) {
            return;
        }
        File file = new File(path);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
        contentValues.put(MediaStore.MediaColumns.TITLE, file.getName ());
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
        contentValues.put(MediaStore.MediaColumns.SIZE, file.length());
        contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
        Uri uri = MediaStore.Audio.Media.getContentUriForPath(path);
        Cursor cursor = context.getContentResolver().query(uri, null, MediaStore.MediaColumns.DATA + "=?", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            String id = cursor.getString(0);
            contentValues.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
            context.getContentResolver().update(uri, contentValues, MediaStore.MediaColumns.DATA + "=?", new String[]{path});
            Uri newuri = ContentUris.withAppendedId(uri, Long.valueOf(id));
            try {
                RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION, newuri);
                Toast.makeText(context, activity.getResources ().getString ( R.string.ringtone_successful ) + list.get ( position ).name , Toast.LENGTH_SHORT).show();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            cursor.close();
        }
    }

    public void createAlertDialog(String title,String message,int res)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle ( title );
        alertDialog.setMessage(message);
        alertDialog.setIcon ( res );
        alertDialog.setButton ( DialogInterface.BUTTON_POSITIVE , activity.getString ( R.string.ok ) , new DialogInterface.OnClickListener ( ) {
            @Override
            public void onClick ( DialogInterface dialog , int which ) {
                alertDialog.dismiss ();
            }
        } );
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static void showProgressDialog(Activity activity, String message) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setMessage(message);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable ( false );
            checkDialog.show();

        } catch (Exception e) {

        }
    }

    public static void dismissProgressDialog(Activity activity)
    {
        checkDialog.dismiss();
    }

    public class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.circle_im_view)
        CircleImageView circleImView;
        @BindView(R.id.ringtone_name)
        TextView ringtoneName;
        @BindView(R.id.start_music)
        FloatingActionButton startMusic;
        @BindView(R.id.set_as_rington)
        FloatingActionButton setAsRington;
        @BindView(R.id.set_as_notification)
        FloatingActionButton setAsNotification;

        public CustomRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            bind(this,itemView);
        }



    }

}