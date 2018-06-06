package kz.sgq.colorassistant.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.ComboModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.ComboModel
import kz.sgq.colorassistant.mvp.view.ComboView

@InjectViewState
class ComboPresenter : MvpPresenter<ComboView>() {
    private var model: ComboModel = ComboModelImpl()

    fun initPresenter(list: MutableList<String>){
        model.initList(list)
        viewState.init(model.getSize())
        initHeader()
        initColor()
        initColors()
    }

    fun handlerTextColor(index: Int) {
        viewState.setTextColor(model
                .getColorList()[index])
    }

    fun handlerBackgroundColor(index: Int) {
        viewState.setBackgroundColor(model
                .getColorList()[index])
    }

    fun getColor(index: Int): Int = model.getColorList()[index]

    private fun initHeader() {
        for (i in 0 until model.getSize())
            for (j in 0 until model.getSize())
                if (i + j < model.getSize())
                    viewState.initHeader(i, j, model.getColorList()[i + j])
    }

    private fun initColor() {
        for (i in 0 until model.getSize())
            viewState.initColor(i, model.getColorList()[i])
    }

    private fun initColors() {
        for (i in 0 until model.getSize())
            viewState.initColors(i, model.getNameType(),
                    model.getValue()[i])
    }
}