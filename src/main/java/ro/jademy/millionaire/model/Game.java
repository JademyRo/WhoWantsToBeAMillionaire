package ro.jademy.millionaire.model;

import ro.jademy.millionaire.data.QuestionProvider;

import java.sql.SQLOutput;
import java.util.*;

public class Game {

    // 15 levels
    // 4 break points
    //      -> level [1, 5]    -> difficulty 0
    //      -> level [6, 10]   -> difficulty 1
    //      -> level [11, 14]  -> difficulty 2
    //      -> level 15        -> difficulty 3

    Scanner sc = new Scanner(System.in);
    Random random = new Random();


    private static final List<Level> LEVELS = Arrays.asList(
            new Level(1, 0, 100, 0),
            new Level(2, 0, 200, 0),
            new Level(3, 0, 500, 0),
            new Level(4, 0, 700, 0),
            new Level(5, 0, 1000, 0),
            new Level(6, 1, 2000, 1000),
            new Level(7, 1, 4000, 1000),
            new Level(8, 1, 8000, 1000),
            new Level(9, 1, 16000, 1000),
            new Level(10, 1, 32000, 1000),
            new Level(11, 2, 64000, 32000),
            new Level(12, 2, 125000, 32000),
            new Level(13, 2, 250000, 32000),
            new Level(14, 2, 500000, 32000),
            new Level(15, 3, 1000000, 500000)
    );

    private List<Question> difficultyZeroQuestions = new ArrayList<>();
    private List<Question> difficultyOneQuestions = new ArrayList<>();
    private List<Question> difficultyTwoQuestions = new ArrayList<>();
    private List<Question> difficultyThreeQuestions = new ArrayList<>();

    private List<Lifeline> lifelines = new ArrayList<>();
    int indexLevel = 0;
    private Level currentLevel = LEVELS.get(indexLevel);

    public Game(List<Question> difficultyZeroQuestions, List<Question> difficultyOneQuestions, List<Question> difficultyTwoQuestions, List<Question> difficultyThreeQuestions) {
        this.difficultyZeroQuestions = difficultyZeroQuestions;
        this.difficultyOneQuestions = difficultyOneQuestions;
        this.difficultyTwoQuestions = difficultyTwoQuestions;
        this.difficultyThreeQuestions = difficultyThreeQuestions;

        lifelines.add(new Lifeline("50-50"));
        lifelines.add(new Lifeline("50-50"));
        lifelines.add(new Lifeline("50-50"));
    }

    public void start() {

        // TODO
        // show welcome screen
        // optionally: show rules (rounds, lifelines, etc) & commands

        // show current level question
        // read command from player
        //     - if lifeline -> apply lifeline
        //     - if end game -> end game
        //     - read answer -> check answer
        //               - if answer correct -> go to next level (set next level as current, etc.)
        //               - if answer incorrect -> end game (calculate end sum, show bye bye message etc.)

        boolean gameContinue = false;
        showWelcome();
        showRules(lifelines);

        do {
            currentLevel = LEVELS.get(indexLevel);
            if (currentLevel.getDifficultyLevel() == 0) {
                List<Answer> questionAnswers = askQuestion(difficultyZeroQuestions, currentLevel);
                gameContinue = answerQuestion(difficultyZeroQuestions, currentLevel, questionAnswers);

            } else if (currentLevel.getDifficultyLevel() == 1) {
                List<Answer> questionAnswers = askQuestion(difficultyOneQuestions, currentLevel);
                gameContinue = answerQuestion(difficultyOneQuestions, currentLevel, questionAnswers);

            } else if (currentLevel.getDifficultyLevel() == 2) {
                List<Answer> questionAnswers = askQuestion(difficultyTwoQuestions, currentLevel);
                gameContinue = answerQuestion(difficultyTwoQuestions, currentLevel, questionAnswers);
            } else if (currentLevel.getDifficultyLevel() == 3) {
                List<Answer> questionAnswers = askQuestion(difficultyThreeQuestions, currentLevel);
                gameContinue = answerQuestion(difficultyThreeQuestions, currentLevel, questionAnswers);
                if (gameContinue) {
                    System.out.println("CONGRATULATIONS, YOU WON : " + currentLevel.getReward() + " !!");
                    gameContinue = false;
                    break;
                }

            } else {
                System.out.println("No difficulty found for currentLevel");
            }


            if (gameContinue) {
                indexLevel++;
                System.out.println("Proceeding to next level: " + currentLevel.getNumber());
            }
        } while (gameContinue);


    }

