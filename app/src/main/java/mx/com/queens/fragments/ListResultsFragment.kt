package mx.com.queens.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.com.queens.R
import mx.com.queens.adapters.ClickListener
import mx.com.queens.adapters.ListResultsAdapter
import mx.com.queens.conections.ConectionSQLiteHelper
import mx.com.queens.entities.SolutionEntity
import mx.com.queens.utils.OnBackPressed


class ListResultsFragment : BaseFragment(), OnBackPressed {
    private lateinit var soluciones: ArrayList<String>
    private var numSolution: Int = 0
    var adaptador: ListResultsAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var lista: RecyclerView? = null

    companion object {
        private const val SOLUCIONS: String = "SOLUCIONS"
        private const val NUMSOLUCION: String = "NUMSOLUCION"
        fun newInstance(soluciones: ArrayList<String>, numSolution: Int): ListResultsFragment {
            val fragment = ListResultsFragment()
            val args = Bundle()
            args.putSerializable(SOLUCIONS, soluciones)
            args.putInt(NUMSOLUCION, numSolution)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.arguments?.let {
            this.soluciones = it.getSerializable(SOLUCIONS) as ArrayList<String>
            this.numSolution = it.getInt(NUMSOLUCION)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(
            R.layout.fragment_list_results,
            container, false
        )
        return view
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chargeAdapter(soluciones)

    }

    fun showOneResultFragment(cadena: Int, response: ArrayList<SolutionEntity>) {
        val trans = fragmentManager!!.beginTransaction()
        trans.replace(R.id.mainContainer, OneResultFragment.newInstance(response))
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        trans.addToBackStack(null);
        trans.commit();
    }

    private fun chargeAdapter(response: ArrayList<String>?) {
        lista = view?.findViewById(R.id.rvRegister)
        //altura definida
        lista?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(view?.context)
        lista?.layoutManager = layoutManager
        adaptador = ListResultsAdapter(response, object : ClickListener {
            override fun onClick(vista: View, index: Int) {

                val cadena = response!!.get(index).toString().toInt()

                var consult = consultRow(cadena)

                showOneResultFragment(cadena, consult)

            }
        })
        lista?.adapter = adaptador
    }

    fun consultRow(solucion: Int): ArrayList<SolutionEntity>{
        var conn: ConectionSQLiteHelper = ConectionSQLiteHelper(
            context = this.context,
            name = "bd_solutions",
            factory = null,
            version = 1
        )

        var solutionsEntity: SolutionEntity? = null

        var db = conn.readableDatabase

        var listResults: ArrayList<SolutionEntity> = ArrayList<SolutionEntity>()

        var cursor = db.rawQuery("SELECT * FROM solutions WHERE numSolution = "+ numSolution!! + " AND solution = " + solucion, null)

        while (cursor.moveToNext()) {
            solutionsEntity = SolutionEntity(cursor.getInt(0), cursor.getInt(1),  cursor.getInt(2), cursor.getInt(3))

            listResults.add(solutionsEntity)
        }


        return listResults
    }


    override fun onBackPressed(): Boolean {
        this.activity?.let {
            it.finish()
        }
        return true
    }

}
