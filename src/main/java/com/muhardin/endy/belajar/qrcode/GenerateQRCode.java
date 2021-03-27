package com.muhardin.endy.belajar.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class GenerateQRCode {
    public static void main(String[] args) throws Exception {
        String productCode = "03";
        String productionYear = "21";
        Integer productNumberLength = 6;
        Integer productQuantity = 2000;

        String folderOutput = "target/qrcode/"+productCode;
        new File(folderOutput).mkdirs();

        for (int i = 1; i <= productQuantity; i++) {
            String productNumber = productCode + productionYear
                    + String.format("%1$" + productNumberLength + "s", i)
                    .replace(' ', '0');
            System.out.println("Generate QR Code for product number "+productNumber);
            ImageIO.write(generateQRCodeImage(productNumber),
                    "jpg",
                    new File(folderOutput+File.separator
                            +productNumber + ".jpg"));
        }
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
