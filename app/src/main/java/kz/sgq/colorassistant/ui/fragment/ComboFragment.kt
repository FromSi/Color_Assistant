/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.ui.fragment

import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_combo.*
import kotlinx.android.synthetic.main.item_combo_color.view.*
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.mvp.presenter.ComboPresenter
import kz.sgq.colorassistant.mvp.view.ComboView
import kz.sgq.colorassistant.ui.activity.ComboActivity

class ComboFragment : MvpAppCompatFragment(), ComboView {
    @InjectPresenter
    lateinit var presenter: ComboPresenter

    private var backgroundColorList: MutableList<ImageView> = arrayListOf()
    private var textColorList: MutableList<ImageView> = arrayListOf()
    private var cardList: MutableList<CardView> = arrayListOf()
    private var list: MutableList<String> = arrayListOf()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_combo, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.initPresenter(list)
        initListImageView(view)
        initListTextView(view)
        initListCardView(view)
    }

    fun initPresenter(list: MutableList<String>) {
        this.list = list
    }

    override fun init(size: Int) {
        removeView(size)
    }

    override fun initHeader(i: Int, j: Int, color: Int) {
        when (j) {
            0 -> {
                cardList[i].imgOne.setBackgroundColor(color)
                cardList[i].imgOne.visibility = View.VISIBLE
            }
            1 -> {
                cardList[i].imgTwo.setBackgroundColor(color)
                cardList[i].imgTwo.visibility = View.VISIBLE
            }
            2 -> {
                cardList[i].imgThree.setBackgroundColor(color)
                cardList[i].imgThree.visibility = View.VISIBLE
            }
            3 -> {
                cardList[i].imgFour.setBackgroundColor(color)
                cardList[i].imgFour.visibility = View.VISIBLE
            }
            4 -> {
                cardList[i].imgFive.setBackgroundColor(color)
                cardList[i].imgFive.visibility = View.VISIBLE
            }
        }
    }

    override fun initColor(i: Int, color: Int) {
        cardList[i].imgColor
                .setBackgroundColor(color)
        initExample(i, color)
    }

    override fun initColors(i: Int, nameTypeColor: String, value: String) {
        cardList[i].nameTypeColor.text = nameTypeColor
        cardList[i].value.text = value
    }

    override fun setBackgroundColor(color: Int) {
        background.setBackgroundColor(color)
    }

    override fun setTextColor(color: Int) {
        text.setTextColor(color)
    }

    private fun removeView(index: Int) {
        when (index) {
            3 -> {
                containerCard.removeView(cardFour)
                containerCard.removeView(cardFive)
            }
            4 -> containerCard.removeView(cardFive)
        }
    }

    private fun initClickBackgroundColor(){
        for (i in 0..4)
            backgroundColorList[i].setOnClickListener {
                presenter.handlerBackgroundColor(i)
            }
    }

    private fun initClickTextColor(){
        for(i in 0..4)
            textColorList[i].setOnClickListener {
                presenter.handlerTextColor(i)
            }
    }

    private fun initClickCardView(){
        for(i in 0..4)
            cardList[i].setOnClickListener {
                (activity as ComboActivity).initInfo(presenter.getColor(i))
            }
    }

    private fun initListImageView(view: View) {
        backgroundColorList.add(view.findViewById(R.id.backgroundColorOne))
        backgroundColorList.add(view.findViewById(R.id.backgroundColorTwo))
        backgroundColorList.add(view.findViewById(R.id.backgroundColorThree))
        backgroundColorList.add(view.findViewById(R.id.backgroundColorFour))
        backgroundColorList.add(view.findViewById(R.id.backgroundColorFive))
        initClickBackgroundColor()
    }

    private fun initListTextView(view: View) {
        textColorList.add(view.findViewById(R.id.textColorOne))
        textColorList.add(view.findViewById(R.id.textColorTwo))
        textColorList.add(view.findViewById(R.id.textColorThree))
        textColorList.add(view.findViewById(R.id.textColorFour))
        textColorList.add(view.findViewById(R.id.textColorFive))
        initClickTextColor()
    }

    private fun initListCardView(view: View) {
        cardList.add(view.findViewById(R.id.cardOne))
        cardList.add(view.findViewById(R.id.cardTwo))
        cardList.add(view.findViewById(R.id.cardThree))
        cardList.add(view.findViewById(R.id.cardFour))
        cardList.add(view.findViewById(R.id.cardFive))
        initClickCardView()
    }

    private fun initExample(index: Int, color: Int) {
        backgroundColorList[index].visibility = View.VISIBLE
        backgroundColorList[index].setBackgroundColor(color)
        textColorList[index].visibility = View.VISIBLE
        textColorList[index].setBackgroundColor(color)
        if (index == 0)
            background.setBackgroundColor(color)
        if (index == 1)
            text.setTextColor(color)
    }
}