package ro.jademy.millionaire.data;

import ro.jademy.millionaire.model.Answer;
import ro.jademy.millionaire.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class QuestionProvider {

    public static List<Question> ALL_QUESTIONS = Arrays.asList(
            new Question("Question 1?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twooooooooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 2?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twoooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 3?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twoooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 4?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twooooooooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 5?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twoooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 6?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 7?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twoooooooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 0),
            new Question("Question 8?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Twooooooo"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 9?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 10?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 11?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 12?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 13?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 14?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 1),
            new Question("Question 15?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 16?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 17?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 18?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 19?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 20?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 21?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 22?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 2),
            new Question("Question 23?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 3),
            new Question("Question 24?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 3),
            new Question("Question 25?", Arrays.asList(
                    new Answer("One"),
                    new Answer("Two"),
                    new Answer("Three")
            ), new Answer("Four"), 3)
    );


    public static List<Question> getRandomQuestions(int nrOfQuestions, int difficulty) {
        // loop through all questions
        // get all questions of given difficulty
        // loop through sub-list until nrOfQuestions and select random items by index
        // return said list
        List<Question> questionsOfDiff = new ArrayList<>();
        List<Question> questionsRandom = new ArrayList<>();
        for (Question question : QuestionProvider.ALL_QUESTIONS) {
            if (question.getDifficulty() == difficulty) {
                questionsOfDiff.add(question);
            }
        }
        Random random = new Random();
        for (int i = 0; i < nrOfQuestions; i++) {
            int randomIndex = random.nextInt(questionsOfDiff.size());
            questionsRandom.add(questionsOfDiff.remove(randomIndex));
        }
        return questionsRandom;
    }


}
