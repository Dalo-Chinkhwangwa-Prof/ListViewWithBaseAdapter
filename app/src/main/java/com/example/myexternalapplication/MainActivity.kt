package com.example.myexternalapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
//    this wil be the name of the file
    private val fileName = "MyNewFile.txt"
//    the external file path we will be using
    private val filePath = "MyExternalPath"

//    the arrayList we will be using to store our names from the external storage file
    private var nameList: MutableList<Human> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        adding an onclick listener to our button
        add_name_button.setOnClickListener { _ ->
//            when the button is clicked call writeToExternal() method
            writeToExternal()
        }
    }

    override fun onResume() {
        super.onResume()
//        call method readExternalStorage() when the activity is resumes/ onResume() is called
        readExternalStorage()
    }
//      method the write to externalStorage
    private fun writeToExternal() {
        val actualFile = File(getExternalFilesDir(filePath), fileName)
        val fOutputStream = FileOutputStream(actualFile, true)

        val inputString = name_edittext.text.toString()
        name_edittext.text.clear()

        fOutputStream.write("\n$inputString".toByteArray())
        fOutputStream.close()

        Toast.makeText(this, "Name Saved Successfully", Toast.LENGTH_LONG).show()
        readExternalStorage()
    }

//    method to read the external storage
    private fun readExternalStorage() {
        try {
//            set the external file directory as well as the filename
            val actualFile = File(getExternalFilesDir(filePath), fileName)
//            instantiate the input stream for the file declared
            val fileInputStream = actualFile.inputStream()
//            create an input stream reader from the file input stream created
            val inputStreamReader = InputStreamReader(fileInputStream)
//            create a buffered reader from the input stream reader
            val bufferedReader = BufferedReader(inputStreamReader)
//            reset the list to have 0 values before adding to the values
            nameList = mutableListOf()
            var inString: String? = null

//            this is where we are reading from our file using the buffered reader and adding every line to
//            our arrayList
            var x = 0
            while ({ inString = bufferedReader.readLine(); inString }() != null) {
                nameList.add(Human(inString.toString(),x)) //Addition of line to arrayList
                x++
            }
//            close the buffered reader after reading from it
            bufferedReader.close()

//            instantiate the array adapter
//            val arrayAdapter = ArrayAdapter<String>(
//                this,
//                R.layout.list_item_view,
//                R.id.guest_name_textview,
//                nameList
//            )
            val newAdapter = MyListAdapter(nameList)
//            set the ListView Adapter to the array adapter we created above
            information_display_listview.adapter = newAdapter
//            set an item click listener to the list view for item clicks
//            information_display_listview.setOnItemClickListener { parent, view, position, id ->
////                make a toast to display the name using the position of the item in the arrayList
////                Toast.makeText(this, nameList[position], Toast.LENGTH_SHORT).show()
////              get the imageview in the layout file and change the background
//                view.findViewById<ImageView>(R.id.my_icon_imageview).background =
//                    getDrawable(R.drawable.ic_launcher_background)
//            }

        } catch (throwable: Throwable) {
//            use our well created ErrorLogger singleton object
            ErrorLogger.logThrowable(throwable)
        }
    }
}