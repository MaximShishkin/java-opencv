package ru.shishkin.opencv;

import javafx.beans.property.adapter.JavaBeanProperty;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel {
    private JButton btn1, btn2;

    public Panel() {
        btn1 = new JButton();
        btn1.setText("RESTART");
        btn1.setForeground(Color.BLUE);
        btn1.setFont(new Font("serif", 0, 20));
        btn1.setBounds(700, 70, 200, 100);
        btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                onnn();
            }
        });
        add(btn1);
    }

    void onnn() {
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
        UtilsFrame.showImage(img, "Илон Маск");
        UtilsFrame.showImage(img3, "Илон Маск с THRESH_OTSU");
        img.release();
        img2.release();
        img3.release();
    }
}
