package com.covid19.coronarg;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.covid19.coronarg.fragments.MainFragment;
import com.covid19.coronarg.fragments.MainFragmentTab;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    private DrawerLayout drawer;
    private TextView appBarTV;
    TextView nameText, emailText, phoneText;
    DatabaseReference reference;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarTV = findViewById(R.id.appbar_text_view);

        //시작할 때 바로 안전정보 확인
        appBarTV.setText("코로나19 안전정보");
        MainFragmentTab fragmentTab = new MainFragmentTab();
        ft.replace(R.id.f_container, fragmentTab);
        ft.commit();

        ImageButton menuRight = findViewById(R.id.leftRight);
        //drawer.openDrawer(GravityCompat.START);
        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        nameText = header.findViewById(R.id.name_text);
        emailText = header.findViewById(R.id.email_text);
        phoneText = header.findViewById(R.id.phone_text);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                    nameText.setText(name);
                    emailText.setText(email);
                    phoneText.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawers();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            appBarTV.setText("코로나19 안전정보");
            MainFragmentTab fragmentTab = new MainFragmentTab();
            ft.replace(R.id.f_container, fragmentTab);
            ft.commit();

        } else if (id == R.id.nav_gallery) {
            appBarTV.setText("프로필");
            MainFragment fragment = new MainFragment();
            ft.replace(R.id.f_container, fragment);
            ft.commit();

        } else if (id == R.id.nav_share) {
            appBarTV.setText("로그아웃");
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), com.covid19.coronarg.Login.class);

            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}