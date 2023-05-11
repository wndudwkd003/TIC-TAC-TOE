package com.zynar.tic_tac_toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zynar.tic_tac_toe.databinding.ActivityMainBinding;
import com.zynar.tic_tac_toe.helpers.AIHelper;
import com.zynar.tic_tac_toe.helpers.GameStateHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int[][] board = new int[3][3];    // 실제 틱택토 보드 배열
    private TextView[][] textViews; // 틱택토가 그려질 배열
    private final GameStateHelper gameStateHelper = new GameStateHelper();  // 보드의 상태 체크하는 헬퍼
    private int count = 0;  // 현재 수 카운트
    private final AIHelper aiHelper = new AIHelper();   // AI 헬퍼
    private boolean isUserTurn = true;  // 유저 턴 판별 플래그
    public interface AiTurnListener {
        void onAiTurn();
    }

    private AiTurnListener aiTurnListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setClickListener(binding); // 클릭 리스너 등록

        // 행렬에 각각의 주소 저장
        textViews = new TextView[][]{
                {binding.tv11, binding.tv12, binding.tv13},
                {binding.tv21, binding.tv22, binding.tv23},
                {binding.tv31, binding.tv32, binding.tv33}
        };

        // 다시하기 버튼
        binding.fabRetry.setOnClickListener(v->{
            gameReStart();
        });

        // AI 턴 리스너 AI 턴 실행
        aiTurnListener = () -> {
            int[] move = aiHelper.getNextMove(board);   // AI가 생각한 최적의 수를 반환 move[0] = x, move[1] = y
            TextView tv = textViews[move[0]][move[1]];
            tv.setText("O");    // AI가 입력한 칸에 O 입력
            board[move[0]][move[1]] = 2;
        };
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView textView = findViewById(id);

        // 터치한곳의 배열 좌표값을 배열에 입력
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                TextView tv = textViews[i][j];
                if (textView.equals(tv)) {
                    // 이미 선택되어 있는 칸을 클릭했을 때 온클릭 메서드 탈출
                    if (board[i][j] != 0) {
                        Toast.makeText(this, "이미 선택되어 있는 칸입니다!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        // 유저 턴 실행
                        userTurnPlay(textView, i, j);
                        break;
                    }
                }
            }
        }
    }

    private void userTurnPlay(TextView textView, int i, int j) {
        board[i][j] = 1;
        textView.setText("X");
        count++;    // 현재 카운트 증가
        isUserTurn = false; // 플레이어 수를 뒀기 때문에 턴 변경
        gameStateCheck(gameStateHelper.check(board)); // 현재 게임 상태 체킹

        // AI 턴일경우 아래 조건문 실행
        if (!isUserTurn) {
            aiTurnListener.onAiTurn();  // AI가 둘 수를 결정
            count++;    // 현재 카운트 증가
            gameStateCheck(gameStateHelper.check(board)); // 현재 게임 상태 체킹
            isUserTurn = true;  // 플레이어 턴으로 변경
        }
    }

    private void gameStateCheck(int result) {
        if (result != -1) {
            // 결과 창 생성
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("결과");

            // 확인 버튼 생성
            builder.setPositiveButton("확인", (dialog, which) -> {
                gameReStart();
            });

            // 취소 버튼 생성
            builder.setNegativeButton("취소", (dialog, which) -> {});

            // result 1은 인간이 이겼을 경우, 0은 AI가 이겼을 경우
            if (result == 1) {
                builder.setMessage("게임에서 승리하셨습니다!\n재시작하시겠습니까?");
                builder.show();
            } else if (result == 0){
                builder.setMessage("게임에서 패배하셨습니다ㅠㅠ\n재시작하시겠습니까?");
                builder.show();
            }
            isUserTurn = true;  // 게임 결과가 나왔음
        } else {
            // 카운트가 9면 보드가 꽉 찼음 비긴거
            if (count == 9) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("결과");
                builder.setMessage("게임에서 비겼습니다!\n재시작하시겠습니까?");
                builder.setPositiveButton("확인", (dialog, which) -> {
                    gameReStart();
                });
                builder.setNegativeButton("취소", (dialog, which) -> {});
                builder.show();
                isUserTurn = true;  // 게임 결과가 나왔음
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
        // 리스타트
        TextView[] flatTextViews = Arrays.stream(textViews).flatMap(Arrays::stream).toArray(TextView[]::new);
        Arrays.stream(flatTextViews).forEach(textView -> textView.setText(""));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
        count = 0;
        isUserTurn = true;
    }
}