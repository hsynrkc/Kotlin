package com.androidapp.artgalerybook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.androidapp.artgalerybook.databinding.ActivityArtBinding
import com.google.android.material.snackbar.Snackbar
import java.io.ByteArrayOutputStream

class ArtActivity : AppCompatActivity() {
    private lateinit var binding : ActivityArtBinding
    private lateinit var activityResultLauncher : ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    var selectedBitmap : Bitmap? = null

    private lateinit var database : SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArtBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)

        registerLauncher()

        val intent = intent
        val info = intent.getStringExtra("info")
        if (info.equals("new")){
            binding.artName.setText("")
            binding.artistName.setText("")
            binding.yearText.setText("")
            binding.saveBtn.visibility = View.VISIBLE
            binding.imageView.setImageResource(R.drawable.select)

        }else{
            binding.saveBtn.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",1)

            val cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?", arrayOf(selectedId.toString()))

            val artNameIx = cursor.getColumnIndex("artName")
            val artArtistNameIx = cursor.getColumnIndex("artistName")
            val artYearIx = cursor.getColumnIndex("artYear")
            val imageIx = cursor.getColumnIndex("image")

            while (cursor.moveToNext()){
                binding.artName.setText(cursor.getString(artNameIx))
                binding.artistName.setText(cursor.getString(artArtistNameIx))
                binding.yearText.setText(cursor.getString(artYearIx))

                val byteArray = cursor.getBlob(imageIx)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                binding.imageView.setImageBitmap(bitmap)
            }
            cursor.close()

        }

    }

    fun save(view: View){
        val artName = binding.artName.text.toString()
        val artistName = binding.artistName.text.toString()
        val artYear = binding.yearText.text.toString()

        if (selectedBitmap != null){
            val smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)

            //görseli veriye çevirme
            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArray = outputStream.toByteArray()

            try {
                //val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)
                database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY, artName VARCHAR, artistName VARCHAR, artYear VARCHAR, image BLOB)")

                val sqlString = "INSERT INTO arts (artName, artistName, artYear, image) VALUES (?,?,?,?)"
                val statement = database.compileStatement(sqlString)
                statement.bindString(1,artName)
                statement.bindString(2,artistName)
                statement.bindString(3,artYear)
                statement.bindBlob(4,byteArray)
                statement.execute()


            }catch (e: Exception){
                e.printStackTrace()
            }

            //kayıt yaptıktan sonra ana ekrana git ve arkadaki açık aktiviteleri kapat
            val intent = Intent(this@ArtActivity,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }

    }

    //görsel boyutu küçültme
    private fun makeSmallerBitmap (image : Bitmap, maximumSize : Int ): Bitmap{
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1){
            //landspace(yatay)
            width = maximumSize
            val scaleHeight = width / bitmapRatio
            height = scaleHeight.toInt()

        }else{
            //portrait(dikey)
            height = maximumSize
            val scaleWidth = height * bitmapRatio
            width = scaleWidth.toInt()

        }
        return Bitmap.createScaledBitmap(image,width,height,true)

    }

    fun select (view: View){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //rationale
                Snackbar.make(view, "Permission needed for galery", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                    //request permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }).show()
            }else {
                //request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //intent
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    val imageData = intentFromResult.data
                    //binding.imageView.setImageURI(imageData)
                    if (imageData != null) {
                        try {
                            if (Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(this@ArtActivity.contentResolver,imageData)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageData)
                                binding.imageView.setImageBitmap(selectedBitmap)
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result ->
            if (result){
                //permission granted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }else{
                //permission denied
                Toast.makeText(this@ArtActivity,"Permission needed!",Toast.LENGTH_LONG).show()
            }

        }
    }
}