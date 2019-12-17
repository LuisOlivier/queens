package mx.com.queens.ProblemQueens

import java.util.ArrayList;

class ProblemQueens(tamanio: Int) {
    private lateinit var horizontal: BooleanArray
    private lateinit var vertical: BooleanArray
    private lateinit var diagonalSuperior: BooleanArray
    private lateinit var diagonalInferior: BooleanArray
    private val n: Int
    private lateinit var solucion: IntArray
    private var haySolucion = false
    private val soluciones = ArrayList<Any>()
    private val sols = false
    private fun inicializar() {
        horizontal = BooleanArray(n)
        vertical = BooleanArray(n)
        solucion = IntArray(n)
        for (i in 0 until n) {
            horizontal[i] = true
            vertical[i] = true
            solucion[i] = -1
        }
        diagonalInferior = BooleanArray(2 * n - 1)
        diagonalSuperior = BooleanArray(2 * n - 1)
        for (i in 0 until 2 * n - 1) {
            diagonalInferior[i] = true
            diagonalSuperior[i] = true
        }
        haySolucion = false
    }

    private fun buscarSolucion(fila: Int) {
        var col = 0
        while (col < n && !haySolucion) {
            if (horizontal[fila] && vertical[col] && diagonalInferior[col - fila + n - 1] && diagonalSuperior[col + fila]
            ) {
                solucion[fila] = col
                horizontal[fila] = false
                vertical[col] = false
                diagonalInferior[col - fila + n - 1] = false
                diagonalSuperior[col + fila] = false
                if (fila == n - 1 && solucionNueva(solucion)) {
                    haySolucion = true
                } else {
                    if (fila + 1 < n) {
                        buscarSolucion(fila + 1)
                    }
                    if (!haySolucion) {
                        solucion[fila] = -1
                        horizontal[fila] = true
                        vertical[col] = true
                        diagonalInferior[col - fila + n - 1] = true
                        diagonalSuperior[col + fila] = true
                    }
                }
            }
            col++
        }
    }

    fun buscarSoluciones() {
        var flag = true
        while (flag) {
            buscarSolucion(0)
            if (solucionNueva(solucion)) {
                flag = true
                agregarSolucion()
            } else {
                flag = false
            }
            inicializar()
        }
    }

    fun buscarUnaSolucion() {
        buscarSolucion(0)
        agregarSolucion()
    }

    private fun agregarSolucion() {
        soluciones.add(solucion)
    }

    private fun solucionNueva(nuevaSolucion: IntArray): Boolean {
        if (nuevaSolucion[0] == -1) return false
        var esNueva = true
        var i = 0
        while (i < soluciones.size && esNueva) {
            val unaSol = soluciones.get(i) as IntArray
            esNueva = !sonIguales(unaSol, nuevaSolucion)
            i++
        }
        return esNueva
    }

    private fun sonIguales(a: IntArray, b: IntArray): Boolean {
        var i = 0
        var j = 0
        val flag = true
        while (i < a.size && j < b.size) {
            if (a[i] != b[j]) {
                return false
            }
            i++
            j++
        }
        return flag
    }

    fun getSoluciones(): ArrayList<Any> {
        return soluciones
    }

    init {
        if (tamanio < 4) throw NullPointerException()
        n = tamanio
        inicializar()
    }
}