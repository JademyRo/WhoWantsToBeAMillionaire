package ro.jademy.millionaire;

import ro.jademy.millionaire.data.QuestionProvider;
import ro.jademy.millionaire.model.Game;
import ro.jademy.millionaire.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // 1. define objects
        // define properties
        // define methods

        // 2. define interactions

        // initialize questions
        List<Question> difficultyZeroQuestions = QuestionProvider.getRandomQuestions(5, 0);
        List<Question> difficultyOneQuestions = QuestionProvider.getRandomQuestions(5, 1);
        List<Question> difficultyTwoQuestions = QuestionProvider.getRandomQuestions(4, 2);
        List<Question> difficultyThreeQuestions = QuestionProvider.getRandomQuestions(1, 3);

        // initialize game
        Game game = new Game(difficultyZeroQuestions, difficultyOneQuestions, difficultyTwoQuestions, difficultyThreeQuestions);

        // start the game


        game.start();
    }


}
