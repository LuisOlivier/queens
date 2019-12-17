package mx.com.queens.fragments

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_one_result.*
import mx.com.queens.R
import mx.com.queens.adapters.ClickListener
import mx.com.queens.adapters.OneResultAdapter
import mx.com.queens.adapters.OneResultTableAdapter
import mx.com.queens.entities.SolutionEntity
import mx.com.queens.utils.OnBackPressed


class OneResultFragment : BaseFragment(), OnBackPressed {
    private lateinit var responseSolution: ArrayList<SolutionEntity>

    var adaptador: OneResultAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var lista: RecyclerView? = null

    var adaptadorTablero: OneResultTableAdapter? = null

    companion object {
        private const val RESPONSE: String = "RESPONSE"
        fun newInstance(response: ArrayList<SolutionEntity>): OneResultFragment {
            val fragment = OneResultFragment()
            val args = Bundle()
            args.putSerializable(RESPONSE, response)
            fragment.arguments = args
            return fragment

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.arguments?.let {
            this.responseSolution = it.getSerializable(RESPONSE) as ArrayList<SolutionEntity>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(
            R.layout.fragment_one_result,
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

        var title = getString(R.string.str_list_result_adapter_solution)
        tvTitle.setText(title + " " + (responseSolution[0].solution + 1))

        chargeAdapter(responseSolution)

        chargeTableAdapter(responseSolution)

    }

    private fun chargeAdapter(response: ArrayList<SolutionEntity>?) {
        lista = view?.findViewById(R.id.rvRegister)
        //altura definida
        lista?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(view?.context)
        lista?.layoutManager = layoutManager
        //Cambiar codigo
        //tvNoRegisters.visibility = View.INVISIBLE
        //rvRegister.visibility = View.VISIBLE
        adaptador = OneResultAdapter(response, object : ClickListener {
            override fun onClick(vista: View, index: Int) {

            }
        })
        lista?.adapter = adaptador
    }

    private fun chargeTableAdapter(response: ArrayList<SolutionEntity>?) {
        var queensNumber =  response!!.size
        var tableSize = response!!.size * response!!.size
        var rowQueens:ArrayList<Int> = ArrayList<Int>()
        var i  = 0
        response.forEach {
            rowQueens.add((i*queensNumber) + it.y.toString().toInt())
            i++
        }

        var listPositionQueens = ArrayList<Int>()

        for (j in 0..tableSize-1) {
            listPositionQueens.add(0)
        }
        rowQueens!!.forEach {
            listPositionQueens[it] = 1
        }

        var listTable = ArrayList<Boolean>()
        var boolTable:Boolean = false

        for(i in 1..queensNumber){

                if(boolTable){
                    if(queensNumber%2 == 0){
                        boolTable = false
                    }else{
                        boolTable = true
                    }

                }else{
                    if(queensNumber%2 == 0){
                        boolTable = true
                    }else{
                        boolTable = false
                    }
                }


            for (j in 1..queensNumber){
                if(boolTable){
                    listTable.add(true)
                    boolTable = false
                }else{
                    listTable.add(false)
                    boolTable = true
                }

            }
        }

        rvTable.layoutManager = GridLayoutManager(this.activity, queensNumber)
        adaptadorTablero = OneResultTableAdapter(listPositionQueens, listTable , object : ClickListener {
            override fun onClick(vista: View, index: Int) {
            }
        })
        rvTable?.adapter = adaptadorTablero

    }

    override fun onBackPressed(): Boolean {
        this.activity?.let {
            it.finish()
        }
        return true
    }

}
