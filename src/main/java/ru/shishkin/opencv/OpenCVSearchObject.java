package ru.shishkin.opencv;

import javafx.event.ActionEvent;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

// 8. Поиск объектов
public class OpenCVSearchObject {
    // 8.5. Поиск прямых линий
    private void onClickButton88(ActionEvent e) {
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
                Imgproc.line(result, new org.opencv.core.Point(line[0], line[1]), new Point(line[2], line[3]), OpenCVUtils.COLOR_BLACK);
            }
        }
        JavaFXUtils.showImage(result, "Результат поиска прямых линий");
        img.release();
        imgGray.release();
        edges.release();
        result.release();
    }
}
