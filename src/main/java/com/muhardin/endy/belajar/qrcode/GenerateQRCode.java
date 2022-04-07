package com.muhardin.endy.belajar.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GenerateQRCode {

    private static final String logoPath = "src/main/resources/logo.png";
    private static final Integer productNumberLength = 6;
    private static final Integer productCodeLength = 2;
    private static final Integer qrCodeSize = 300;

    public static void main(String[] args) throws Exception {
        
        Integer productCodesQty = 20;
        String productionYear = "22";
        Integer productQuantity = 10000;

        for(int i = 0; i<productCodesQty; i++){
            generateQrForProduct(productionYear, i+1, productQuantity);
        }
    }

    public static void generateQrForProduct(
            String productionYear, Integer productCode, Integer productQuantity)
            throws Exception {
        
        String strProductCode = String
                                .format("%1$" + productCodeLength + "s", productCode)
                                .replace(' ', '0');

        System.out.println("--- Generating QR Code for Product Code "+strProductCode+" ---");

        String folderOutput = "target/qrcode/"+strProductCode;
        new File(folderOutput).mkdirs();

        for (int i = 1; i <= productQuantity; i++) {
                String productNumber = strProductCode + productionYear
                        + String.format("%1$" + productNumberLength + "s", i)
                        .replace(' ', '0');
                System.out.println("Generate QR Code for product number "+productNumber);
                BufferedImage qrCode = generateQRCodeImage(productNumber, qrCodeSize);
                BufferedImage qrWithLogo = pasangLogo(qrCode, logoPath);
                ImageIO.write(qrWithLogo, "png",
                        new File(folderOutput+File.separator
                                +productNumber + ".png"));
        }
        
    }

    public static BufferedImage generateQRCodeImage(String barcodeText, Integer size) throws Exception {
        Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode("QBNGold "+barcodeText,
                        BarcodeFormat.QR_CODE,
                        size, size, hints);
        BufferedImage qrCode = MatrixToImageWriter.toBufferedImage(bitMatrix);

        int startingYposition = size-2;

        Graphics2D g = (Graphics2D) qrCode.getGraphics();
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 40));
        Color textColor = Color.BLACK;
        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(barcodeText, (qrCode.getWidth() / 2)   - (fm.stringWidth(barcodeText) / 2), startingYposition);
        return qrCode;
    }

    public static BufferedImage pasangLogo(BufferedImage qrImage, String logoPath) throws Exception {
        BufferedImage logo = ImageIO.read(new File(logoPath));
        int deltaHeight = qrImage.getHeight() - logo.getHeight();
        int deltaWidth = qrImage.getWidth() - logo.getWidth();

        BufferedImage combined = new BufferedImage(
                qrImage.getHeight(),
                qrImage.getWidth(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D) combined.getGraphics();
        g.drawImage(qrImage, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(logo,
                (int) Math.round(deltaWidth / 2),
                (int) Math.round(deltaHeight / 2),
                null);
        return combined;
    }
}