    private void showWelcome() {
        System.out.println("||=========================================||");
        System.out.println("||               WELCOME TO                ||");
        System.out.println("|| 'WHO WANTS TO BE A BILLIONAIRE Game' !! ||");
        System.out.println("||=========================================||");
        System.out.println();
    }

    private void showRules(List<Lifeline> lifelineList) {
        boolean validInput = false;

        System.out.println("The rules: ");
        System.out.println("You will be given a question. You have 4 answer options and only 1 is correct");
        System.out.println("You have: " + lifelineList.size() + " lifelines !");
        System.out.println("Lifeline 50-50  gives you 2 answers options instead of 4, so it's easier to answer correctly !");
        System.out.println("You have 4 reward checkpoints , meaning if you answer incorrectly after the checkpoint, ");
        System.out.println("You will still get the prize from that reward checkpoint");
        System.out.println("You can see the question BEFORE deciding if you want to answer or not !");
        System.out.println("If you decide to leave, you will be given the last reward checkpoint you reached !");
        System.out.println("[Choose option by typing 1, 2, 3, 4 ] [H for lifeline] [Q to quit] ");
        System.out.println();
    }


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


    private List<Answer> askQuestion(List<Question> questionList, Level currentLevel) {
        // ask the question


        boolean usedHelp = false;

        System.out.println("Prize: " + currentLevel.getReward() + ", Reward Checkpoint: " + currentLevel.getRewardBreakout() + "\n");
        System.out.println(questionList.get(0).getText());

        List<Answer> allAnswers = new ArrayList<>();
        allAnswers.addAll(questionList.get(0).getWrongAnswers());
        int indexCorrectAnswer = random.nextInt(allAnswers.size());
        allAnswers.add(indexCorrectAnswer, questionList.get(0).getCorrectAnswer());

        printAnswers(allAnswers);


        return allAnswers;


    }


    private boolean answerQuestion(List<Question> questionList, Level currentLevel, List<Answer> allAnswers) {
        boolean isCorrectAnswer = false;
        // scan input

        // if answer is correct remove question , and return true.
        String choice = validInputAnswer(false);
        switch (choice) {
            case "1":
                if (allAnswers.get(0).getText().equals
                        (questionList.get(0).getCorrectAnswer().getText())) {
                    isCorrectAnswer = true;
                    System.out.println("Correct Answer !! \n");
                    questionList.remove(0);
                } else {
                    System.out.println(" WRONG ANSWER !! \n ");
                    System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                    isCorrectAnswer = false;
                }
                break;

            case "2":
                if (allAnswers.get(1).getText().equals
                        (questionList.get(0).getCorrectAnswer().getText())) {
                    isCorrectAnswer = true;
                    System.out.println("Correct Answer !! \n");
                    questionList.remove(0);
                } else {
                    System.out.println(" WRONG ANSWER !! \n");
                    System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                    isCorrectAnswer = false;
                }
                break;

            case "3":
                if (allAnswers.get(2).getText().equals
                        (questionList.get(0).getCorrectAnswer().getText())) {
                    isCorrectAnswer = true;
                    System.out.println("Correct Answer ! \n");
                    questionList.remove(0);
                } else {
                    System.out.println(" WRONG ANSWER !! \n");
                    System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                    isCorrectAnswer = false;
                }
                break;

            case "4":
                if (allAnswers.get(3).getText().equals
                        (questionList.get(0).getCorrectAnswer().getText())) {
                    isCorrectAnswer = true;
                    System.out.println("Correct Answer ! \n");
                    questionList.remove(0);
                } else {
                    System.out.println(" WRONG ANSWER !! \n");
                    System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                    isCorrectAnswer = false;
                }
                break;

            case "H":

                if (lifelines.size() > 0) {
                    lifelines.remove(0);
                    int wrongAnswerSize = questionList.get(0).getWrongAnswers().size();
                    String wrongAnswerString = questionList.get(0).getWrongAnswers().get(random.nextInt(wrongAnswerSize)).getText();
                    //get indexCorrectAnswer
                    int indexCorrectAnswer = -1;
                    for (int i = 0; i < allAnswers.size(); i++) {
                        if (questionList.get(0).getCorrectAnswer().getText().equals(allAnswers.get(i).getText())) {
                            indexCorrectAnswer = i;
                        }
                    }
                    //set 50-50 answers
                    for (int i = 0; i < allAnswers.size(); i++) {
                        if (allAnswers.get(i).getText().equals(wrongAnswerString) || i == indexCorrectAnswer) {
                            //empty code block
                        } else {
                            allAnswers.set(i, new Answer(""));
                        }
                    }

                    System.out.println(questionList.get(0).getText());
                    printAnswers(allAnswers);

                    choice = validInputAnswer(true);
                    switch (choice) {
                        case "1":
                            if (allAnswers.get(0).getText().equals
                                    (questionList.get(0).getCorrectAnswer().getText())) {
                                isCorrectAnswer = true;
                                System.out.println("Correct Answer !! \n");
                                questionList.remove(0);
                            } else {
                                System.out.println(" WRONG ANSWER !! \n ");
                                System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                                isCorrectAnswer = false;
                            }
                            break;

                        case "2":
                            if (allAnswers.get(1).getText().equals
                                    (questionList.get(0).getCorrectAnswer().getText())) {
                                isCorrectAnswer = true;
                                System.out.println("Correct Answer !! \n");
                                questionList.remove(0);
                            } else {
                                System.out.println(" WRONG ANSWER !! \n");
                                System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                                isCorrectAnswer = false;
                            }
                            break;

                        case "3":
                            if (allAnswers.get(2).getText().equals
                                    (questionList.get(0).getCorrectAnswer().getText())) {
                                isCorrectAnswer = true;
                                System.out.println("Correct Answer ! \n");
                                questionList.remove(0);
                            } else {
                                System.out.println(" WRONG ANSWER !! \n");
                                System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                                isCorrectAnswer = false;
                            }
                            break;

                        case "4":
                            if (allAnswers.get(3).getText().equals
                                    (questionList.get(0).getCorrectAnswer().getText())) {
                                isCorrectAnswer = true;
                                System.out.println("Correct Answer ! \n");
                                questionList.remove(0);
                            } else {
                                System.out.println(" WRONG ANSWER !! \n");
                                System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                                isCorrectAnswer = false;
                            }
                            break;

                        case "Q":
                            System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                            isCorrectAnswer = false;
                            break;

                    }
                }
                break;

            case "Q":
                System.out.println("Reward Checkpoint: " + currentLevel.getRewardBreakout());
                isCorrectAnswer = false;
                break;
        }


        return isCorrectAnswer;
    }


