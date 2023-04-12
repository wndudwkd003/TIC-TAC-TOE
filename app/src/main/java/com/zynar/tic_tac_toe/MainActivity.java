package com.zynar.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.zynar.tic_tac_toe.databinding.ActivityMainBinding;
import com.zynar.tic_tac_toe.helpers.GameStateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isUserTurn = true;
    private final int[][] board = new int[3][3];
    private TextView[][] textViews;
    private final GameStateHelper gameStateHelper = new GameStateHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setClickListener(binding); // 클릭 리스너 등록

        textViews = new TextView[][]{
                {binding.tv11, binding.tv12, binding.tv13},
                {binding.tv21, binding.tv22, binding.tv23},
                {binding.tv31, binding.tv32, binding.tv33}
        };


        binding.fabRetry.setOnClickListener(v->{
            gameReStart();
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView textView = findViewById(id);
        if (isUserTurn) {
            textView.setText("O");
        } else {
            textView.setText("X");
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextView tv = textViews[i][j];
                if (textView.equals(tv)) {
                    if (isUserTurn) {
                        board[i][j] = 1;
                    } else {
                        board[i][j] = 2;
                    }
                    break;
                }
            }
        }

        isUserTurn = !isUserTurn;

        int result = gameStateHelper.check(board);
        if (result != -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("결과");

            builder.setPositiveButton("확인", (dialog, which) -> {
                gameReStart();
            });

            if (result == 1) {
                builder.setMessage("게임에서 승리하셨습니다!\n재시작하시겠습니까?");
                builder.show();
            } else if (result == 0){
                builder.setMessage("게임에서 패배하셨습니다ㅠㅠ\n재시작하시겠습니까?");
                builder.show();
            }
        }
    }

    private void setClickListener(ActivityMainBinding binding) {
        binding.tv11.setOnClickListener(this);
        binding.tv12.setOnClickListener(this);
        binding.tv13.setOnClickListener(this);
        binding.tv21.setOnClickListener(this);
        binding.tv22.setOnClickListener(this);
        binding.tv23.setOnClickListener(this);
        binding.tv31.setOnClickListener(this);
        binding.tv32.setOnClickListener(this);
        binding.tv33.setOnClickListener(this);
    }

    private void gameReStart() {
        TextView[] flatTextViews = Arrays.stream(textViews).flatMap(Arrays::stream).toArray(TextView[]::new);
        Arrays.stream(flatTextViews).forEach(textView -> textView.setText(""));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        isUserTurn = true;
    }
}