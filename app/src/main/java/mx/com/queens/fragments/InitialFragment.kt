package mx.com.queens.fragments

import android.app.AlertDialog
import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_initial.*
import mx.com.queens.ProblemQueens.ProblemQueens
import mx.com.queens.R
import mx.com.queens.adapters.ClickListener
import mx.com.queens.adapters.ListInitialResultsAdapter
import mx.com.queens.conections.ConectionSQLiteHelper
import mx.com.queens.ui.Constants
import mx.com.queens.utils.OnBackPressed




class InitialFragment : BaseFragment(), OnBackPressed {


    var adaptador: ListInitialResultsAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var lista: RecyclerView? = null

    companion object {
        fun newInstance(): Fragment {
            var fragment = InitialFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(
            R.layout.fragment_initial,
            container, false
        )
        return view
    }

    override fun onStart() {
        super.onStart()
        etNumber.setText("")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnContinueTo.setOnClickListener{
            if(etNumber.text.toString() != ""){
                if(etNumber.text.toString().toInt() in 8..99) {
                    val reinas = ProblemQueens(etNumber.text.toString().toInt())
                    //reinas.buscarUnaSolucion();
                    reinas.buscarSoluciones()
                    val soluciones = reinas.getSoluciones()
                    for (i in 0 until soluciones.size) {
//                        val aux = soluciones.get(i) as IntArray
//                        println("Solucion " + (i + 1) + ":")
//                        for (j in aux.indices) {
//                            print("(" + (j + 1) + "," + (aux[j] + 1) + ")")
//                        }
//                        println("")
                    }

                    var boolConsult = consultResultRegister(etNumber.text.toString().toInt())

                    if(boolConsult){
                        createAlert(R.string.str_initial_fragment_alert_existing_number){
                        }
                    }else{
                        solutionRegister(soluciones)

                        var consult = consultRow(etNumber.text.toString().toInt())

                        showListResultsFragment(etNumber.text.toString().toInt(), consult)
                    }

                }else{
                    createAlert(R.string.str_initial_fragment_alert_valid_number){
                    }
                }

            }else{
                createAlert(R.string.str_initial_fragment_alert_a_number){
                }
            }

        }

        consult()

    }



    private fun chargeResultsAdapter(response: ArrayList<String>?) {
        lista = view?.findViewById(R.id.rvSolutionsRegisters)
        //altura definida
        lista?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(view?.context)
        lista?.layoutManager = layoutManager
        adaptador = ListInitialResultsAdapter(response, object : ClickListener {
            override fun onClick(vista: View, index: Int) {

                val cadena = response!!.get(index).toString().toInt()

                var consult = consultRow(cadena)

                showListResultsFragment(cadena, consult)

            }
        })
        lista?.adapter = adaptador
    }


    fun showListResultsFragment(cadena: Int ,soluciones: ArrayList<String>) {

        val trans = fragmentManager!!.beginTransaction()
        trans.replace(R.id.mainContainer, ListResultsFragment.newInstance(soluciones, cadena))
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);
        trans.commit();
    }



    override fun onBackPressed(): Boolean {
        this.activity?.let {
            it.finish()
        }
        return true
    }

    fun createAlert(message: Int, function: (Boolean) -> Unit){
        val builder = AlertDialog.Builder(this.activity!!)
        builder.setPositiveButton(R.string.str_initial_fragment_alert_btn_ok) { _, _ ->
            function(true)
        }
        builder.setTitle(R.string.str_initial_fragment_alert_title)
        builder.setMessage(message)
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }

    fun solutionRegister(soluciones: ArrayList<Any>){
        var conn: ConectionSQLiteHelper = ConectionSQLiteHelper(
            context = this.context,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        var db = conn.writableDatabase

        var values = ContentValues()


        for (i in 0 until soluciones.size) {
            val aux = soluciones.get(i) as IntArray
            println("Solucion " + (i + 1) + ":")
            for (j in aux.indices) {
                values.put(Constants.CAMPO_NUMSOLUTION,etNumber.text.toString().toInt())
                values.put(Constants.CAMPO_SOLUTION,i)
                values.put(Constants.CAMPO_X,(aux[j]))
                values.put(Constants.CAMPO_Y,(j))
                var idresult = db.insert(Constants.TABLE_NAME,null, values)
                print("(" + (j + 1) + "," + (aux[j] + 1) + ")")
            }
            println("")
        }
        db.close()

    }

    fun consult(){
        var conn: ConectionSQLiteHelper = ConectionSQLiteHelper(
            context = this.context,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        var solutions: String? = null

        var db = conn.readableDatabase

        var listResults: ArrayList<String> = ArrayList<String>()

        var cursor = db.rawQuery("SELECT DISTINCT (numSolution) FROM solutions", null)

        while (cursor.moveToNext()){
            solutions = cursor.getString(0)
            listResults.add(solutions)

        }

        chargeResultsAdapter(listResults)
    }

    fun consultResultRegister(solution: Int): Boolean{
        var conn: ConectionSQLiteHelper = ConectionSQLiteHelper(
            context = this.context,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        var solutions: String? = null

        var db = conn.readableDatabase

        var listResults: ArrayList<String> = ArrayList<String>()

        var cursor = db.rawQuery("SELECT DISTINCT (numSolution) FROM solutions WHERE numSolution = " + solution, null)

        while (cursor.moveToNext()){
            solutions = cursor.getString(0)
            listResults.add(solutions)

        }

        return if(listResults.size > 0){
            true
        }else{
            false
        }
    }

    fun consultRow(numSolution: Int): ArrayList<String>{
        var conn: ConectionSQLiteHelper = ConectionSQLiteHelper(
            context = this.context,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        var solutionsInt: String? = null

        var db = conn.readableDatabase

        var listResults: ArrayList<String> = ArrayList<String>()

        var cursor = db.rawQuery("SELECT DISTINCT (solution) FROM solutions WHERE numSolution = " + numSolution, null)

        while (cursor.moveToNext()) {
            solutionsInt = cursor.getString(0)
            listResults.add(solutionsInt)
        }


        return listResults
    }


}
