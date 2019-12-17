package mx.com.queens.fragments

import androidx.fragment.app.Fragment
import mx.com.queens.R


abstract class BaseFragment: Fragment() {

    private var loaderFragment: LoaderFragment = LoaderFragment.newInstance()

    fun hideLoader() {
        this.run {
            this.loaderFragment.dismissAllowingStateLoss()
            this.loaderFragment.isShowing = false
        }
    }

    fun showLoader() {
        if (!this.loaderFragment.isShowing) {
            this.run {
                this.loaderFragment.show(this.fragmentManager!!, getString(R.string.tag_dialog_loader))
                this.loaderFragment.isShowing = true
            }
        }
    }

}