package com.example.fellow_traveller.Trips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fellow_traveller.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class TripPageDriverActivity extends AppCompatActivity {
    private ImageView QRImage;
    public static TextView QRResultTextView;
    private Button scanButton;
    private final int demensionPixels = 700;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_page_driver);
        QRImage = findViewById(R.id.ActivityTripPageDriver_qr_image);
        QRResultTextView = findViewById(R.id.ActivityTripPageDriver_qr_result_textView);
        scanButton = findViewById(R.id.ActivityTripPageDriver_scan_button);
        createQR();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(TripPageDriverActivity.this, ScanQRCodeActivity.class);
                startActivity(mainIntent);
            }
        });

    }
    public void createQR(){
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            //The first value is the string we want to make QR for
            BitMatrix bitMatrix = qrCodeWriter.encode("ft764535", BarcodeFormat.QR_CODE, demensionPixels, demensionPixels);
            Bitmap bitmap = Bitmap.createBitmap(demensionPixels, demensionPixels, Bitmap.Config.RGB_565);

            for(int x = 0; x < demensionPixels; x++){
                for(int y = 0; y < demensionPixels; y++){
                    bitmap.setPixel( x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            QRImage.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
