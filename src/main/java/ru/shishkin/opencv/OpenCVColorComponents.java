package ru.shishkin.opencv;

import org.opencv.core.*;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

// 6. Изменение значений компонентов цвета
public class OpenCVColorComponents {
    // 6.1.1. Получить черно-белое изображение
    protected void getBlackWhiteImage(Mat img) {
        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        Imgproc.threshold(img2, img3, 100, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(img3, "Черно-белый Илон Маск");

        img.release();
        img2.release();
        img3.release();
    }

    // 6.1.2. Получить черно-белый контур
    protected void method612(Mat img) {
        Mat img2 = new Mat();
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);

        Mat img3 = new Mat();
        Imgproc.adaptiveThreshold(img2, img3, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, 5);

        Mat img4 = new Mat();
        Imgproc.adaptiveThreshold(img2, img4, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY_INV, 3, 5);

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(img3, "Илонс Маск с ADAPTIVE_THRESH_MEAN_C");
        JavaFXUtils.showImage(img4, "Илон Маск с ADAPTIVE_THRESH_GAUSSIAN_C + THRESH_BINARY_INV");

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
        JavaFXUtils.showImage(imgInv, "Илон Маск с ADAPTIVE_THRESH_MEAN_C + инверсия");

        img.release();
        img2.release();
        img3.release();
        img4.release();
        imgInv.release();
        lut.release();
    }

    // 6.2.1. Увеличение и уменьшение яркости
    protected void method621(Mat img) {
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

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(imgBGR, "Илон Маск с яркостью +40");
        JavaFXUtils.showImage(imgBGR1, "Илон Маск с яркостью -40");

        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // 6.2.2. Увеличение и уменьшение насыщенности
    protected void method622(Mat img) {
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

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(imgBGR, "Илон Маск с насыщенностью +40");
        JavaFXUtils.showImage(imgBGR1, "Илон Маск с насыщенностью -40");

        img.release();
        imgHSV.release();
        imgHSV1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // 6.3. Изменение цветового баланса
    protected void method63(Mat img) {
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

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(imgBGR, "Синий Илон Маск");
        JavaFXUtils.showImage(imgBGR1, "Желтый Илон Маск");

        img.release();
        imgLab.release();
        imgLab1.release();
        imgBGR.release();
        imgBGR1.release();
    }

    // 6.4. Изменение контраста
    protected void method64(Mat img) {
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

        JavaFXUtils.showImage(img, "Илон Маск");
        JavaFXUtils.showImage(img2, "Илон Маск с контрастом: " + contrast);

        img.release();
        img2.release();
        lut.release();
    }

    // 6.5. Создание негатива изображения
    protected void method65(Mat img) {
        Mat m = new Mat(img.rows(), img.cols(), img.type(), OpenCVUtils.COLOR_WHITE);
        Mat negative = new Mat();
        Core.subtract(m, img, negative);
        OpenCVUtils.showImage(negative, "Негатив");

        img.release();
        negative.release();
        m.release();
    }

    // 6.6. Сепия
    protected void method66(Mat img) {
        // Построение матрицы трансформации
        Mat kernel = new Mat(3, 3, CvType.CV_32FC1);
        kernel.put(0, 0,
                0.131, 0.534, 0.272, // blue  = b * b1 + g * g1 + r * r1
                0.168, 0.686, 0.349, // green = b * b2 + g * g2 + r * r2
                0.189, 0.769, 0.393  // red   = b * b3 + g * g3 + r * r3
        );

        Mat sepia = new Mat();
        Core.transform(img, sepia, kernel);
        OpenCVUtils.showImage(sepia, "Сепия");

        img.release();
        kernel.release();
        sepia.release();
    }

    // 6.7. Вычисление гистограммы
    protected void method67(Mat img) {
        JavaFXUtils.showImage(img, "Оригинал");
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
        Mat imgHistRed = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        Mat imgHistGreen = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        Mat imgHistBlue = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        Mat imgHistGray = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);

        for (int i = 0, j = histGray.rows(); i < j; i++) {
            v = Math.round(histRed.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistRed, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_RED);
            }

            v = Math.round(histGreen.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistGreen, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_GREEN);
            }

            v = Math.round(histBlue.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistBlue, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_BLUE);
            }

