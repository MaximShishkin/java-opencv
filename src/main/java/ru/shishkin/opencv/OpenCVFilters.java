package ru.shishkin.opencv;

import javafx.event.ActionEvent;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// 7. Применение фильтров
public class OpenCVFilters {
    // 7.1. Размытие и подавление цифрового шума
    // 7.1.1. Метод blur(): однородное сглаживание
    protected void blur(Mat img) {
        Mat img2 = new Mat();
        Imgproc.blur(img, img2, new Size(3, 3));
        JavaFXUtils.showImage(img2, "Size(3, 3)");

        Mat img3 = new Mat();
        Imgproc.blur(img, img3, new Size(45, 45), new Point(-1, -1));
        JavaFXUtils.showImage(img3, "Size(45, 45)");

        img.release();
        img2.release();
        img3.release();
    }

    // 7.1.2. Размытие по Гауссу
    protected void gaussianBlur(Mat img) {
        Mat img2 = new Mat();
        Imgproc.GaussianBlur(img, img2, new Size(3, 3), 0);
        JavaFXUtils.showImage(img2, "Size(3, 3)");

        Mat img3 = new Mat();
        Imgproc.GaussianBlur(img, img3, new Size(45, 45), 0);
        JavaFXUtils.showImage(img3, "Size(45, 45)");

        Mat img4 = new Mat();
        Imgproc.GaussianBlur(img, img4, new Size(0, 0), 1.5);
        JavaFXUtils.showImage(img4, "Size(0, 0), 1.5");

        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    // 7.1.5.
    private void onClickButton77(ActionEvent e) {
        //Медианный фильтр
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        Mat img2 = new Mat();
        JavaFXUtils.showImage(img, "Илон Маск");
        Imgproc.medianBlur(img, img2, 3);
        JavaFXUtils.showImage(img2, "Илон Маск с параметром фильтра 3");
        Mat img3 = new Mat();
        Imgproc.medianBlur(img, img3, 5);
        JavaFXUtils.showImage(img3, "Илон Маск с параметром фильтра 5");
        Mat img4 = new Mat();
        Imgproc.medianBlur(img, img4, 45);
        JavaFXUtils.showImage(img4, "Илон Маск с параметром фильтра 45");
        img.release();
        img2.release();
        img3.release();
        img4.release();
    }

    // 7.3
    private void onClickButton99(ActionEvent e) {
        //Дилатация и жрозия ищображения
        Mat img = Imgcodecs.imread(getClass().getClassLoader().getResource("ElonMusk.jpg").getPath());
        if (img.empty()) {
            System.out.println("Не удалось загрузить изображение");
            return;
        }
        JavaFXUtils.showImage(img, "Илон Маск");
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Mat img2 = new Mat();
        Imgproc.dilate(img, img2, kernel);
        JavaFXUtils.showImage(img2, "Илон Маск с дилатацией");
        Mat img3 = new Mat();
        Imgproc.erode(img, img3, kernel);
        JavaFXUtils.showImage(img3, "Илон Маск с эрозией");
    }

    private void onClickButton88(ActionEvent e) {
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
        JavaFXUtils.showImage(edges, "Илон Маск с применением Canny");
        Mat lines = new Mat();
        Imgproc.HoughLinesP(edges, lines, 1, Math.toRadians(2), 20, 30, 0);
        Mat result = new Mat(img.size(), CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        for (int i = 0, r = lines.rows(); i < r; i++) {
            for (int j = 0, c = lines.cols(); j < c; j++) {
                double[] line = lines.get(i, j);
                Imgproc.line(result, new org.opencv.core.Point(line[0], line[1]),
                        new Point(line[2], line[3]), OpenCVUtils.COLOR_BLACK);
            }
        }
        JavaFXUtils.showImage(result, "Результат поиска прямых линий");
        img.release();
        imgGray.release();
        edges.release();
        result.release();
    }
}