    private String validInputAnswer(boolean helpUsed) {
        String temp = "";
        boolean isValid = false;
        do {
            if (helpUsed) {
                System.out.println("[Choose option by typing 1, 2, 3, 4 ] [Lifeline used] [Q to quit] ");
            } else {
                System.out.println("[Choose option by typing 1, 2, 3, 4 ] [H for lifeline: "
                        + lifelines.size() + "] [Q to quit] ");
            }

            temp = sc.nextLine();

            // TODO maybe switchcase?
            if (temp.equals("1")) {
                temp = "1";
                isValid = true;
            } else if (temp.equals("2")) {
                temp = "2";
                isValid = true;
            } else if (temp.equals("3")) {
                temp = "3";
                isValid = true;
            } else if (temp.equals("4")) {
                temp = "4";
                isValid = true;

            } else if (temp.equalsIgnoreCase("H")) {
                if (helpUsed || lifelines.size() == 0) {
                    //empty code block
                } else {
                    temp = "H";
                    isValid = true;
                }

            } else if (temp.equalsIgnoreCase("Q")) {
                temp = "Q";
                isValid = true;
            }
        } while (!isValid);


        return temp;
    }


    private String validInputYesOrNo() {
        boolean isValid = false;
        String temp;
        do {
            System.out.println("[Type Y/N]");
            temp = sc.nextLine();
            if (temp.equalsIgnoreCase("y")) {
                isValid = true;
            } else if (temp.equalsIgnoreCase("n")) {
                isValid = true;
            }
        } while (!isValid);

        return temp;

    }

    private void printAnswers(List<Answer> answerList) {
        String middleBar = "  |  ";
        int maxLength = 0;
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).getText().length() > maxLength) {
                maxLength = answerList.get(i).getText().length();
            }
        }

        for (int i = 0; i < answerList.size(); i++) {
            //TODO printf pad
            System.out.print((i + 1) + ". ");
            System.out.printf("%-" + maxLength + "s", answerList.get(i).getText());
            if (i % 2 == 0) {
                System.out.print(middleBar);
            } else {
                System.out.println();
            }
        }


    }


}
