package com.zynar.tic_tac_toe.helpers;

public class GameStateHelper {
    public int check(int[][] board) {
        // 가로, 세로, 대각선 방향에 대한 승리 조건 검사
        for (int i = 0; i < 3; i++) {
            // 가로 방향 검사
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 1) {
                    System.out.println("You win!");
                    return 1;
                } else if (board[i][0] == 2) {
                    System.out.println("You lose...");
                    return 0;
                }
            }

            // 세로 방향 검사
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 1) {
                    System.out.println("You win!");
                    return 1;
                } else if (board[0][i] == 2) {
                    System.out.println("You lose...");
                    return 0;
                }
            }
        }

        // 대각선 방향 검사
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2]) || (board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
            if (board[1][1] == 1) {
                System.out.println("You win!");
                return 1;
            } else if (board[1][1] == 2) {
                System.out.println("You lose...");
                return 0;
            }
        }

        return -1;
    }
}
