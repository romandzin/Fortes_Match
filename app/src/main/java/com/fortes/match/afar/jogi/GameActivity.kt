package com.fortes.match.afar.jogi

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.fortes.match.afar.jogi.databinding.ActivityGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.abs
import kotlin.random.Random


class GameActivity : AppCompatActivity(), Navigator {

    private var binding: ActivityGameBinding? = null
    private lateinit var clickPlayer: MediaPlayer
    private lateinit var musicPlayer: MediaPlayer
    private lateinit var explosionPlayer: MediaPlayer

    private val elementsImages = arrayOf(
        R.drawable.element_1, R.drawable.element_2,
        R.drawable.element_3, R.drawable.element_4,
        R.drawable.element_5, R.drawable.element_6,
    )

    private val countDownTimer = object : CountDownTimer(120000, 1000) {
        override fun onTick(p0: Long) {
            val formatter = SimpleDateFormat("mm:ss")
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = p0
            binding?.timesText?.text = formatter.format(calendar.time)
        }

        override fun onFinish() {
            checkResults()
        }

    }

    private fun turnOnSound() {
        clickPlayer = MediaPlayer.create(this, R.raw.click_sound)
        clickPlayer.isLooping = false
        clickPlayer.setVolume(VolumeData.clickVolume, VolumeData.clickVolume)

        explosionPlayer = MediaPlayer.create(this, R.raw.explosion)
        explosionPlayer.isLooping = false
        explosionPlayer.setVolume(VolumeData.clickVolume, VolumeData.clickVolume)

        musicPlayer = MediaPlayer.create(this, R.raw.music)
        musicPlayer.isLooping = true
        musicPlayer.setVolume(VolumeData.musicVolume, VolumeData.musicVolume)
        musicPlayer.start()
    }

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

    var coins = 0

    var element1Checks = 0
    var element2Checks = 0
    var element3Checks = 0
    var element4Checks = 0
    var element5Checks = 0

    var startedToClear = false

    private val allElements by lazy {
        createList()
    }

    override fun onCreate(savedInstanceState: Bundle?) { //TODO добавить звук и таймер
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        countDownTimer.start()
        allElements.forEach { element ->
            render(element)
        }
        binding!!.restartButton.setOnClickListener {
            refreshScreen()
            clickPlayer.start()
        }
        binding!!.settingsButton.setOnClickListener {
            clickPlayer.start()
            showSettingsScreen()
        }
        turnOnSound()
        setFullWindow()
    }

    private fun setFullWindow() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    private fun showWinScreen() {
        binding?.let {
            it.winScreen.root.isVisible = true
            it.winScreen.pointsText.text = "+$coins"
            it.winScreen.settingsButton.setOnClickListener {
                clickPlayer.start()
                showSettingsScreen()
            }
            it.winScreen.exitButton.setOnClickListener {
                clickPlayer.start()
                onBackPressed()
            }
        }
    }

    private fun showLooseScreen() {
        binding?.let {
            it.looseScreen.root.isVisible = true
            it.looseScreen.settingsButton.setOnClickListener {
                clickPlayer.start()
                showSettingsScreen()
            }
            it.looseScreen.exit.setOnClickListener {
                clickPlayer.start()
                onBackPressed()
            }
        }
    }

    private fun checkResults() {
        if (element1Checks >= 8 && element2Checks >= 6 && element3Checks >= 18 && element4Checks >= 15 && element5Checks >= 18) {
            showWinScreen()
        } else showLooseScreen()
    }

