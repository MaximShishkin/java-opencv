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
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class ApplicationOpenCV extends Application {
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

        // Получить черно-белое изображение (6.1)
        Button button = new Button("Получить черно-белое изображение");
        button.setOnAction(this::onClickButton);
        root.getChildren().add(button);

        // Получить черно-белый контур (6.1)
        Button button1 = new Button("Получить черно-белый контур");
        button1.setOnAction(this::onClickButton1);
        root.getChildren().add(button1);

        // Увеличение и уменьшение яркости (6.2)
        Button button2 = new Button("Увеличение и уменьшение яркости");
        button2.setOnAction(this::onClickButton2);
        root.getChildren().add(button2);

        // Увеличение и уменьшение насыщенности (6.2)
        Button button3 = new Button("Увеличение и уменьшение насыщенности");
        button3.setOnAction(this::onClickButton3);
        root.getChildren().add(button3);

        // Изменение цветового баланса (6.3)
        Button button4 = new Button("Изменение цветового баланса");
        button4.setOnAction(this::onClickButton4);
        root.getChildren().add(button4);

        // Изменение контраста (6.4)
        Button button5 = new Button("Изменение контраста");
        button5.setOnAction(this::onClickButton5);
        root.getChildren().add(button5);

        // Создание негатива изображения (6.5)
        Button button6 = new Button("Создание негатива изображения");
        button6.setOnAction(this::onClickButton6);
        root.getChildren().add(button6);

        // Сепия (6.6)
        Button button7 = new Button("Сепия");
        button7.setOnAction(this::onClickButton7);
        root.getChildren().add(button7);

        // Вычисление гистограммы (6.7)
        Button button8 = new Button("Вычисление гистограммы");
        button8.setOnAction(this::onClickButton8);
        root.getChildren().add(button8);

        // Автоматическое выравнивание гистограммы изображения в градациях серого (6.8)
        Button button9 = new Button("Выравнивание гистограммы");
        button9.setOnAction(this::onClickButton9);
        root.getChildren().add(button9);

        // Адаптивное выравнивание гистограммы с помощью алгоритма CLAHE (6.9)
        Button button10 = new Button("Выравнивание гистограммы с CLAHE");
        button10.setOnAction(this::onClickButton10);
        root.getChildren().add(button10);

        // Различные цветовые палитры (6.10)
        Button button11 = new Button("Различные цветовые палитры");
        button11.setOnAction(this::onClickButton11);
        root.getChildren().add(button11);

        // 7.1.1. Метод blur(): однородное сглаживание
        Button button12 = new Button("Однородное сглаживание");
        button12.setOnAction(this::onClickButton12);
        root.getChildren().add(button12);

        Scene scene = new Scene(root);
        stage.setTitle("OpenCV " + Core.VERSION);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });
        stage.show();
    }

    // Получить черно-белое изображение (6.1)
    private void onClickButton(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        Imgproc.threshold(img2, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(img3, "Черно-белый Илон Маск");

        img.release();
        img2.release();
        img3.release();
    }

    // Получить черно-белый контур (6.1)
    private void onClickButton1(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        Imgproc.adaptiveThreshold(img2, img3, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 5);

        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(img2, img4, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 3, 5);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(img3, "Илонс Маск с ADAPTIVE_THRESH_MEAN_C");
        UtilsJavaFX.showImage(img4, "Илон Маск с ADAPTIVE_THRESH_GAUSSIAN_C + THRESH_BINARY_INV");

        // Инверсия с помощью таблицы соответствия
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        byte[] arr = new byte[256];
        for (int i = 0; i < 256; i++) {
            arr[i] = (byte) (255 - i);
        }
        lut.put(0, 0, arr);

        // Преобразование в соответствии с таблицей
        Mat imgInv = new Mat();
        Core.LUT(img3, lut, imgInv);
        UtilsJavaFX.showImage(imgInv, "Илон Маск с ADAPTIVE_THRESH_MEAN_C + инверсия");

        img.release();
        img2.release();
        img3.release();
        img4.release();
        imgInv.release();
        lut.release();
    }

    // Увеличение и уменьшение яркости (6.2)
    private void onClickButton2(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        // Уменьшение яркости
        Mat imgHSV1 = new Mat();
        Imgproc.cvtColor(img, imgHSV1, Imgproc.COLOR_BGR2HSV);
        Core.add(imgHSV1, new Scalar(0, 0, -40), imgHSV1);
        Mat imgBGR1 = new Mat();
        Imgproc.cvtColor(imgHSV1, imgBGR1, Imgproc.COLOR_HSV2BGR);

        // Увеличение яркости
        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        Core.add(imgHSV, new Scalar(0, 0, 40), imgHSV);
        Mat imgBGR = new Mat();
        Imgproc.cvtColor(imgHSV, imgBGR, Imgproc.COLOR_HSV2BGR);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(imgBGR, "Илон Маск с яркостью +40");
        UtilsJavaFX.showImage(imgBGR1, "Илон Маск с яркостью -40");

        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // Увеличение и уменьшение насыщенности (6.2)
    private void onClickButton3(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        // Увеличение насыщенности
        Mat imgHSV = new Mat();
        Imgproc.cvtColor(img, imgHSV, Imgproc.COLOR_BGR2HSV);
        Core.add(imgHSV, new Scalar(0, 40, 0), imgHSV);
        Mat imgBGR = new Mat();
        Imgproc.cvtColor(imgHSV, imgBGR, Imgproc.COLOR_HSV2BGR);

        // Уменьшение насыщенности
        Mat imgHSV1 = new Mat();
        Imgproc.cvtColor(img, imgHSV1, Imgproc.COLOR_BGR2HSV);
        Core.add(imgHSV1, new Scalar(0, -40, 0), imgHSV1);
        Mat imgBGR1 = new Mat();
        Imgproc.cvtColor(imgHSV1, imgBGR1, Imgproc.COLOR_HSV2BGR);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(imgBGR, "Илон Маск с насыщенностью +40");
        UtilsJavaFX.showImage(imgBGR1, "Илон Маск с насыщенностью -40");

        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // Изменение цветового баланса (6.3)
    private void onClickButton4(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        // Положение цвета от зеленого до красного
        Mat imgLab = new Mat();
        Imgproc.cvtColor(img, imgLab, Imgproc.COLOR_BGR2Lab);
        Core.add(imgLab, new Scalar(0, 10, 0), imgLab);
        Mat imgBGR = new Mat();
        Imgproc.cvtColor(imgLab, imgBGR, Imgproc.COLOR_Lab2BGR);

        // Положение цвета от синего до желтого
        Mat imgLab1 = new Mat();
        Imgproc.cvtColor(img, imgLab1, Imgproc.COLOR_BGR2Lab);
        Core.add(imgLab1, new Scalar(0, 0, 20), imgLab1);
        Mat imgBGR1 = new Mat();
        Imgproc.cvtColor(imgLab1, imgBGR1, Imgproc.COLOR_Lab2BGR);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(imgBGR, "Синий Илон Маск");
        UtilsJavaFX.showImage(imgBGR1, "Желтый Илон Маск");

        img.release();
        imgLab.release();
        imgLab1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // Изменение контраста (6.4)
    private void onClickButton5(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        // Вычисление средней яркости изображения
        Scalar meanBGR = Core.mean(img);
        double mean = meanBGR.val[0] * 0.114 + meanBGR.val[1] * 0.587 + meanBGR.val[2] * 0.299;
        // Коэффициент контраста
        double contrast = 1.5;

        // Построение таблицы соответствия
        Mat lut = new Mat(1, 256, CvType.CV_8UC1);
        byte[] arr = new byte[256];
        int color = 0;
        for (int i = 0; i < 256; i++) {
            color = (int) (contrast * (i - mean) + mean);
            //color = (int) (contrast * (i - 128) + 128);
            //color = (int) ((contrast * (i / 255.0 - 0.5) + 0.5) * 255);
            color = color > 255 ? 255 : (color < 0 ? 0 : color);
            arr[i] = (byte) (color);
        }
        lut.put(0, 0, arr);

        // Применение таблицы соответствия к изображению
        Mat img2 = new Mat();
        Core.LUT(img, lut, img2);

        UtilsJavaFX.showImage(img, "Илон Маск");
        UtilsJavaFX.showImage(img2, "Илон Маск с контрастом: " + contrast);

        img.release();
        img2.release();
        lut.release();
    }

    // Создание негатива изображения (6.5)
    private void onClickButton6(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        Mat m = new Mat(img.rows(), img.cols(), img.type(), UtilsOpenCV.COLOR_WHITE);
        Mat negative = new Mat();
        Core.subtract(m, img, negative);
        UtilsOpenCV.showImage(negative, "Негатив");
        img.release();
        negative.release();
        m.release();
    }

    // Сепия (6.6)
    private void onClickButton7(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        // Построение матрицы трансформации
        Mat kernel = new Mat(3, 3, CvType.CV_32FC1);
        kernel.put(0, 0,
                0.131, 0.534, 0.272, // blue  = b * b1 + g * g1 + r * r1
                0.168, 0.686, 0.349, // green = b * b2 + g * g2 + r * r2
                0.189, 0.769, 0.393  // red   = b * b3 + g * g3 + r * r3
        );

        Mat sepia = new Mat();
        Core.transform(img, sepia, kernel);
        UtilsOpenCV.showImage(sepia, "Сепия");

        img.release();
        kernel.release();
        sepia.release();
    }

    // Вычисление гистограммы (6.7)
    private void onClickButton8(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        UtilsJavaFX.showImage(img, "Оригинал");
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);

        // Вычисляем гистограммы по каналам
        ArrayList<Mat> images = new ArrayList<Mat>();
        images.add(img);
        images.add(imgGray);
        Mat histGray = new Mat();
        Mat histRed = new Mat();
        Mat histGreen = new Mat();
        Mat histBlue = new Mat();
        Imgproc.calcHist(images, new MatOfInt(3), new Mat(), histGray, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(2), new Mat(), histRed, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(1), new Mat(), histGreen, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(0), new Mat(), histBlue, new MatOfInt(256), new MatOfFloat(0, 256));

        // Нормализация диапазона
        Core.normalize(histGray, histGray, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histRed, histRed, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histGreen, histGreen, 0, 128, Core.NORM_MINMAX);
        Core.normalize(histBlue, histBlue, 0, 128, Core.NORM_MINMAX);

        // Отрисовка гистограмм
        double v = 0;
        int h = 150;
        Mat imgHistRed = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHistGreen = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHistBlue = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHistGray = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);

        for (int i = 0, j = histGray.rows(); i < j; i++) {
            v = Math.round(histRed.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistRed, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_RED);
            }

            v = Math.round(histGreen.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistGreen, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_GREEN);
            }

            v = Math.round(histBlue.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistBlue, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLUE);
            }

            v = Math.round(histGray.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistGray, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_GRAY);
            }
        }

        UtilsJavaFX.showImage(imgHistRed, "Red");
        UtilsJavaFX.showImage(imgHistGreen, "Green");
        UtilsJavaFX.showImage(imgHistBlue, "Blue");
        UtilsJavaFX.showImage(imgHistGray, "Gray");

        img.release();
        imgGray.release();
        imgHistRed.release();
        imgHistGreen.release();
        imgHistBlue.release();
        imgHistGray.release();
        histGray.release();
        histRed.release();
        histGreen.release();
        histBlue.release();
    }

    // Автоматическое выравнивание гистограммы изображения в градациях серого (6.8)
    private void onClickButton9(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        Imgproc.equalizeHist(img2, img3);

        // Вычисляем и отрисовываем гистограммы
        ArrayList<Mat> images = new ArrayList<Mat>();
        images.add(img2);
        images.add(img3);
        Mat hist = new Mat();
        Mat hist2 = new Mat();

        Imgproc.calcHist(images, new MatOfInt(0), new Mat(), hist, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(1), new Mat(), hist2, new MatOfInt(256), new MatOfFloat(0, 256));
        Core.normalize(hist, hist, 0, 128, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 128, Core.NORM_MINMAX);

        double v = 0;
        int h = 150;

        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);

        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new Point(i, h - 1), new Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
            }

            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new Point(i, h - 1), new Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
            }
        }

        UtilsJavaFX.showImage(img2, "Оригинал");
        UtilsJavaFX.showImage(imgHist, "Гистограмма до");
        UtilsJavaFX.showImage(img3, "equalizeHist");
        UtilsJavaFX.showImage(imgHist2, "Гистограмма после");

        img.release();
        img2.release();
        img3.release();
        imgHist.release();
        imgHist2.release();
        hist.release();
        hist2.release();
    }

    // Адаптивное выравнивание гистограммы с помощью алгоритма CLAHE (6.9)
    private void onClickButton10(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        CLAHE clane = Imgproc.createCLAHE();
        clane.setClipLimit(4);
        clane.apply(img2, img3);

        // Вычисляем и отрисовываем гистограммы
        ArrayList<Mat> images = new ArrayList<Mat>();
        images.add(img2);
        images.add(img3);
        Mat hist = new Mat();
        Mat hist2 = new Mat();
        Imgproc.calcHist(images, new MatOfInt(0), new Mat(), hist, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(1), new Mat(), hist2, new MatOfInt(256), new MatOfFloat(0, 256));
        Core.normalize(hist, hist, 0, 128, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 128, Core.NORM_MINMAX);

        double v = 0;
        int h = 150;

        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);

        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
            }

            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
            }
        }

        UtilsJavaFX.showImage(img2, "Оригинал");
        UtilsJavaFX.showImage(imgHist, "Гистограмма до");
        UtilsJavaFX.showImage(img3, "CLAHE");
        UtilsJavaFX.showImage(imgHist2, "Гистограмма после");

        img.release();
        img2.release();
        img3.release();
        imgHist.release();
        imgHist2.release();
        hist.release();
        hist2.release();
    }

    // Различные цветовые палитры (6.10)
    private void onClickButton11(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        System.out.println(img.size());
        Imgproc.resize(img, img, new Size(), 0.6, 0.6, Imgproc.INTER_LINEAR);
        UtilsJavaFX.showImage(img, "Оригинал");

        Mat img2 = new Mat();
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_AUTUMN);
        UtilsJavaFX.showImage(img2, "COLORMAP_AUTUMN");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_BONE);
        UtilsJavaFX.showImage(img2, "COLORMAP_BONE");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_COOL);
        UtilsJavaFX.showImage(img2, "COLORMAP_COOL");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_HOT);
        UtilsJavaFX.showImage(img2, "COLORMAP_HOT");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_HSV);
        UtilsJavaFX.showImage(img2, "COLORMAP_HSV");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_JET);
        UtilsJavaFX.showImage(img2, "COLORMAP_JET");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_OCEAN);
        UtilsJavaFX.showImage(img2, "COLORMAP_OCEAN");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_PARULA);
        UtilsJavaFX.showImage(img2, "COLORMAP_PARULA");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_PINK);
        UtilsJavaFX.showImage(img2, "COLORMAP_PINK");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_RAINBOW);
        UtilsJavaFX.showImage(img2, "COLORMAP_RAINBOW");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_SPRING);
        UtilsJavaFX.showImage(img2, "COLORMAP_SPRING");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_SUMMER);
        UtilsJavaFX.showImage(img2, "COLORMAP_SUMMER");
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_WINTER);
        UtilsJavaFX.showImage(img2, "COLORMAP_WINTER");
        img.release();
        img2.release();
    }

    // 7.1.1. Метод blur(): однородное сглаживание
    private void onClickButton12(ActionEvent e) {
        Mat img = Imgcodecs.imread(textArea.getText());

        if (img.empty()) {
            JOptionPane.showMessageDialog(null, "Неверно указан путь!", "Ошибка", 0);
            return;
        }

        new OpenCVFilters().onClickButton12(img);
    }
}