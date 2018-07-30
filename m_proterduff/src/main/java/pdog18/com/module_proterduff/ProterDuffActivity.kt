package pdog18.com.module_proterduff

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup


class ProterDuffActivity : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var proterDuffView: ProterDuffView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proter_duff)
        proterDuffView = findViewById<ProterDuffView>(R.id.pdv)


        (findViewById<View>(R.id.rg) as RadioGroup).setOnCheckedChangeListener(this)

    }

    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        when (checkedId) {
            R.id.clear -> proterDuffView!!.setXfermode(PorterDuff.Mode.CLEAR)
            R.id.src -> proterDuffView!!.setXfermode(PorterDuff.Mode.SRC)
            R.id.dst -> proterDuffView!!.setXfermode(PorterDuff.Mode.DST)
            R.id.src_over -> proterDuffView!!.setXfermode(PorterDuff.Mode.SRC_OVER)
            R.id.dst_over -> proterDuffView!!.setXfermode(PorterDuff.Mode.DST_OVER)
            R.id.src_in -> proterDuffView!!.setXfermode(PorterDuff.Mode.SRC_IN)
            R.id.dst_in -> proterDuffView!!.setXfermode(PorterDuff.Mode.DST_IN)
            R.id.src_out -> proterDuffView!!.setXfermode(PorterDuff.Mode.SRC_OUT)
            R.id.dst_out -> proterDuffView!!.setXfermode(PorterDuff.Mode.DST_OUT)
            R.id.src_atop -> proterDuffView!!.setXfermode(PorterDuff.Mode.SRC_ATOP)
            R.id.dst_atop -> proterDuffView!!.setXfermode(PorterDuff.Mode.DST_ATOP)
            R.id.xor -> proterDuffView!!.setXfermode(PorterDuff.Mode.XOR)
            R.id.add -> proterDuffView!!.setXfermode(PorterDuff.Mode.ADD)
            R.id.multiply -> proterDuffView!!.setXfermode(PorterDuff.Mode.MULTIPLY)
            R.id.screen -> proterDuffView!!.setXfermode(PorterDuff.Mode.SCREEN)
            R.id.overlay -> proterDuffView!!.setXfermode(PorterDuff.Mode.OVERLAY)
            R.id.darken -> proterDuffView!!.setXfermode(PorterDuff.Mode.DARKEN)
            R.id.lighten -> proterDuffView!!.setXfermode(PorterDuff.Mode.LIGHTEN)
        }
    }

    fun setBackgroundColor(view: View) {
        proterDuffView!!.setBackgroundColor(Color.GRAY)

    }

    fun clearBackgroundColor(view: View) {
        proterDuffView!!.setBackground(null)
    }
}
