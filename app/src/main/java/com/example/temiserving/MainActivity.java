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
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener;


public class MainActivity extends AppCompatActivity{

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private Robot robot;
    DatabaseReference test = firebaseDatabase.getReference("test");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Temi SDK 초기화
        robot = Robot.getInstance();
        MyMovementStatusChangedListener listener = new MyMovementStatusChangedListener();
        robot.addOnMovementStatusChangedListener(listener);
        DatabaseReference serveRef = firebaseDatabase.getReference("table_loc");

        Button returnButton = (Button) findViewById(R.id.ReturnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serveRef.setValue(0);
            }
        });


        robot.addOnSequencePlayStatusChangedListener(new OnSequencePlayStatusChangedListener() {
            @Override
            public void onSequencePlayStatusChanged(int i) {
                if(i == 0){
                    robot.playSequence("Move");
                    robot.speak(TtsRequest.create("할 일을 주세요"));
                }
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
                        robot.speak(TtsRequest.create("도착했습니다"));
                        test.setValue(0);

                        break;

                    case 1:
                        robot.goTo("1");
                        robot.speak(TtsRequest.create("도착했습니다"));
                        robot.playSequence("");
                        test.setValue(1);
                        break;

                    case 2:
                        robot.goTo("2");
                        robot.speak(TtsRequest.create("도착했습니다"));

                        break;

                    case 3:
                        robot.goTo("3");
                        robot.speak(TtsRequest.create("도착했습니다"));

                        break;

                    case 4:
                        robot.goTo("4");
                        robot.speak(TtsRequest.create("도착했습니다"));

                        break;

                    case 5:
                        robot.goTo("5");
                        robot.speak(TtsRequest.create("도착했습니다"));

                        break;
                    case 6:
                        robot.goTo("6");
                        robot.speak(TtsRequest.create("도착했습니다"));
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
//    @Override
//    public void onMovementStatusChanged(@OnMovementStatusChangedListener.Type String type, @OnMovementStatusChangedListener.Status @NonNull String status) {
//        if (type.equals(OnMovementStatusChangedListener.TYPE_SKID_JOY) && status.equals(OnMovementStatusChangedListener.STATUS_COMPLETE)) {
//            // 로봇의 이동이 완료되었을 때 실행할 작업을 여기에 구현합니다.
//            // 예를 들어, 이동 완료 메시지 출력
//            System.out.println("로봇의 이동이 완료되었습니다.");
//            test.setValue(1000);
//        }
//    }

}
