package mx.com.queens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.com.queens.conections.ConectionSQLiteHelper
import mx.com.queens.fragments.InitialFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var conn:ConectionSQLiteHelper  = ConectionSQLiteHelper(
            context = this@MainActivity,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        showListLoginFragment()


    }

    private fun showListLoginFragment() {
        val transaction = this.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContainer, InitialFragment())
        transaction.commit()
    }
}
