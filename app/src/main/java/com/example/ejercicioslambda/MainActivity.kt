package com.example.ejercicioslambda

import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //esPrimo es una variable que almacena una función
        //Si ponemos return tenemos que poner una etiqueta (esPrimo@)
        //Si solo hay un parámetro, podemos no ponerlo y nos referiremos a el como it
        //Ejemplo con 2 parametros -> val esPrimo:(Int,String) -> Boolean = esPrimo@{num,cadena-> (Resto de codigo es lo mismo)
        val esPrimo:(num: Int) -> Boolean = esPrimo@{
            //Empiezo en 2 porque todos son divisibles entre 1 y
            //termino en it/2 porque a partir de la mitad del numero que estamos dividiendo, todo da decimales 100%
            for (i in 2..it/2) {
                if (it % i == 0) {
                    return@esPrimo false
                }
            }
            true
        }

        val esMagico:(Int) -> Boolean = {
            //Metodo para elevar al cubo, estas obligado a convertirlo a double para que funcione porque es un metodo de la clase double.
            var numElevado: Int = it.toDouble().pow(3).toInt()
            //Variable para hacer la suma de los digitos
            var sumaDig: Int = 0
            //Con este bucle dividimos entre 10, nos quedamos con el resto de la división que es el último dígito y se lo sumamos
            //a la variable y luego lo quitamos con /=
            while (numElevado > 0) {
                sumaDig += numElevado%10
                numElevado /= 10

            }
            //Nos podemos ahorrar todo esto con (sumaDig == it) como return
            var magico: Boolean = false
            if (sumaDig == it) magico = true
            magico
        }

        val esCapicua:(num: Int) -> Boolean = esCapicua@{
            //Funcion de la clase to string para darle la vuelta a un numero y lo volvemos a convertir a int
            //Si coincide con el numero que pasamos es capicua.
            val numInv: Int = it.toString().reversed().toInt()

            if (numInv == it) return@esCapicua true

            false
        }
        //parametro 1: Array de enteros, parametro 2: funcion que recibe un Int y devuelve boolean (f es el nombre de la función)
        fun filtArray(lista: Array<Int>, f: (Int) -> Boolean): ArrayList<Int> {
            //Declaramos un array vacio que va a ser el return
            val listaSalida: ArrayList<Int> = arrayListOf()
            //Recorres los numeros de la lista que pasamos como parametro y si al mandarlo a la función devuelve true se añade.
            for (num: Int in lista) {
                if (f(num)) listaSalida.add(num)

            }
            return listaSalida
        }

        val lista: Array<Int> = arrayOf(1, 2, 6, 11, 17, 121)

        val listaOriginal: TextView = findViewById(R.id.listaOriginal)
        val listaSalida: TextView = findViewById(R.id.ListaSalida)
        val grupoRadioB: RadioGroup = findViewById(R.id.grupoRadioB)
        val filtrar: Button = findViewById(R.id.botonFiltrar)


        filtrar.setOnClickListener {
            //Dependiendo de que radio button tenga seleccionado, mandamos la lista de numeros
            //A un método o a otro
            when (grupoRadioB.checkedRadioButtonId) {
                R.id.radioButton -> listaSalida.text = filtArray(lista, esPrimo).toString()
                R.id.radioButton2 -> listaSalida.text = filtArray(lista, esMagico).toString()
                R.id.radioButton3 -> listaSalida.text = filtArray(lista, esCapicua).toString()

            }
        }

        listaOriginal.text = lista.contentToString()

    }
}