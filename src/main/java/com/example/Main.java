package com.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame frame;
    private QuizManager quizManager;
    private TimerThread timerThread;
    private int score = 0;

    public Main(String filePath) {
        quizManager = new QuizManager(filePath);
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Quiz App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        showNextQuestion();

        frame.setVisible(true);
    }

    private void showNextQuestion() {
        if (!quizManager.hasNextQuestion()) {
            showResult();
            return;
        }

        Question question = quizManager.getNextQuestion();

        JPanel questionPanel = new JPanel(new BorderLayout());
        JLabel questionLabel = new JLabel(question.getQuestion());
        questionPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(2, 2));
        ButtonGroup buttonGroup = new ButtonGroup();

        for (String option : question.getOptions()) {
            JRadioButton optionButton = new JRadioButton(option);
            buttonGroup.add(optionButton);
            optionsPanel.add(optionButton);

            optionButton.addActionListener(e -> {
                if (option.equals(question.getAnswer())) {
                    score += 10;
                }
                timerThread.stopTimer();
                showNextQuestion();
            });
        }

        questionPanel.add(optionsPanel, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.add(questionPanel, BorderLayout.CENTER);

        // Timer
        timerThread = new TimerThread(15); // 15 detik untuk setiap soal
        timerThread.start();

        frame.revalidate();
        frame.repaint();
    }

    private void showResult() {
        frame.getContentPane().removeAll();
        JLabel resultLabel = new JLabel("Quiz Complete! Your score: " + score);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(resultLabel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main("src/main/resources/questions.json"));
    }
}
