package ru.shishkin.opencv;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.io.IOException;

public class OpenCVApplication extends Application {
    private TextArea textArea;

    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        VBox root = new VBox(15.0);
        root.setAlignment(Pos.CENTER);

        textArea = new TextArea("D:\\Files\\MakShish\\Desktop\\ElonMusk.jpg");
        textArea.setMaxSize(300, 10);
        root.getChildren().add(textArea);

        // 6.1.1. Получить черно-белое изображение
        Button button = new Button("Получить черно-белое изображение");
        button.setOnAction(this::onClickButton);
        root.getChildren().add(button);

        // 6.1.2. Получить черно-белый контур
        Button button1 = new Button("Получить черно-белый контур");
        button1.setOnAction(this::onClickButton1);
        root.getChildren().add(button1);

        // 6.2.1. Увеличение и уменьшение яркости
        Button button2 = new Button("Увеличение и уменьшение яркости");
        button2.setOnAction(this::onClickButton2);
        root.getChildren().add(button2);

        // 6.2.2. Увеличение и уменьшение насыщенности
        Button button3 = new Button("Увеличение и уменьшение насыщенности");
        button3.setOnAction(this::onClickButton3);
        root.getChildren().add(button3);

        // 6.3. Изменение цветового баланса
        Button button4 = new Button("Изменение цветового баланса");
        button4.setOnAction(this::onClickButton4);
        root.getChildren().add(button4);

        // 6.4. Изменение контраста
        Button button5 = new Button("Изменение контраста");
        button5.setOnAction(this::onClickButton5);
        root.getChildren().add(button5);

        // 6.5. Создание негатива изображения
        Button button6 = new Button("Создание негатива изображения");
        button6.setOnAction(this::onClickButton6);
        root.getChildren().add(button6);

        // 6.6. Сепия
        Button button7 = new Button("Сепия");
        button7.setOnAction(this::onClickButton7);
        root.getChildren().add(button7);

        // 6.7. Вычисление гистограммы
        Button button8 = new Button("Вычисление гистограммы");
        button8.setOnAction(this::onClickButton8);
        root.getChildren().add(button8);

        // 6.8. Автоматическое выравнивание гистограммы изображения в градациях серого
        Button button9 = new Button("Выравнивание гистограммы");
        button9.setOnAction(this::onClickButton9);
        root.getChildren().add(button9);

        // 6.9. Адаптивное выравнивание гистограммы с помощью алгоритма CLAHE
        Button button10 = new Button("Выравнивание гистограммы с CLAHE");
        button10.setOnAction(this::onClickButton10);
        root.getChildren().add(button10);

        // 6.10. Различные цветовые палитры
        Button button11 = new Button("Различные цветовые палитры");
        button11.setOnAction(this::onClickButton11);
        root.getChildren().add(button11);

        // 7.1.1. Метод blur(): однородное сглаживание
        Button button12 = new Button("Однородное сглаживание");
        button12.setOnAction(this::onClickButton12);
        root.getChildren().add(button12);

        // 7.1.2. Размытие по Гауссу
        Button button13 = new Button("Размытие по Гауссу");
        button13.setOnAction(this::onClickButton13);
        root.getChildren().add(button13);

        Scene scene = new Scene(root);
        stage.setTitle("OpenCV " + Core.VERSION);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        stage.show();
    }

    // 6.1.1. Получить черно-белое изображение
    private void onClickButton(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method611(img);
    }

    // 6.1.2. Получить черно-белый контур
    private void onClickButton1(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method612(img);
    }

    // 6.2.1. Увеличение и уменьшение яркости
    private void onClickButton2(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method621(img);
    }

    // 6.2.2. Увеличение и уменьшение насыщенности
    private void onClickButton3(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method622(img);
    }

    // 6.3. Изменение цветового баланса
    private void onClickButton4(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method63(img);
    }

    // 6.4. Изменение контраста
    private void onClickButton5(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method64(img);
    }

    // 6.5. Создание негатива изображения
    private void onClickButton6(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method65(img);
    }

    // 6.6. Сепия
    private void onClickButton7(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method66(img);
    }

    // 6.7. Вычисление гистограммы
    private void onClickButton8(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method67(img);
    }

    // 6.8. Автоматическое выравнивание гистограммы изображения в градациях серого
    private void onClickButton9(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method68(img);
    }

    // 6.9. Адаптивное выравнивание гистограммы с помощью алгоритма CLAHE
    private void onClickButton10(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method69(img);
    }

    // 6.10. Различные цветовые палитры
    private void onClickButton11(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVColorComponents().method610(img);
    }

    // 7.1.1. Метод blur(): однородное сглаживание
    private void onClickButton12(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVFilters().blur(img);
    }

    // 7.1.2. Размытие по Гауссу
    private void onClickButton13(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVFilters().gaussianBlur(img);
    }
}