package mx.com.queens.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_loader_layout.*
import mx.com.queens.R


class LoaderFragment : DialogFragment() {

    var isShowing = false

    companion object {
        fun newInstance(): LoaderFragment {
            return LoaderFragment()
        }
        private const val speedLoader = 1.0f
        private const val speedGeneratingToken = 3.0f
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        this.lavLoader.speed = speedLoader
//        this.lavGenerateToken.speed = speedGeneratingToken
        this.isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loader_layout, container, false)
    }


    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager?.beginTransaction()
            ft?.add(this, tag)
            ft?.commitAllowingStateLoss()
        } catch (e: IllegalStateException) {
            Log.d(getString(R.string.tag_dialog_loader), "Exception", e)
        }
    }
}
