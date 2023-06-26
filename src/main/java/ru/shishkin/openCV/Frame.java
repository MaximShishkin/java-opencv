package ru.shishkin.opencv;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Frame extends javafx.application.Application {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(args);
    }

    public void start(Stage stage) throws Exception {
        VBox root = new VBox(15.0);
        root.setAlignment(Pos.CENTER);

        Button button = new Button("Получить чёрно-белое изображение");
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

        Scene scene = new Scene(root, 330.0, 410.0);
        stage.setTitle("OpenCV " + Core.VERSION);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
        });

        stage.show();
    }

    private void onClickButton(ActionEvent e) {
        //Получение чёрно-белого изображения
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);
        Mat img3 = new Mat();
        double thresh = Imgproc.threshold(img2, img3, 100, 255,
                Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);
        System.out.println(thresh);
        CvUtilsFX.showImage(img, "Илон Маск");
        CvUtilsFX.showImage(img3, "Илон Маск с THRESH_OTSU");
        img.release();
        img2.release();
        img3.release();
    }

    private void onClickButton1(ActionEvent e) {
        //Cоздание черно-белого контура
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
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
        CvUtilsFX.showImage(img, "Илон Маск");
        CvUtilsFX.showImage(img3, "Илонс Маск с ADAPTIVE_THRESH_MEAN_C");
        CvUtilsFX.showImage(img4, "Илон Маск с ADAPTIVE_THRESH_GAUSSIAN_C + THRESH_BINARY_INV");
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
        CvUtilsFX.showImage(imgInv, "Илон Маск с ADAPTIVE_THRESH_MEAN_C + Inv");
        img.release();
        img2.release();
        img3.release();
        img4.release();
        imgInv.release();
        lut.release();
    }

    private void onClickButton2(ActionEvent e) {
        //Изменение яркости
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
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
        CvUtilsFX.showImage(img, "Илон Маск");
        CvUtilsFX.showImage(imgBGR, "Илон Маск с Яркостью +40");
        CvUtilsFX.showImage(imgBGR1, "Илон Маск с Яркостью -40");
        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    private void onClickButton3(ActionEvent e) {
        //Изменение насыщенности
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
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

        CvUtilsFX.showImage(img, "Илон Маск");
        CvUtilsFX.showImage(imgBGR, "Илон Маск с насыщенностью +40");
        CvUtilsFX.showImage(imgBGR1, "Илон Маск с насыщенностью -40");
        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    private void onClickButton4(ActionEvent e) {
        //Изменение цветового баланса
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
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
        CvUtilsFX.showImage(img, "Илон Маск");
        CvUtilsFX.showImage(imgBGR, "Синий Илон Маск");
        CvUtilsFX.showImage(imgBGR1, "Жёлтый Илон Маск");
        img.release();
        imgLab.release();
        imgLab1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    private void onClickButton5(ActionEvent e) {
        //Вычисление гистограммы
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        CvUtilsFX.showImage(img, "Оригинал");
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
        Mat imgHistRed = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistGreen = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistBlue = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHistGray = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        for (int i = 0, j = histGray.rows(); i < j; i++) {
            v = Math.round(histRed.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistRed, new Point(i, h - 1), new Point(i, h - 1 - v), CvUtils.COLOR_RED);
            }
            v = Math.round(histGreen.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistGreen, new Point(i, h - 1), new Point(i, h - 1 - v), CvUtils.COLOR_GREEN);
            }
            v = Math.round(histBlue.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistBlue, new Point(i, h - 1), new Point(i, h - 1 - v), CvUtils.COLOR_BLUE);
            }
            v = Math.round(histGray.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHistGray, new Point(i, h - 1), new Point(i, h - 1 - v), CvUtils.COLOR_GRAY);
            }
        }
        CvUtilsFX.showImage(imgHistRed, "Red");
        CvUtilsFX.showImage(imgHistGreen, "Green");
        CvUtilsFX.showImage(imgHistBlue, "Blue");
        CvUtilsFX.showImage(imgHistGray, "Gray");
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
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
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
        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_BLACK);
            }
            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new Point(i, h - 1),
                        new Point(i, h - 1 - v), CvUtils.COLOR_BLACK);
            }
        }
        CvUtilsFX.showImage(img2, "Оригинал");
        CvUtilsFX.showImage(imgHist, "Гистограмма до");
        CvUtilsFX.showImage(img3, "CLAHE");
        CvUtilsFX.showImage(imgHist2, "Гистограмма после");
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
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat img2 = new Mat();
        CvUtilsFX.showImage(img, "Илон Маск");
        Imgproc.medianBlur(img, img2, 3);
        CvUtilsFX.showImage(img2, "Илон Маск с параметром фильтра 3");
        Mat img3 = new Mat();
        Imgproc.medianBlur(img, img3, 5);
        CvUtilsFX.showImage(img3, "Илон Маск с параметром фильтра 5");
        Mat img4 = new Mat();
        Imgproc.medianBlur(img, img4, 45);
        CvUtilsFX.showImage(img4, "Илон Маск с параметром фильтра 45");
        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    private void onClickButton8(ActionEvent e) {
        //Поиск прямых линий
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat imgGray = new Mat();
        Imgproc.cvtColor(img, imgGray, Imgproc.COLOR_BGR2GRAY);
        Mat edges = new Mat();
        Imgproc.Canny(imgGray, edges, 80, 200);
        CvUtilsFX.showImage(edges, "Илон Маск с применением Canny");
        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.toRadians(2), 20, 30, 0);
        Mat result = new Mat(img.size(), CvType.CV_8UC3, CvUtils.COLOR_WHITE);
        for (int i = 0, r = lines.rows(); i < r; i++) {
            for (int j = 0, c = lines.cols(); j < c; j++) {
                double[] line = lines.get(i, j);
                Imgproc.line(result, new Point(line[0], line[1]),
                        new Point(line[2], line[3]), CvUtils.COLOR_BLACK);
            }
        }
        CvUtilsFX.showImage(result, "Результат поиска прямых линий");
        img.release();
        imgGray.release();
        edges.release();
        result.release();
    }

    private void onClickButton9(ActionEvent e) {
        //Дилатация и жрозия ищображения
        Mat img = Imgcodecs.imread("C:\\foto.jpg");
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        CvUtilsFX.showImage(img, "Илон Маск");
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat img2 = new Mat();
        Imgproc.dilate(img, img2, kernel);
        CvUtilsFX.showImage(img2, "Илон Маск с дилатацией");
        Mat img3 = new Mat();
        Imgproc.erode(img, img3, kernel);
        CvUtilsFX.showImage(img3, "Илон Маск с эрозией");
    }
}
