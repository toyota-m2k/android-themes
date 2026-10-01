package io.github.toyota32k.lib.themes

import android.content.Intent
import android.os.Bundle
import android.view.View
import io.github.toyota32k.binder.exposedDropdownMenuBinding
import io.github.toyota32k.dialog.UtDialogEx
import io.github.toyota32k.dialog.mortal.UtMortalActivity
import io.github.toyota32k.dialog.task.UtImmortalTask
import io.github.toyota32k.dialog.task.createViewModel
import io.github.toyota32k.dialog.task.getViewModel
import io.github.toyota32k.lib.themes.databinding.DialogColorVariationBinding

class ColorVariationDialog : UtDialogEx() {
    private val viewModel by lazy { getViewModel<ColorVariationViewModel>() }
    private lateinit var controls: DialogColorVariationBinding

    override fun preCreateBodyView() {
        title = getString(R.string.color_variation_title)
        heightOption = HeightOption.COMPACT
        widthOption = WidthOption.LIMIT(400)
        leftButtonType = ButtonType.CANCEL
        rightButtonType = ButtonType.OK
        gravityOption = GravityOption.RIGHT_TOP
        draggable = true
    }

    override fun createBodyView(savedInstanceState: Bundle?,inflater: IViewInflater): View {
        controls = DialogColorVariationBinding.inflate(inflater.layoutInflater)
        binder
            .exposedDropdownMenuBinding(controls.dayNightDropdown, viewModel.dayNightMode, NightMode.entries)
            .exposedDropdownMenuBinding(controls.colorContrastDropdown, viewModel.contrastLevel, ContrastLevel.entries)
            .exposedDropdownMenuBinding(controls.themeDropdown, viewModel.themeInfo, viewModel.settings.colorVariationList.themes) { toLabel { it.label } }
        return controls.root
    }

    override fun onPositive() {
        viewModel.save()
        super.onPositive()
    }

    companion object {
        fun show(settings: IThemeSettings, preferRestart:Boolean=false) {
            UtImmortalTask.launchTask(this::class.java.name) {
                createViewModel<ColorVariationViewModel> { initialize(settings) }
                if (showDialog(taskName) { ColorVariationDialog() }.status.ok ) {
                    withOwner {
                        val activity = it.asActivity() as? UtMortalActivity ?: return@withOwner
                        val applyMode = if (preferRestart) ThemeDelegate.ApplyMode.RESTART else ThemeDelegate.ApplyMode.RECREATE
                        settings.themeDelegate.applyTheme(activity, settings.themeData, settings.contrastLevel, settings.dayNightMode, applyMode)
                    }
                }
            }
        }
    }
}