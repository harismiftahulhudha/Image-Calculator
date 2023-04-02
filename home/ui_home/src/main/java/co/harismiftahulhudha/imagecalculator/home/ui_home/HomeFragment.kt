package co.harismiftahulhudha.imagecalculator.home.ui_home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.harismiftahulhudha.imagecalculator.core.base.BaseFragment
import co.harismiftahulhudha.imagecalculator.core.extension.decrypt
import co.harismiftahulhudha.imagecalculator.core.extension.encrypt
import co.harismiftahulhudha.imagecalculator.core.extension.setOnSelfDebouncedClickListener
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import co.harismiftahulhudha.imagecalculator.home.ui_home.databinding.FragmentHomeBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.notkamui.keval.keval
import com.shz.imagepicker.imagepicker.ImagePicker
import com.shz.imagepicker.imagepicker.ImagePickerCallback
import com.shz.imagepicker.imagepicker.model.GalleryPicker
import com.shz.imagepicker.imagepicker.model.PickedResult
import dagger.hilt.android.AndroidEntryPoint
import java.io.*

@AndroidEntryPoint
class HomeFragment: BaseFragment(), ImagePickerCallback {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: ImageCalculatorAdapter
    private val requestGalleryPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var countGranted = 0
        permissions.entries.forEach {
            if (it.value) {
                countGranted++
            }
        }
        if (countGranted > 0) {
            startGallery()
        }
    }
    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        var countGranted = 0
        permissions.entries.forEach {
            if (it.value) {
                countGranted++
            }
        }
        if (countGranted > 0) {
            startCamera()
        }
    }
    private var isCamera = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        subscribeListeners()
        subscribeObservers()
    }

    override fun initComponents() {
        binding.apply {
            if (viewModel.switchStorage != -1) {
                rgStorage.check(viewModel.switchStorage)
            }
            if (viewModel.switchStorage == -1) {
                viewModel.switchStorage = rgStorage.checkedRadioButtonId
            }
            rvInput.layoutManager = LinearLayoutManager(requireContext())
            rvInput.setHasFixedSize(true)
            rvInput.setItemViewCacheSize(20)
            adapter = ImageCalculatorAdapter()
            rvInput.adapter = adapter
        }
    }

    override fun subscribeListeners() {
        binding.apply {
            rgStorage.setOnCheckedChangeListener { _, checkedId ->
                viewModel.switchStorage = checkedId
                loadData()
            }
            btnInput.setOnSelfDebouncedClickListener {
                if (BuildConfig.FLAVOR.contains("camera")) {
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        startCamera()
                    } else {
                        requestCameraPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                            )
                        )
                    }
                } else {
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        startGallery()
                    } else {
                        requestGalleryPermissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                            )
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun subscribeObservers() {
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                loadData()
            }
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.event.collect { event ->
                    when (event) {
                        is HomeViewModel.Event.OnCreateImageCalculator -> {
                            viewModel.createImageCalculator(event.payload)
                        }
                        is HomeViewModel.Event.OnGetListImageCalculator -> {
                            adapter.submitList(event.data)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        }
    }

    private fun startGallery() {
        val imagePicker = ImagePicker.Builder(requireActivity().packageName + ".provider", this)
            .useGallery(true)
            .autoRotate(true)
            .multipleSelection(false)
            .galleryPicker(GalleryPicker.NATIVE)
            .build()
        imagePicker.launch(requireContext())
        isCamera = false
    }

    private fun startCamera() {
        val imagePicker = ImagePicker.Builder(requireActivity().packageName + ".provider", this)
            .useCamera(true)
            .autoRotate(true)
            .build()
        imagePicker.launch(requireContext())
        isCamera = true
    }

    override fun onImagePickerResult(result: PickedResult) {
        when (result) {
            PickedResult.Empty -> {
                //
            }
            is PickedResult.Error -> {
                result.throwable.printStackTrace()
            }
            is PickedResult.Single -> {
                try {
                    val pickedImage = result.image
                    val file = pickedImage.file
                    val bitmap = BitmapFactory.decodeFile(file.path)
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    val image = InputImage.fromBitmap(bitmap, 0)
                    recognizer.process(image).addOnSuccessListener { text ->
                        var expressionText = ""
                        text.text.split("\n").forEach { tx ->
                            val convertedString = tx.replace(" ", "").lowercase().replace("x", "*")
                            if (convertedString.contains("*") || convertedString.contains("/") || convertedString.contains("+") || convertedString.contains("-")) {
                                expressionText = convertedString
                                return@forEach
                            }
                        }
                        if (expressionText.isNotBlank()) {
                            var resCount = expressionText.keval().toString()

                            if (resCount.split(".").size > 1 && resCount.split(".")[1] == "0") {
                                resCount = resCount.split(".")[0]
                            }
                            if (viewModel.switchStorage == binding.rbFile.id) {

                                val content = readFromFile().let { data ->
                                    "${file.absolutePath}&&${expressionText.replace("*", " * ").replace("+", " + ").replace("-", " - ").replace("/", " / ")}&&$resCount;;${data}"
                                }
                                writeStringAsFile(content)
                                viewModel.setImageCalculatorFromFile(content)

                            }
                            else {
                                viewModel.onCreateImageCalculator(CreateImageCalculatorPayload(
                                    image = file.absolutePath, input = expressionText.replace("*", " * ").replace("+", " + ").replace("-", " - ").replace("/", " / "), result = resCount
                                ))
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), it.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }

    private fun readFromFile(): String {
        var ret = ""
        try {
            val inputStream: InputStream = requireContext().openFileInput("image_calculator.txt")
            val inputStreamReader = InputStreamReader(inputStream)
            val size: Int = inputStream.available()
            val buffer = CharArray(size)
            inputStreamReader.read(buffer)
            inputStream.close()
            ret = String(buffer).decrypt()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ret
    }

    private fun writeStringAsFile(fileContents: String) {
        try {
            val out = FileWriter(File(requireContext().filesDir, "image_calculator.txt"))
            out.write(fileContents.encrypt())
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadData() {
        binding.apply {
            if (viewModel.switchStorage == rbFile.id) {
                val content = readFromFile()
                viewModel.setImageCalculatorFromFile(content)
            } else {
                viewModel.getListImageCalculator()
            }
        }
    }
}