            v = Math.round(histGray.get(i, 0)[0]);

            if (v != 0) {
                Imgproc.line(imgHistGray, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_GRAY);
            }
        }

        JavaFXUtils.showImage(imgHistRed, "Red");
        JavaFXUtils.showImage(imgHistGreen, "Green");
        JavaFXUtils.showImage(imgHistBlue, "Blue");
        JavaFXUtils.showImage(imgHistGray, "Gray");

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

    // 6.8. Автоматическое выравнивание гистограммы изображения в градациях серого
    protected void method68(Mat img) {
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

        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);

        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new Point(i, h - 1), new Point(i, h - 1 - v), OpenCVUtils.COLOR_BLACK);
            }

            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new Point(i, h - 1), new Point(i, h - 1 - v), OpenCVUtils.COLOR_BLACK);
            }
        }

        JavaFXUtils.showImage(img2, "Оригинал");
        JavaFXUtils.showImage(imgHist, "Гистограмма до");
        JavaFXUtils.showImage(img3, "equalizeHist");
        JavaFXUtils.showImage(imgHist2, "Гистограмма после");

        img.release();
        img2.release();
        img3.release();
        imgHist.release();
        imgHist2.release();
        hist.release();
        hist2.release();
    }

    // 6.9. Адаптивное выравнивание гистограммы с помощью алгоритма CLAHE
    protected void method69(Mat img) {
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

        Mat imgHist = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);
        Mat imgHist2 = new Mat(h, 256, CvType.CV_8UC3, OpenCVUtils.COLOR_WHITE);

        for (int i = 0, j = hist.rows(); i < j; i++) {
            v = Math.round(hist.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_BLACK);
            }

            v = Math.round(hist2.get(i, 0)[0]);
            if (v != 0) {
                Imgproc.line(imgHist2, new org.opencv.core.Point(i, h - 1), new org.opencv.core.Point(i, h - 1 - v), OpenCVUtils.COLOR_BLACK);
            }
        }

        JavaFXUtils.showImage(img2, "Оригинал");
        JavaFXUtils.showImage(imgHist, "Гистограмма до");
        JavaFXUtils.showImage(img3, "CLAHE");
        JavaFXUtils.showImage(imgHist2, "Гистограмма после");

        img.release();
        img2.release();
        img3.release();
        imgHist.release();
        imgHist2.release();
        hist.release();
        hist2.release();
    }

    // 6.10. Различные цветовые палитры
    protected void method610(Mat img) {
        Imgproc.resize(img, img, new Size(), 0.6, 0.6, Imgproc.INTER_LINEAR);
        JavaFXUtils.showImage(img, "Оригинал");

        Mat img2 = new Mat();
        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_AUTUMN);
        JavaFXUtils.showImage(img2, "COLORMAP_AUTUMN");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_BONE);
        JavaFXUtils.showImage(img2, "COLORMAP_BONE");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_COOL);
        JavaFXUtils.showImage(img2, "COLORMAP_COOL");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_HOT);
        JavaFXUtils.showImage(img2, "COLORMAP_HOT");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_HSV);
        JavaFXUtils.showImage(img2, "COLORMAP_HSV");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_JET);
        JavaFXUtils.showImage(img2, "COLORMAP_JET");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_OCEAN);
        JavaFXUtils.showImage(img2, "COLORMAP_OCEAN");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_PARULA);
        JavaFXUtils.showImage(img2, "COLORMAP_PARULA");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_PINK);
        JavaFXUtils.showImage(img2, "COLORMAP_PINK");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_RAINBOW);
        JavaFXUtils.showImage(img2, "COLORMAP_RAINBOW");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_SPRING);
        JavaFXUtils.showImage(img2, "COLORMAP_SPRING");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_SUMMER);
        JavaFXUtils.showImage(img2, "COLORMAP_SUMMER");

        Imgproc.applyColorMap(img, img2, Imgproc.COLORMAP_WINTER);
        JavaFXUtils.showImage(img2, "COLORMAP_WINTER");

        img.release();
        img2.release();
    }
}