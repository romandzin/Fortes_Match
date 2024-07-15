package com.fortes.match.afar.jogi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.fortes.match.afar.jogi.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private var binding: ActivityGameBinding? = null

    private val elementsImages = arrayOf(
        R.drawable.element_1, R.drawable.element_2,
        R.drawable.element_3, R.drawable.element_4,
        R.drawable.element_5, R.drawable.element_6,
    )

    private val elementsViews: Array<View> by lazy {
        arrayOf(
            binding!!.element11,
            binding!!.element12,
            binding!!.element13,
            binding!!.element14,
            binding!!.element15,
            binding!!.element16,
            binding!!.element21,
            binding!!.element22,
            binding!!.element23,
            binding!!.element24,
            binding!!.element25,
            binding!!.element26,
            binding!!.element31,
            binding!!.element32,
            binding!!.element33,
            binding!!.element34,
            binding!!.element35,
            binding!!.element36,
            binding!!.element41,
            binding!!.element42,
            binding!!.element43,
            binding!!.element44,
            binding!!.element45,
            binding!!.element46,
            binding!!.element51,
            binding!!.element52,
            binding!!.element53,
            binding!!.element54,
            binding!!.element55,
            binding!!.element56,
            binding!!.element61,
            binding!!.element62,
            binding!!.element63,
            binding!!.element64,
            binding!!.element65,
            binding!!.element66,
        )
    }

    var firstClicked: Element? = null
    var secondClicked: Element? = null

    var firstIndex: Int = 0
    var secondIndex: Int = 0

    var clicksEnabled = true

    private val allElements by lazy {
        createList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        allElements.forEach { element ->
            render(element)
        }
        binding!!.restartButton.setOnClickListener {
            refreshScreen()
        }
        setFullWindow()
    }

    private fun setFullWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun createList(): MutableList<Element> {
        return mutableListOf(
            Element(getRandomElement(), 1, 1, binding!!.element11),
            Element(getRandomElement(), 1, 2, binding!!.element12),
            Element(getRandomElement(), 1, 3, binding!!.element13),
            Element(getRandomElement(), 1, 4, binding!!.element14),
            Element(getRandomElement(), 1, 5, binding!!.element15),
            Element(getRandomElement(), 1, 6, binding!!.element16),

            Element(getRandomElement(), 2, 1, binding!!.element21),
            Element(getRandomElement(), 2, 2, binding!!.element22),
            Element(getRandomElement(), 2, 3, binding!!.element23),
            Element(getRandomElement(), 2, 4, binding!!.element24),
            Element(getRandomElement(), 2, 5, binding!!.element25),
            Element(getRandomElement(), 2, 6, binding!!.element26),

            Element(getRandomElement(), 3, 1, binding!!.element31),
            Element(getRandomElement(), 3, 2, binding!!.element32),
            Element(getRandomElement(), 3, 3, binding!!.element33),
            Element(getRandomElement(), 3, 4, binding!!.element34),
            Element(getRandomElement(), 3, 5, binding!!.element35),
            Element(getRandomElement(), 3, 6, binding!!.element36),

            Element(getRandomElement(), 4, 1, binding!!.element41),
            Element(getRandomElement(), 4, 2, binding!!.element42),
            Element(getRandomElement(), 4, 3, binding!!.element43),
            Element(getRandomElement(), 4, 4, binding!!.element44),
            Element(getRandomElement(), 4, 5, binding!!.element45),
            Element(getRandomElement(), 4, 6, binding!!.element46),

            Element(getRandomElement(), 5, 1, binding!!.element51),
            Element(getRandomElement(), 5, 2, binding!!.element52),
            Element(getRandomElement(), 5, 3, binding!!.element53),
            Element(getRandomElement(), 5, 4, binding!!.element54),
            Element(getRandomElement(), 5, 5, binding!!.element55),
            Element(getRandomElement(), 5, 6, binding!!.element56),

            Element(getRandomElement(), 6, 1, binding!!.element61),
            Element(getRandomElement(), 6, 2, binding!!.element62),
            Element(getRandomElement(), 6, 3, binding!!.element63),
            Element(getRandomElement(), 6, 4, binding!!.element64),
            Element(getRandomElement(), 6, 5, binding!!.element65),
            Element(getRandomElement(), 6, 6, binding!!.element66),
        )
    }

    private fun refreshScreen() {
        this.recreate()
    }

    private fun render(element: Element) {
        clicksEnabled = true
        element.view.setBackgroundResource(element.image)
        element.view.setOnClickListener {
            if (clicksEnabled) {
                if (firstClicked == null) firstClicked = element
                else {
                    if (abs(firstClicked!!.row - element.row) == 1 && firstClicked!!.column == element.column) {
                        secondClicked = element
                        swapElementsRow(firstClicked!!, secondClicked!!)
                        clicksEnabled = false
                        lifecycleScope.launch {
                            delay(500L)
                            val firstSwapped = allElements[firstIndex]
                            val secondSwapped = allElements[secondIndex]
                            val resultFirst = checkForLines(firstSwapped)
                            val resultSecond = checkForLines(secondSwapped)
                            if (!resultSecond && !resultFirst) backOldElements()
                            else {
                                delay(100)
                                firstClicked = null
                                secondClicked = null
                                firstIndex = 0
                                secondIndex = 0
                                clicksEnabled = true
                            }
                        }
                    } else if (abs(firstClicked!!.column - element.column) == 1 && firstClicked!!.row == element.row) {
                        secondClicked = element
                        swapElementsColumn(firstClicked!!, secondClicked!!)
                        clicksEnabled = false
                        lifecycleScope.launch {
                            delay(200L)
                            val firstSwapped = allElements[firstIndex]
                            val secondSwapped = allElements[secondIndex]
                            val resultFirst = checkForLines(firstSwapped)
                            val resultSecond = checkForLines(secondSwapped)
                            if (!resultSecond && !resultFirst) backOldElements()
                            else {
                                delay(100)
                                firstClicked = null
                                secondClicked = null
                                firstIndex = 0
                                secondIndex = 0
                                clicksEnabled = true
                            }
                        }
                    }
                    else {
                        firstClicked = null
                    }
                }
            }
        }
    }

    private fun swapElementsRow(first: Element, second: Element) {
        val newFirst = Element(first.image, second.row, first.column, second.view)
        val newSecond = Element(second.image, first.row, second.column, first.view)

        firstIndex = allElements.indexOf(first)
        allElements[firstIndex] = newFirst
        newFirst.view.setBackgroundResource(newFirst.image)

        secondIndex = allElements.indexOf(second)
        allElements[secondIndex] = newSecond
        newSecond.view.setBackgroundResource(newSecond.image)
    }

    private fun swapElementsColumn(first: Element, second: Element) {
        val newFirst = Element(first.image, first.row, second.column, second.view)
        val newSecond = Element(second.image, second.row, first.column, first.view)

        firstIndex = allElements.indexOf(first)

        allElements[firstIndex] = newFirst
        newFirst.view.setBackgroundResource(newFirst.image)

        secondIndex = allElements.indexOf(second)
        allElements[secondIndex] = newSecond
        newSecond.view.setBackgroundResource(newSecond.image)
    }

    private fun checkForLines(element: Element): Boolean {
        val rowMatch = mutableSetOf<Element>()
        val columnMatch = mutableSetOf<Element>()
        var cleared = false

        var clickedRow = allElements.filter { it.row == element.row }
        clickedRow = clickedRow.sortedBy { it.column }
        var clickedColumn =
            allElements.filter { it.column == element.column }
        clickedColumn = clickedColumn.sortedBy { it.row }
        for (i in clickedRow) {
            if (clickedRow.indexOf(i) == 0) {
                if (i.image == element.image && (i.image == clickedRow[clickedRow.indexOf(i) + 1].image)) {
                    rowMatch.add(i)
                }
            }
            else if (clickedRow.indexOf(i) == clickedRow.size - 1) {
                if (i.image == element.image && (i.image == clickedRow[clickedRow.indexOf(i) - 1].image)) {
                    rowMatch.add(i)
                }
            }
            else {
                if (i.image == element.image && (i.image == clickedRow[clickedRow.indexOf(i) - 1].image || i.image == clickedRow[clickedRow.indexOf(i) + 1].image)) {
                    rowMatch.add(i)
                }
            }
        }

        for (i in clickedColumn) {
            if (clickedColumn.indexOf(i) == 0) {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(i) + 1].image)) {
                    columnMatch.add(i)
                }
            }
            else if (clickedColumn.indexOf(i) == clickedColumn.size - 1) {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(i) - 1].image)) {
                    columnMatch.add(i)
                }
            }
            else {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(i) - 1].image || i.image == clickedColumn[clickedColumn.indexOf(i) + 1].image)) {
                    columnMatch.add(i)
                }
            }
        }

        if (rowMatch.size >= 3) {
            Log.d("tag", rowMatch.toString())
            clearMatches(rowMatch.toList(), true)
            cleared = true
        }
        if (columnMatch.size >= 3) {
            Log.d("tag", columnMatch.toString())
            clearMatches(columnMatch.toList(), false)
            cleared = true
        }
        return cleared
    }

    private fun backOldElements() {
        lifecycleScope.launch {
            delay(100)

            firstClicked?.let {
                allElements[firstIndex] = it
                it.view.setBackgroundResource(it.image)
            }

            secondClicked?.let {
                allElements[secondIndex] = it
                it.view.setBackgroundResource(it.image)
            }

            firstClicked = null
            secondClicked = null
            firstIndex = 0
            secondIndex = 0
            clicksEnabled = true
        }
    }

    private fun clearMatches(matches: List<Element>, isRow: Boolean) {
        if (isRow) { //Row logic //TODO change rowLogic
            if (matches[0].row == 1) {
                for (i in matches) {
                    allElements.add(Element(getRandomElement(), 1, i.column, i.view))
                }
            }
            else if (matches[0].row == 2) {
                for (i in matches) {
                    val elements = allElements.filter { it.column == i.column && it.row == 1 }
                    for (y in elements) {
                        allElements.add(Element(getRandomElement(), 1, y.column, y.view))
                        y.row += 1
                        val currentViewIndex = elementsViews.indexOf(y.view)
                        y.view = elementsViews[currentViewIndex + 6]
                    }
                }
            }
            else {
                for (i in matches) {
                    val elements = allElements.filter { it.column == i.column && it.row < i.row }
                    for (y in (elements.size - 1) downTo (0)) {
                        if (y == elements.size - 1) {
                            elements[y].view = i.view
                            elements[y].row = i.row
                        } else {
                            if (y == 0) {
                                allElements.add(
                                    generateNewElement(
                                        elements[y].view,
                                        elements[y].column
                                    )
                                )
                                elements[y].row += 1
                                val currentViewIndex = elementsViews.indexOf(elements[y].view)
                                elements[y].view = elementsViews[currentViewIndex + 6]
                            } else {
                                elements[y].row += 1
                                val currentViewIndex = elementsViews.indexOf(elements[y].view)
                                elements[y].view = elementsViews[currentViewIndex + 6]
                            }
                        }
                    }
                }
            }
            /* allElements.removeAll(matches)
            allElements
            allElements.forEach { element ->
                render(element)
            } */
        }
        else { //Column Logic
            if (matches.size == 6) {
                for (i in 0..5) {
                    allElements.add(
                        Element(
                            getRandomElement(),
                            matches[i].row,
                            matches[i].column,
                            matches[i].view
                        )
                    )
                }
            }
            else {
                matches.sortedBy { it.row }
                val elementsToChange = allElements.filter { it.row <= matches[0].row && it.column == matches[0].column && it.image != matches[0].image }
                if (elementsToChange.isEmpty()) {
                    for (i in matches) {
                        val newElement = Element(getRandomElement(), i.row, i.column, i.view)
                        allElements.add(newElement)
                        newElement.view.setBackgroundResource(newElement.image)
                    }
                }
                else {
                    var changed = 0
                    for (i in elementsToChange.indices) {
                        val newElement = Element(getRandomElement(), elementsToChange[i].row, matches[i].column, elementsToChange[i].view)
                        allElements.add(newElement)
                        newElement.view.setBackgroundResource(newElement.image)
                    }
                    for (i in (elementsToChange.size - 1).downTo(0)) {
                        elementsToChange[i].row = matches[i].row
                        elementsToChange[i].view = matches[i].view
                        elementsToChange[i].view.setBackgroundResource(elementsToChange[i].image)
                        changed += 1
                    }
                    if (changed < matches.size) {
                        for (i in (changed - 1)..<matches.size) {
                            val newElement = Element(getRandomElement(), matches[i].row, matches[i].column, matches[i].view)
                            allElements.add(newElement)
                            newElement.view.setBackgroundResource(newElement.image)
                        }
                    }
                }
            }
        }
        allElements.removeAll(matches)
        allElements
        allElements.forEach { element ->
            render(element)
        }
    }

    private fun generateNewElement(view: View, column: Int): Element {
        return Element(getRandomElement(), 1, column, view)
    }

    private fun getRandomElement(): Int {
        val randomIndex = Random.Default.nextInt(0, 6)
        return elementsImages[randomIndex]
    }
}