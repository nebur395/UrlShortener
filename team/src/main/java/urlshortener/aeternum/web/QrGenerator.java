package urlshortener.aeternum.web;

import com.auth0.jwt.internal.org.apache.commons.codec.EncoderException;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.auth0.jwt.internal.org.apache.commons.io.IOUtils;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.JavaScriptUtils;
import urlshortener.common.repository.ClickRepository;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.web.UrlShortenerController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.zxing.*;

@RestController
public class QrGenerator {
    private static String infoLevel, infoColour, infoLogo;
    private static String vCardText, urlVcard, qrEncoded;
    private static BufferedImage image, overlayImage, combinedImage;

    private static final Logger LOG = LoggerFactory
        .getLogger(QrGenerator.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    public static String generateQR(URI shortedURL, HttpServletRequest request) {
        Client client = ClientBuilder.newClient();
        String shortURL = shortedURL.toString();


        // VCard Starting Text
        StringBuilder output = new StringBuilder();
        output.append("BEGIN:VCARD\n");
        output.append("VERSION:3.0\n");


        // Additions to Vcard
        output.append("URL:" + shortURL + "\n");

        if ((!request.getParameter("fName").equals("")) || (!request.getParameter("lName").equals(""))) {
            output.append("FN:" + request.getParameter("fName") + " " + request.getParameter("lName") + "\n");
        }
        if (!request.getParameter("Email").equals("")) {
            output.append("EMAIL:" + request.getParameter("Email") + "\n");
        }
        if (!request.getParameter("Phone").equals("") && !request.getParameter("Phone").equals("null")) {
            output.append("TEL;TYPE=PREF:" + request.getParameter("Phone") + "\n");
        }
        if (!request.getParameter("Company").equals("")) {
            output.append("ORG:" + request.getParameter("Company") + "\n");
        }
        if ((!request.getParameter("Street").equals("")) || (!request.getParameter("Zip").equals("") && !request.getParameter("Zip").equals("null")) || (!request.getParameter("City").equals("")) || (!request.getParameter("Country").equals(""))) {
            output.append("ADR:" + request.getParameter("Street") + ";" + request.getParameter("City") + ";" + request.getParameter("Zip") + ";" + request.getParameter("Country") + "\n");
        }
        LOG.info("Nombre: " + request.getParameter("fName"));
        LOG.info(request.getParameter("Street"));
        LOG.info(request.getParameter("Zip"));
        LOG.info(request.getParameter("City"));
        LOG.info(request.getParameter("Country"));
        // Final text for Vcard
        output.append("REV:" + getCurrentTimeStamp() + "\n");
        output.append("END:VCARD\n");

        vCardText = output.toString();

        // Taking the correction level selected by the user
        infoLevel = request.getParameter("Level");

        // BEGINING OF QR GENERATION
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix matrix = null;
        HashMap<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // Selection of correction level
        switch (infoLevel) {
            case "L":
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                break;
            case "M":
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
                break;
            case "Q":
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
                break;
            case "H":
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                break;
        }


        try {
            matrix = qrWriter.encode(vCardText, BarcodeFormat.QR_CODE, 500, 500, hints);

            int width = matrix.getWidth();

            image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, width);

            infoColour = request.getParameter("Colour");

            switch (infoColour) {
                case "Black":
                    graphics.setColor(Color.BLACK);
                    break;
                case "Red":
                    graphics.setColor(Color.RED);
                    break;
                case "Blue":
                    graphics.setColor(Color.BLUE);
                    break;
                case "Green":
                    graphics.setColor(Color.GREEN);
                    break;
                case "Yellow":
                    graphics.setColor(Color.YELLOW);
                    break;
                case "Orange":
                    graphics.setColor(Color.ORANGE);
                    break;
                case "Purple":
                    graphics.setColor(new Color(90, 20, 204));
                    break;
                case "Pink":
                    graphics.setColor(Color.PINK);
                    break;
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (matrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

         //If we want an image in the qr we read it
        if (!request.getParameter("Logo").equals("")){
            infoLogo = request.getParameter("Logo");
            LOG.info("Logo enviado " + infoLogo);

            try {
                URL urlLogo = new URL(infoLogo);
                overlayImage =  ImageIO.read(urlLogo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // TODO resize of overlayImage para que quede bien en un qr de 500 x 500
    /*
            BufferedImage resizedOverlay = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedOverlay.createGraphics();
            g.drawImage(overlayImage, 0, 0, 250, 300, null);
            g.dispose();
    */
            combinedImage = new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);


            // Cambiar iverlayImage por resizedOverlay cuando este completado
            Graphics2D g = (Graphics2D)combinedImage.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.drawImage(overlayImage, (int)Math.round(150), (int)Math.round(150), null);

            // Here we codify the image to send it as a String
            Base64 encoder = new Base64();

            ByteOutputStream bos = null;
            try {
                bos = new ByteOutputStream();
                ImageIO.write(combinedImage, "png", bos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] imgUrl = bos.getBytes();


            qrEncoded = encoder.encodeToString(imgUrl);

            return qrEncoded;

        } else {
            // Here we codify the image to send it as a String
            Base64 encoder = new Base64();

            ByteOutputStream bos = null;
            try {
                bos = new ByteOutputStream();
                ImageIO.write(image, "png", bos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] imgUrl = bos.getBytes();


            qrEncoded = encoder.encodeToString(imgUrl);

            return qrEncoded;
        }
    }

    // Method that returns a time Stamp to generate the Vcard
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
