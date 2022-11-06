package dev.technologies.rgbtohexcolorconverter.telhexcode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Color_pick_wheel colorpicker_fragment = new Color_pick_wheel();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, (Fragment)colorpicker_fragment).commit();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.rate_my_app:
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                }
                catch (ActivityNotFoundException e){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                }
                return true;

            case R.id.privacy_policy:
                Intent my_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/telhexcode"));
                startActivity(my_intent);
                return true;

            case R.id.share_my_app:
                String string2 = getResources().getString(R.string.share_1);
                String string3 = getResources().getString(R.string.share_2) + " \n" + "https://play.google.com/store/apps/details?id" + getPackageName();
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", string2);
                intent.putExtra("android.intent.extra.TEXT", string3);
                startActivity(Intent.createChooser((Intent)intent, null));

            default:
                return super.onOptionsItemSelected(item);

        }
    }


}