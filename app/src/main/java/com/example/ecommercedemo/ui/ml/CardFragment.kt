package com.example.ecommercedemo.ui.ml


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ecommercedemo.R
import com.example.ecommercedemo.util.getBitmapFromAsset
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.fragment_card.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CardFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bm = context?.let { getBitmapFromAsset(it, "image3.png") }

        val image = bm?.let { FirebaseVisionImage.fromBitmap(it) }

//        val detector = FirebaseVision.getInstance().cloudTextRecognizer

        val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

        val result = image?.let {
            detector.processImage(it)
                .addOnSuccessListener { firebaseVisionText ->
                    // Task completed successfully
                    // ...
                    view_finder.text = firebaseVisionText.text
                    Log.d("CardFragment", firebaseVisionText.text)
                    firebaseVisionText.textBlocks.forEach { Log.d("CardFragment","firebaseVisionText ${it.text}") }
                }.addOnFailureListener {
                    // Task failed with an exception
                    // ...
                    Log.d("CardFragment","${it.message}")
                    view_finder.text = it.message
                }

        }

    }
}
