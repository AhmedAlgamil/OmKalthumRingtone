package com.example.ringtone;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.ringtone.HelpingMethods.onPermission;

public class MainActivity extends AppCompatActivity {

    final float STANDARDPIX = - 120f, STANDARDPIXZERO = 0f;
    final int duration = 500;

    @BindView ( R.id.recycler_view_ring_tone )
    RecyclerView recyclerViewRingTone;
    @BindView ( R.id.facebook )
    ImageView facebook;
    @BindView ( R.id.youtube )
    ImageView youtube;
    @BindView ( R.id.twitter )
    ImageView twitter;
    @BindView ( R.id.insta )
    ImageView insta;
    @BindView ( R.id.about_me )
    FloatingActionButton aboutMe;

    List < RingTone > ringToneslist;

    RecyclerAdapter recyclerAdapter;
    @BindView ( R.id.rate_us )
    ImageView rateUs;
    @BindView ( R.id.adView )
    AdView adView;

    @RequiresApi ( api = Build.VERSION_CODES.O )
    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        ButterKnife.bind ( this );
        onPermission ( this );
        showAdmob();
        makeCloseAboutMe ( );
        init ( );
        makeRecyclerView ( );

    }

    public void showAdmob()
    {
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adView.loadAd(adRequest2);

    }

    public void init ( ) {
        ringToneslist = new ArrayList <> ( );
        ringToneslist.add ( new RingTone ( "ازاي ازاي" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%a7%d8%b2%d8%a7%d9%8a%20%d8%a7%d8%b2%d8%a7%d9%8a.mp3" ) );
        ringToneslist.add ( new RingTone ( "الحب كله حبيته ليه" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%a7%d9%84%d8%ad%d8%a8%20%d9%83%d9%84%d9%87.mp3" ) );
        ringToneslist.add ( new RingTone ( "انت خليتني اعيش الحب وياك الف حب" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%a7%d9%86%d8%aa%20%d8%ae%d9%84%d8%aa%d9%86%d9%8a%20%d8%a7%d8%b9%d9%8a%d8%b4%20%d8%a7%d9%84%d8%ad%d8%a8%20%d9%88%d9%8a%d8%a7%d9%83%20%d8%a7%d9%84%d9%81%20%d8%ad%d8%a8.mp3" ) );
        ringToneslist.add ( new RingTone ( "بعيد عنك" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%a8%d8%b9%d9%8a%d8%af%20%d8%b9%d9%86%d9%83.mp3" ) );
        ringToneslist.add ( new RingTone ( "حيرت قلبي معاك" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%ad%d9%8a%d8%b1%d8%aa%20%d9%82%d9%84%d8%a8%d9%8a%20%d9%85%d8%b9%d8%a7%d9%83.mp3" ) );
        ringToneslist.add ( new RingTone ( "الهوا غلاب" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d8%b5%d8%ad%d9%8a%d8%ad%20%d8%a7%d9%84%d9%87%d9%88%d8%a7%20%d8%ba%d9%84%d8%a7%d8%a8.mp3" ) );
        ringToneslist.add ( new RingTone ( "فات من عمري سنين" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d9%81%d8%a7%d8%aa%20%d9%85%d9%86%20%d8%b9%d9%85%d8%b1%d9%8a%20%d8%b3%d9%86%d9%8a%d9%86.mp3" ) );
        ringToneslist.add ( new RingTone ( "قبل ما تشوفك عنيا" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d9%82%d8%a8%d9%84%20%d9%85%d8%a7%20%d8%aa%d8%b4%d9%88%d9%81%d9%83%20%d8%b9%d9%86%d9%8a%d8%a7.mp3" ) );
        ringToneslist.add ( new RingTone ( "بعد حين يبدل الحب تارة" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d9%87%d8%b0%d9%87%20%d9%84%d9%8a%d9%84%d8%aa%d9%8a%20%d9%88%d8%ad%d9%84%d9%85%20%d8%ad%d9%8a%d8%a7%d8%aa%d9%8a.mp3" ) );
        ringToneslist.add ( new RingTone ( "يا سلام على الدنيا" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d9%8a%d8%a7%20%d8%b3%d9%84%d8%a7%d9%85%20%d8%b9%d9%84%d9%89%20%d8%a7%d9%84%d8%af%d9%86%d9%8a%d8%a7.mp3" ) );
        ringToneslist.add ( new RingTone ( "يللا نعيش في عيون اليل" , "https://customringtone.000webhostapp.com/downloads/om_kalthom/%d9%8a%d9%84%d9%84%d8%a7%20%d9%86%d8%b9%d9%8a%d8%b4.mp3" ) );
    }

    public void makeRecyclerView ( ) {
        recyclerViewRingTone.setHasFixedSize ( true );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( this );
        recyclerViewRingTone.setLayoutManager ( layoutManager );
        recyclerAdapter = new RecyclerAdapter ( ringToneslist , this );
        recyclerViewRingTone.setAdapter ( recyclerAdapter );
    }

    @RequiresApi ( api = Build.VERSION_CODES.O )
    public void makeCloseAboutMe ( ) {
        facebook.animate ( ).translationX ( STANDARDPIXZERO ).start ( );
        facebook.animate ( ).setDuration ( duration ).translationX ( STANDARDPIXZERO ).start ( );
        insta.animate ( ).setDuration ( duration ).translationX ( STANDARDPIXZERO ).start ( );
        youtube.animate ( ).setDuration ( duration ).translationX ( STANDARDPIXZERO ).start ( );
        twitter.animate ( ).setDuration ( duration ).translationX ( STANDARDPIXZERO ).start ( );
        rateUs.animate ( ).setDuration ( duration ).translationY ( STANDARDPIXZERO ).start ( );

        aboutMe.setTooltipText ( getResources ( ).getString ( R.string.about_me ) );
    }


    @RequiresApi ( api = Build.VERSION_CODES.O )
    public void makeOpenAboutMe ( ) {
        facebook.animate ( ).setDuration ( duration ).translationX ( STANDARDPIX  ).start ( );
        rateUs.animate ( ).setDuration ( duration ).translationY ( STANDARDPIX ).start ( );
        insta.animate ( ).setDuration ( duration ).translationX ( 2 * STANDARDPIX ).start ( );
        youtube.animate ( ).setDuration ( duration ).translationX ( 3 * STANDARDPIX ).start ( );
        twitter.animate ( ).setDuration ( duration ).translationX ( 4 * STANDARDPIX ).start ( );
        aboutMe.setTooltipText ( getResources ( ).getString ( R.string.close ) );
    }

    @RequiresApi ( api = Build.VERSION_CODES.O )
    @OnClick ( { R.id.facebook , R.id.youtube , R.id.twitter , R.id.insta , R.id.about_me } )
    public void onViewClicked ( View view ) {
        switch ( view.getId ( ) ) {
            case R.id.facebook:
                openUrl ( "fb://www.facebook.com/eng.ahmed.5811877" );
                break;
            case R.id.youtube:
                openUrl ( "https://www.youtube.com/channel/UC7lZXUGd1ODOHtoRzu48Tfg" );
                break;
            case R.id.twitter:
                openUrl ( "https://twitter.com/ahmedalgamil231" );
                break;
            case R.id.insta:
                openUrl ( "https://www.instagram.com/ahmed_algamil/?hl=en" );
                break;
            case R.id.about_me:
                if ( aboutMe.getTooltipText ( ).equals ( getResources ( ).getString ( R.string.close ) ) ) {
                    makeCloseAboutMe ( );
                } else {
                    makeOpenAboutMe ( );
                }

                break;
        }
    }

    public void openUrl ( String Url ) {
        Uri uri = Uri.parse ( Url );
        Intent intent = new Intent ( Intent.ACTION_VIEW , uri );
        startActivity ( intent );
    }

    @OnClick ( R.id.rate_us )
    public void onClick ( ) {
//        showAdmob();
    }
}

