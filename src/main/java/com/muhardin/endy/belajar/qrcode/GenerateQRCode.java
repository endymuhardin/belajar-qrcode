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
        String barcodeText = "https://software.endy.muhardin.com";
        BufferedImage hasil = generateQRCodeImage(barcodeText);
        String fileQrCode = "target/qrcode.jpg";
        ImageIO.write(hasil, "jpg", new File(fileQrCode));
    }
    
    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }
}
