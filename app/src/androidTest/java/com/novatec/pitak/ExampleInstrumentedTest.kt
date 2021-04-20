package com.novatec.pitak


import androidx.test.runner.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.novatec.pitak", appContext.packageName)



        val sequence = sequenceOf(1,2,3,4,5)
        val list = listOf(1,2,3,4,5)


        sequence.map {
            it.toString()
            println(" SEQUENCE    "+it)
        }

//         sequence.forEach {
//             println(   "RESULT SEQUENCE: --     " +it)
//        }

        list.map { it.toString()
            println(" LIST    "+it)
        }

     println(   "RESULT LIST: --     " +list.toString())


    }
}
