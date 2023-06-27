package ru.shishkin.opencv;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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

        Button button = new Button("Получить черно-белое изображение");
        button.setOnAction(this::onClickButton);
        root.getChildren().add(button);

        Button button1 = new Button("Cоздать черно-белый контур");
        button1.setOnAction(this::onClickButton1);
        root.getChildren().add(button1);

        Button button2 = new Button("Увеличение и уменьшение яркости");
        button2.setOnAction(this::onClickButton2);
        root.getChildren().add(button2);

        Button button3 = new Button("Увеличение и уменьшение насыщенности");
        button3.setOnAction(this::onClickButton3);
        root.getChildren().add(button3);

        Button button4 = new Button("Изменение цветового баланса");
        button4.setOnAction(this::onClickButton4);
        root.getChildren().add(button4);

        Button button5 = new Button("Вычисление гистограммы");
        button5.setOnAction(this::onClickButton5);
        root.getChildren().add(button5);

        Button button6 = new Button("Изменение гистрограммы");
        button6.setOnAction(this::onClickButton6);
        root.getChildren().add(button6);

        Button button7 = new Button("Медианный фильтр");
        button7.setOnAction(this::onClickButton7);
        root.getChildren().add(button7);

        Button button8 = new Button("Поиск прямых линий");
        button8.setOnAction(this::onClickButton8);
        root.getChildren().add(button8);

        Button button9 = new Button("Эрозия и дилатация");
        button9.setOnAction(this::onClickButton9);
        root.getChildren().add(button9);

        Scene scene = new Scene(root, 350.0, 500.0);
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

    private void onClickButton1(ActionEvent e) {
        //Cоздание черно-белого контура
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        Imgproc.adaptiveThreshold(img2, img3, 255,
                Imgproc.ADAPTIVE_THRESH_MEAN_C,
                Imgproc.THRESH_BINARY, 3, 5);
        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(img2, img4, 255,
                Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                Imgproc.THRESH_BINARY_INV, 3, 5);
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
        UtilsJavaFX.showImage(imgInv, "Илон Маск с ADAPTIVE_THRESH_MEAN_C + Inv");
        img.release();
        img2.release();
        img3.release();
        img4.release();
        imgInv.release();
        lut.release();
    }

    private void onClickButton2(ActionEvent e) {
        //Изменение яркости
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
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
        UtilsJavaFX.showImage(imgBGR, "Илон Маск с Яркостью +40");
        UtilsJavaFX.showImage(imgBGR1, "Илон Маск с Яркостью -40");
        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    private void onClickButton3(ActionEvent e) {
        //Изменение насыщенности
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
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

    private void onClickButton4(ActionEvent e) {
        //Изменение цветового баланса
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
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
        UtilsJavaFX.showImage(imgBGR1, "Жёлтый Илон Маск");
        img.release();
        imgLab.release();
        imgLab1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    private void onClickButton5(ActionEvent e) {
        //Вычисление гистограммы
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
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

    private void onClickButton6(ActionEvent e) {
        //Изменение гистрограммы
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
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
        Imgproc.calcHist(images, new MatOfInt(0), new Mat(),
                hist, new MatOfInt(256), new MatOfFloat(0, 256));
        Imgproc.calcHist(images, new MatOfInt(1), new Mat(),
                hist2, new MatOfInt(256), new MatOfFloat(0, 256));
        Core.normalize(hist, hist, 0, 128, Core.NORM_MINMAX);
        Core.normalize(hist2, hist2, 0, 128, Core.NORM_MINMAX);
        double v = 0;
        int h = 150;
        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new org.opencv.core.Point(i, h - 1),
                        new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
            }
            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new org.opencv.core.Point(i, h - 1),
                        new org.opencv.core.Point(i, h - 1 - v), UtilsOpenCV.COLOR_BLACK);
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

    private void onClickButton7(ActionEvent e) {
        //Медианный фильтр
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat img2 = new Mat();
        UtilsJavaFX.showImage(img, "Илон Маск");
        Imgproc.medianBlur(img, img2, 3);
        UtilsJavaFX.showImage(img2, "Илон Маск с параметром фильтра 3");
        Mat img3 = new Mat();
        Imgproc.medianBlur(img, img3, 5);
        UtilsJavaFX.showImage(img3, "Илон Маск с параметром фильтра 5");
        Mat img4 = new Mat();
        Imgproc.medianBlur(img, img4, 45);
        UtilsJavaFX.showImage(img4, "Илон Маск с параметром фильтра 45");
        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    private void onClickButton8(ActionEvent e) {
        //Поиск прямых линий
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200);
        UtilsJavaFX.showImage(edges, "Илон Маск с применением Canny");
        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.toRadians(2), 20, 30, 0);
        Mat result = new Mat(img.size(), CvType.CV_8UC3, UtilsOpenCV.COLOR_WHITE);
        for (int i = 0, r = lines.rows(); i < r; i++) {
            for (int j = 0, c = lines.cols(); j < c; j++) {
                double[] line = lines.get(i, j);
                Imgproc.line(result, new org.opencv.core.Point(line[0], line[1]),
                        new Point(line[2], line[3]), UtilsOpenCV.COLOR_BLACK);
            }
        }
        UtilsJavaFX.showImage(result, "Результат поиска прямых линий");
        img.release();
        imgGray.release();
        edges.release();
        result.release();
    }

    private void onClickButton9(ActionEvent e) {
        //Дилатация и жрозия ищображения
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        UtilsJavaFX.showImage(img, "Илон Маск");
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat img2 = new Mat();
        Imgproc.dilate(img, img2, kernel);
        UtilsJavaFX.showImage(img2, "Илон Маск с дилатацией");
        Mat img3 = new Mat();
        Imgproc.erode(img, img3, kernel);
        UtilsJavaFX.showImage(img3, "Илон Маск с эрозией");
    }

}