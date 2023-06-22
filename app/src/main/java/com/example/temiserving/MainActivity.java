package com.example.temiserving;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robotemi.sdk.Robot;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private Robot robot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Temi SDK 초기화
        robot = Robot.getInstance();
        DatabaseReference serveRef = firebaseDatabase.getReference("table_loc");

        Button returnButton = (Button) findViewById(R.id.ReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serveRef.setValue(0);
            }
        });

        serveRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int status = (int) snapshot.getValue(Integer.class);
                // 0 : 주방으로 1~6 : 각 테이블로 서빙
                switch (status) {
                    case 0:
                        robot.goTo("0");
                        break;

                    case 1:
                        robot.goTo("1");
                        break;

                    case 2:
                        robot.goTo("2");
                        break;

                    case 3:
                        robot.goTo("3");
                        break;

                    case 4:
                        robot.goTo("4");
                        break;

                    case 5:
                        robot.goTo("5");
                        break;

                    case 6:
                        robot.goTo("6");
                        break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
