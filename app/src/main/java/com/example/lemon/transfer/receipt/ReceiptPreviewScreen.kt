package com.example.lemon.transfer.receipt



import android.content.ContentValues
import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.lemon.ui.theme.LemonBlack
import com.example.lemon.ui.theme.LemonYellow
import java.io.File
import java.io.FileOutputStream



@Composable
fun ReceiptPreviewScreen(
    pdfFile: File,
    onDownload: () -> Unit,
    onBack: () -> Unit
) {
    val bitmap = remember(pdfFile) {
        val renderer = PdfRenderer(
            ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY)
        )
        val page = renderer.openPage(0)

        val bmp = Bitmap.createBitmap(
            page.width,
            page.height,
            Bitmap.Config.ARGB_8888
        )

        page.render(bmp, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        page.close()
        renderer.close()

        bmp
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Receipt Preview",
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = onDownload,
            shape = RoundedCornerShape(25.dp),
            modifier = Modifier.fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = LemonYellow,
                contentColor = LemonBlack
            )
        ) {
            Text("Download PDF")
        }

        TextButton(onClick = onBack) {
            Text("Back", color = LemonYellow,)
        }
    }
}

fun downloadPdf(
    context: Context,
    sourceFile: File,
    fileName: String
) {
    val resolver = context.contentResolver

    val values = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val uri = resolver.insert(
        MediaStore.Files.getContentUri("external"),
        values
    ) ?: return

    resolver.openOutputStream(uri)?.use { out ->
        sourceFile.inputStream().copyTo(out)
    }

    Toast.makeText(
        context,
        "Receipt saved to Downloads",
        Toast.LENGTH_LONG
    ).show()
}