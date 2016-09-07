package com.prototypes.stu.homegrown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageGallery {

    private static String inputText;

    /**
     * @param view
     * @return
     */
    public static Bitmap getViewBitmap(View view) {
        int bitmapWidth = view.getWidth();
        int bitmapHeight = view.getHeight();
        Bitmap.Config bitmapConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, bitmapConfig);

        Canvas canvas = new Canvas(bitmap);
        Drawable drawable = view.getBackground();

        if (drawable != null) {
            drawable.draw(canvas);
        } else {
            canvas.drawColor(Color.RED);
        }

        view.draw(canvas);

        return bitmap;
    }

    /**
     * @param context       the context of the dialog
     * @param bitmap        the bitmap to save
     * @param folderName    the folder to save to
     * @param dialogTitle   the title of the input dialog
     * @param positiveLabel the positive button label in the input dialog
     * @param negativeLabel the negative button label in the input dialog
     */
    public static void saveBitmapToStorage(final Context context, final Bitmap bitmap, final String folderName, String dialogTitle, String positiveLabel, String negativeLabel) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(dialogTitle);

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(input);

        builder.setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String fileName = input.getText().toString();

                        File rootFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), folderName);

                        if (!rootFolder.exists()) {
                            rootFolder.mkdir();
                        }


                        OutputStream outputStream = null;
                        File file = new File(rootFolder.getPath(), fileName + ".png");

                        int fileCount = 1;

                        while (file.exists()) {
                            File renameFile = new File(rootFolder.getPath(), fileName + "(" + fileCount++ + ")" + ".png");
                            file.renameTo(renameFile);
                        }

                        Toast.makeText(context, file.getPath(), Toast.LENGTH_LONG).show();

                        try {
                            outputStream = new FileOutputStream(file);

                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                            outputStream.flush();
                            outputStream.close();

                            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

        );

        builder.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
        );

        builder.show();

    }

}