package kz.sgq.colorassistant.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.sgq.colorassistant.mvp.model.InfoModelImpl
import kz.sgq.colorassistant.mvp.model.interfaces.InfoModel
import kz.sgq.colorassistant.mvp.view.InfoView

@InjectViewState
class InfoPresenter : MvpPresenter<InfoView>() {
    private val model: InfoModel = InfoModelImpl()

    fun initColor(color: Int){
        model.create(color)
        viewState.createLightness(model.getColor(),
                model.getLightness())
        viewState.createSaturation(model.getColor(),
                model.getSaturation())
    }

    fun init(){
//        viewState.installViewPager()
    }
}