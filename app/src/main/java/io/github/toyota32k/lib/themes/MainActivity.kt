package io.github.toyota32k.lib.themes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.github.toyota32k.binder.Binder
import io.github.toyota32k.binder.command.LiteUnitCommand
import io.github.toyota32k.binder.command.bindCommand
import io.github.toyota32k.dialog.mortal.UtMortalActivity
import io.github.toyota32k.dialog.task.UtImmortalTask
import io.github.toyota32k.lib.themes.sample.R
import io.github.toyota32k.lib.themes.sample.databinding.ActivityMainBinding
import io.github.toyota32k.utils.UtLib

class MainActivity : UtMortalActivity() {
    private lateinit var controls: ActivityMainBinding
    private val binder = Binder()
    override fun onCreate(savedInstanceState: Bundle?) {
        UtLib.initialize(applicationContext)
        Settings.applyTheme(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        controls = ActivityMainBinding.inflate(layoutInflater)
        setContentView(controls.root)

        setupWindowInsetsListener(controls.root)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        binder
            .owner(this)
            .bindCommand(LiteUnitCommand {
                UtImmortalTask.launchTask {
                    ColorVariationDialog.show(Settings)
                }
            }, controls.colorVariationButton)
    }
}