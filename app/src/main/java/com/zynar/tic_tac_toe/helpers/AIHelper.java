package com.zynar.tic_tac_toe.helpers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AIHelper {
    private static final int MIN_NUMBER = -10000;
    private static final int MAX_NUMBER = 10000;
    private final GameStateHelper gameStateHelper = new GameStateHelper();
    public int[] getNextMove(int[][] board) {
        int maxEval = MIN_NUMBER;
        int[] move = new int[2];

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int randomNum = random.nextInt(100);
        List<List<Integer>> emptyList = new ArrayList<>();

        boolean flag = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            if (randomNum >= 60) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == 0) {
                            emptyList.add(Arrays.asList(i, j));
                        }
                    }
                }
                int index = random.nextInt(emptyList.size()-1);
                move[0] = emptyList.get(index).get(0);
                move[1] = emptyList.get(index).get(1);
                emptyList.clear();
            }
            else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == 0) {
                            board[i][j] = 2;
                            int eval = minimax(board, 0, false);
                            board[i][j] = 0;
                            if (eval > maxEval) {
                                maxEval = eval;
                                move[0] = i;
                                move[1] = j;
                            }
                        }
                    }
                }
            }
        }
        else {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        int eval = minimax(board, 0, false);
                        board[i][j] = 0;
                        if (eval > maxEval) {
                            maxEval = eval;
                            move[0] = i;
                            move[1] = j;
                        }
                    }
                }
            }
        }
        return move;
    }
    public int minimax(int[][] board, int depth, boolean isMax) {
        if (gameStateHelper.check(board) == 0) {
            return 1;
        } else if (gameStateHelper.check(board) == 1) {
            return -1;
        } else if (gameStateHelper.isFully(board)) {
            return 0;
        }

        if (isMax) {
            int maxEval = MIN_NUMBER;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 2;
                        int eval = minimax(board, depth + 1, false);
                        board[i][j] = 0;
                        maxEval = Math.max(maxEval, eval);
                    }
                }
            }
            return maxEval;
        }
        else {
            int minEval = MAX_NUMBER;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = 1;
                        int eval = minimax(board, depth + 1, true);
                        board[i][j] = 0;
                        minEval = Math.min(minEval, eval);
                    }
                }
            }
            return minEval;
        }
    }
}
