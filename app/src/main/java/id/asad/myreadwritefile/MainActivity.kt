package id.asad.myreadwritefile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import id.asad.myreadwritefile.databinding.ActivityMainBinding
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonNew.setOnClickListener(this@MainActivity)
            buttonOpen.setOnClickListener(this@MainActivity)
            buttonSave.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        binding.edtFile.setText("")
        binding.editTitle.setText("")
        Toast.makeText(this, "Clearing File", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val arrayList = ArrayList<String>()
        val path: File = filesDir

        Collections.addAll(arrayList, *path.list() as Array<String>)
        val items = arrayList.toTypedArray<CharSequence>()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { _, item -> loadData(items[item].toString())}
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.fileName)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this, "Loading" + fileModel.fileName + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(this, "Konten harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.fileName = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving" + fileModel.fileName + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}