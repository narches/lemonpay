package com.example.lemon.transfer.receipt



import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.lemon.R
import com.example.lemon.transfer.TransferUiState
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream





public fun generateBrandedTransactionPdf(
    context: Context,
    tx: TransferUiState.Success
): File {
    val pdf = PdfDocument()
    val page = pdf.startPage(
        PdfDocument.PageInfo.Builder(595, 842, 1).create()
    )
    val canvas = page.canvas
    val paint = Paint()

    canvas.drawColor(Color.WHITE)

    val logo = ContextCompat.getDrawable(context, R.drawable.lemone)!!
    val bitmap = Bitmap.createBitmap(logo.intrinsicWidth, logo.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val logoCanvas = Canvas(bitmap)
    logo.setBounds(0, 0, logoCanvas.width, logoCanvas.height)
    logo.draw(logoCanvas)

    canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, 80, 80, true), 40f, 40f, null)

    paint.textSize = 16f
    paint.color = Color.BLACK

    canvas.drawText("Reference: ${tx.reference}", 40f, 150f, paint)
    canvas.drawText("To: ${tx.phone}", 40f, 180f, paint)
    canvas.drawText("Amount: ${tx.amount}", 40f, 210f, paint)

    val qr = QRCodeWriter().encode(tx.reference, BarcodeFormat.QR_CODE, 200, 200)
    val qrBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
    for (x in 0 until 200)
        for (y in 0 until 200)
            qrBitmap.setPixel(x, y, if (qr[x, y]) Color.BLACK else Color.WHITE)

    canvas.drawBitmap(qrBitmap, 350f, 150f, null)

    pdf.finishPage(page)

    val file = File(context.cacheDir,
        "receipt_preview.pdf")
    pdf.writeTo(FileOutputStream(file))
    pdf.close()

    return file

}