    private fun showSettingsScreen() {
        supportFragmentManager.beginTransaction()
            .replace(binding?.fragmentContainer!!.id, VolumeFragment())
            .addToBackStack("name")
            .commit()
        binding?.let {
            it.restartButton.isVisible = false
            it.settingsButton.isVisible = false
            it.fragmentContainer.isVisible = true
        }
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

    private fun render(element: Element) { //TODO Добавить проверку для всех при установке через iterator
        clicksEnabled = true
        element.view.setBackgroundResource(element.image)
        element.view.setOnClickListener {
            clickPlayer.start()
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
                    } else {
                        firstClicked = null
                    }
                }
            }
        }
        setNewPointsData()
    }

    private fun checkForNewLines() {
        val copyOfElementsList = mutableListOf<Element>()
        copyOfElementsList.addAll(allElements)
        try {
            copyOfElementsList.forEach {
                checkForLines(it)
            }
        } catch (e: Exception) {
            copyOfElementsList.clear()
        }

    }

    private fun setNewPointsData() {
        binding?.let {
            it.pointsText.text = coins.toString()
            it.element71Text.text = "$element1Checks/8"
            it.element72Text.text = "$element2Checks/6"
            it.element73Text.text = "$element3Checks/18"
            it.element74Text.text = "$element4Checks/15"
            it.element75Text.text = "$element5Checks/18"
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
        if (firstIndex == -1) {
            val strangeElement = allElements.filter { it.view == first.view }
            val oldIndex = allElements.indexOf(strangeElement[0])
            allElements.add(oldIndex, first)
        }

        allElements[firstIndex] = newFirst
        newFirst.view.setBackgroundResource(newFirst.image)

        secondIndex = allElements.indexOf(second)
        if (secondIndex == -1) {
            val strangeElement = allElements.filter { it.view == second.view }
            val oldIndex = allElements.indexOf(strangeElement[0])
            allElements.add(oldIndex, second)
        }
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
            } else if (clickedRow.indexOf(i) == clickedRow.size - 1) {
                if (i.image == element.image && (i.image == clickedRow[clickedRow.indexOf(i) - 1].image)) {
                    rowMatch.add(i)
                }
            } else {
                if (i.image == element.image && (i.image == clickedRow[clickedRow.indexOf(i) - 1].image || i.image == clickedRow[clickedRow.indexOf(
                        i
                    ) + 1].image)
                ) {
                    rowMatch.add(i)
                }
            }
        }

        for (i in clickedColumn) {
            if (clickedColumn.indexOf(i) == 0) {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(
                        i
                    ) + 1].image)
                ) {
                    columnMatch.add(i)
                }
            } else if (clickedColumn.indexOf(i) == clickedColumn.size - 1) {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(
                        i
                    ) - 1].image)
                ) {
                    columnMatch.add(i)
                }
            } else {
                if (i.image == element.image && (i.image == clickedColumn[clickedColumn.indexOf(
                        i
                    ) - 1].image || i.image == clickedColumn[clickedColumn.indexOf(
                        i
                    ) + 1].image)
                ) {
                    columnMatch.add(i)
                }
            }
        }

        if (rowMatch.size >= 3) {
            getPoints(rowMatch.toList())
            startedToClear = true
            clearMatches(rowMatch.toList(), true)
            explosionPlayer.start()
            cleared = true
        }

        if (columnMatch.size >= 3) {
            getPoints(columnMatch.toList())
            startedToClear = true
            clearMatches(columnMatch.toList(), false)
            explosionPlayer.start()
            cleared = true
        }
        return cleared
    }

    private fun getPoints(elements: List<Element>) {
        when (elements[0].image) {
            R.drawable.element_4 -> {
                coins += 10 * elements.size
                if (element1Checks < 8) {
                    element1Checks += elements.size
                }
            }

            R.drawable.element_6 -> {
                coins += 20 * elements.size
                if (element2Checks < 6) {
                    element2Checks += elements.size
                }
            }

            R.drawable.element_2 -> {
                coins += 30 * elements.size
                if (element3Checks < 18) {
                    element3Checks += elements.size
                }
            }

            R.drawable.element_1 -> {
                coins += 40 * elements.size
                if (element4Checks < 15) {
                    element4Checks += elements.size
                }
            }

            R.drawable.element_5 -> {
                coins += 50 * elements.size
                if (element5Checks < 18) {
                    element5Checks += elements.size
                }
            }
        }
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
        val changed = mutableListOf<Element>()
        if (isRow) {
            matches.sortedBy { it.column }
            allElements.removeAll(matches)
            allElements
            if (matches[0].row == 1) {
                for (i in matches) {
                    val element = Element(getRandomElement(), 1, i.column, i.view)
                    changed.add(element)
                    allElements.add(element)
                }
            } else if (matches[0].row == 2) {
                for (i in matches) {
                    val elements = allElements.filter { it.column == i.column && it.row == 1 }
                    for (y in elements) {
                        val element = Element(getRandomElement(), 1, y.column, y.view)
                        changed.add(element)
                        allElements.add(element)
                        y.row += 1
                        val currentViewIndex = elementsViews.indexOf(y.view)
                        y.view = elementsViews[currentViewIndex + 6]
                        changed.add(y)
                    }
                }
            } else {
                for (i in matches) {
                    val elements =
                        allElements.filter { it.column == i.column && it.row < i.row }
                    elements.sortedBy { it.row }
                    for (y in (elements.size - 1) downTo (0)) {
                        if (y == elements.size - 1) {
                            elements[y].view = i.view
                            elements[y].row = i.row
                            changed.add(elements[y])
                        } else {
                            elements[y].row += 1
                            val currentViewIndex = elementsViews.indexOf(elements[y].view)
                            elements[y].view = elementsViews[currentViewIndex + 6]
                            changed.add(elements[y])
                        }
                    }
                    val element = generateNewElement(
                        elementsViews[i.column - 1],
                        i.column
                    )
                    allElements.add(element)
                    changed.add(element)
                }
            }
            /* //Row logic //TODO change rowLogic
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
                        } */ */
        } else { //Column Logic
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
            } else {
                matches.sortedBy { it.row }
                val elementsToChange =
                    allElements.filter { it.row < matches[0].row && it.column == matches[0].column }
                elementsToChange.sortedBy { it.row }
                if (elementsToChange.isEmpty()) {
                    for (i in matches) {
                        val newElement = Element(getRandomElement(), i.row, i.column, i.view)
                        allElements.add(newElement)
                        newElement.view.setBackgroundResource(newElement.image)
                    }
                } else {
                    var changedElementCount = 0
                    for (i in elementsToChange.indices) {
                        val newElement = Element(
                            getRandomElement(),
                            elementsToChange[i].row,
                            matches[i].column,
                            elementsToChange[i].view
                        )
                        allElements.add(newElement)
                        newElement.view.setBackgroundResource(newElement.image)
                    }
                    for (i in (elementsToChange.size - 1).downTo(0)) {
                        elementsToChange[i].row = matches[i].row
                        elementsToChange[i].view = matches[i].view
                        elementsToChange[i].view.setBackgroundResource(elementsToChange[i].image)
                        changedElementCount += 1
                    }
                    if (changedElementCount < matches.size) {
                        for (i in (matches.size - changedElementCount - 1).downTo(0)/*(changed - 1)..<matches.size */) {
                            val newElement = Element(
                                getRandomElement(),
                                matches[i].row,
                                matches[i].column,
                                matches[i].view
                            )
                            allElements.add(newElement)
                            newElement.view.setBackgroundResource(newElement.image)
                        }
                    }
                }
            }
            allElements.removeAll(matches)
            allElements
            changed.forEach {
                checkForLines(it)
            }
        }
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

    override fun goBack() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
            binding?.let {
                it.restartButton.isVisible = true
                it.settingsButton.isVisible = true
            }
        }
        else onBackPressed()
    }
}