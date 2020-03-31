package foxphehe.com.meme

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.apmem.tools.layouts.FlowLayout
import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {

    lateinit var sp: SharedPreferences

    var setupView: FlowLayout? = null
    var lastBtn: View? = null
    var lastIcon: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sp = getSharedPreferences("setting", Context.MODE_PRIVATE)
        initData()
        btnOK.setOnClickListener {
            saveOption()
        }
        apply.setOnClickListener {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER); intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(this, LivePaperService::class.java));
            startActivity(intent);
            Toast.makeText(this, "Apply Live WallPaper", Toast.LENGTH_LONG).show()
        }
    }

    private fun initData() {
        val alwayhome = sp.getString("alwayhome", "")
        if (!alwayhome.isEmpty()) {
            val list = alwayhome.split(",")
            list.forEachIndexed { index, s ->
                addViewToLayout(s, alwayhomeLayout)
            }
        }
        setSyokuzi()
        setHikii()
        setKaeruNiwa()
        setSagyouIe()
        setSitakuiIe()
        setSleepy()
        setSleep()
        btnSyokuzi.setOnClickListener {
            if (!btnSyokuzi.isSelected) {
                clear()
                syokuziLayout.setVisible()
                setupView = syokuziLayout
                lastBtn = it
                lastIcon = imgSyokuzi
                imgSyokuzi.setImageResource(R.drawable.ic_cut)
            } else {
                syokuziLayout.setGone()
                imgSyokuzi.setImageResource(R.drawable.ic_add)
            }
            btnSyokuzi.isSelected = !btnSyokuzi.isSelected
        }
        btnHikii.setOnClickListener {
            if (!btnHikii.isSelected) {
                clear()
                hikiiLayout.setVisible()
                setupView = hikiiLayout
                lastBtn = it
                lastIcon = imgHikii
                imgHikii.setImageResource(R.drawable.ic_cut)
            } else {
                hikiiLayout.setGone()
                imgHikii.setImageResource(R.drawable.ic_add)
            }
            btnHikii.isSelected = !btnHikii.isSelected
        }
        btnKaeruNiwa.setOnClickListener {
            if (!btnKaeruNiwa.isSelected) {
                clear()
                kaeru_niwaLayout.setVisible()
                setupView = kaeru_niwaLayout
                lastBtn = it
                lastIcon = imgKaeruNiwa
                imgKaeruNiwa.setImageResource(R.drawable.ic_cut)
            } else {
                kaeru_niwaLayout.setGone()
                imgKaeruNiwa.setImageResource(R.drawable.ic_add)
            }
            btnKaeruNiwa.isSelected = !btnKaeruNiwa.isSelected
        }
        btnSagyouIe.setOnClickListener {
            if (!btnSagyouIe.isSelected) {
                clear()
                sagyou_ieLayout.setVisible()
                setupView = sagyou_ieLayout
                lastBtn = it
                lastIcon = imgSagyouIe
                imgSagyouIe.setImageResource(R.drawable.ic_cut)
            } else {
                sagyou_ieLayout.setGone()
                imgSagyouIe.setImageResource(R.drawable.ic_add)
            }
            btnSagyouIe.isSelected = !btnSagyouIe.isSelected
        }
        btnSitakuIe.setOnClickListener {
            if (!btnSitakuIe.isSelected) {
                clear()
                sitaku_ieLayout.setVisible()
                setupView = sitaku_ieLayout
                lastBtn = it
                lastIcon = imgSitakuIe
                imgSitakuIe.setImageResource(R.drawable.ic_cut)
            } else {
                sitaku_ieLayout.setGone()
                imgSitakuIe.setImageResource(R.drawable.ic_add)
            }
            btnSitakuIe.isSelected = !btnSitakuIe.isSelected
        }
        btnSleepy.setOnClickListener {
            if (!btnSleepy.isSelected) {
                clear()
                sleepyLayout.setVisible()
                setupView = sleepyLayout
                lastBtn = it
                lastIcon = imgSleepy
                imgSleepy.setImageResource(R.drawable.ic_cut)
            } else {
                sleepyLayout.setGone()
                imgSleepy.setImageResource(R.drawable.ic_add)
            }
            btnSleepy.isSelected = !btnSleepy.isSelected
        }
        btnSleep.setOnClickListener {
            if (!btnSleep.isSelected) {
                clear()
                sleepLayout.setVisible()
                setupView = sleepLayout
                lastBtn = it
                lastIcon = imgSleep
                imgSleep.setImageResource(R.drawable.ic_cut)
            } else {
                sleepLayout.setGone()
                imgSleep.setImageResource(R.drawable.ic_add)
            }
            btnSleep.isSelected = !btnSleep.isSelected
        }
    }

    fun clear() {
        lastBtn?.isSelected = false
        lastIcon?.setImageResource(R.drawable.ic_add)
        setupView?.setGone()
    }


    //吃饭
    private fun setSyokuzi() {
        val syokuzi = sp.getString("syokuzi", "9,12,19")
        if (!syokuzi.isEmpty()) {
            val list = syokuzi.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, syokuziLayout)
            }
            App.setSyokuzi(array)
        }
    }

    //写东西
    private fun setHikii() {
        val hikii = sp.getString("hikii", "15,16,20")
        if (!hikii.isEmpty()) {
            val list = hikii.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, hikiiLayout)
            }
            App.setHikii(array)
        }
    }

    //背帽子
    private fun setKaeruNiwa() {
        val kaeru_niwa = sp.getString("kaeru_niwa", "10,18")
        if (!kaeru_niwa.isEmpty()) {
            val list = kaeru_niwa.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, kaeru_niwaLayout)
            }
            App.setKaeru_niwa(array)
        }
    }

    //做手工
    private fun setSagyouIe() {
        val sagyou_ie = sp.getString("sagyou_ie", "11,13,14")
        if (!sagyou_ie.isEmpty()) {
            val list = sagyou_ie.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, sagyou_ieLayout)
            }
            App.setSagyou_ie(array)
        }
    }

    //整理背包
    private fun setSitakuiIe() {
        val sitaku_ie = sp.getString("sitaku_ie", "17,21")
        if (!sitaku_ie.isEmpty()) {
            val list = sitaku_ie.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, sitaku_ieLayout)
            }
            App.setSitaku_ie(array)
        }
    }

    //犯困
    private fun setSleepy() {
        val sleepy = sp.getString("sleepy", "22")
        if (!sleepy.isEmpty()) {
            val list = sleepy.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, sleepyLayout)
            }
            App.setSleepy(array)
        }
    }

    //睡觉
    private fun setSleep() {
        val sleep = sp.getString("sleep", "23,0,1,2,3,4,5,6,7,8")
        if (!sleep.isEmpty()) {
            val list = sleep.split(",")
            val array = IntArray(list.size)
            list.forEachIndexed { index, s ->
                array[index] = s.toInt()
                addViewToLayout(s, sleepLayout)
            }
            App.setSleepy(array)
        }
    }

    private fun saveOption() {
        val edit = sp.edit()
        if (alwayhomeLayout.childCount > 0) {
            Log.e("count", alwayhomeLayout.childCount.toString())
            val count = alwayhomeLayout.childCount
            val str = StringBuilder()
            for (i in 0..(count - 1)) {
                val view = ((alwayhomeLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
            }
            edit.putString("alwayhome", str.toString());
        } else {
            edit.putString("alwayhome", "");
        }
        if (syokuziLayout.childCount > 0) {
            val count = syokuziLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((syokuziLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setSyokuzi(array)
            edit.putString("syokuzi", str.toString());
        } else {
            edit.putString("syokuzi", "");
        }

        if (hikiiLayout.childCount > 0) {
            val count = hikiiLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((hikiiLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setHikii(array)
            edit.putString("hikii", str.toString());
        } else {
            edit.putString("hikii", "");
        }

        if (kaeru_niwaLayout.childCount > 0) {
            val count = kaeru_niwaLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((kaeru_niwaLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setKaeru_niwa(array)
            edit.putString("kaeru_niwa", str.toString());
        } else {
            edit.putString("kaeru_niwa", "");
        }


        if (sagyou_ieLayout.childCount > 0) {
            val count = sagyou_ieLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((sagyou_ieLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setSagyou_ie(array)
            edit.putString("sagyou_ie", str.toString());
        } else {
            edit.putString("sagyou_ie", "");
        }


        if (sitaku_ieLayout.childCount > 0) {
            val count = sitaku_ieLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((sitaku_ieLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setSitaku_ie(array)
            edit.putString("sitaku_ie", str.toString());
        } else {
            edit.putString("sitaku_ie", "");
        }

        if (sleepyLayout.childCount > 0) {
            val count = sleepyLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((sleepyLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setSleepy(array)
            edit.putString("sleepy", str.toString());
        } else {
            edit.putString("sleepy", "");
        }

        if (sleepLayout.childCount > 0) {
            val count = sleepLayout.childCount
            val str = StringBuilder()
            val array = IntArray(count)
            for (i in 0..(count - 1)) {
                val view = ((sleepLayout.getChildAt(i) as LinearLayout).getChildAt(0) as LinearLayout).getChildAt(0)
                view as TextView
                val num = view.text.toString().trim()
                str.append(num)
                if (i < (count - 1))
                    str.append(",")
                array[i] = num.toInt()
            }
            App.setSleep(array)
            edit.putString("sleep", str.toString());
        } else {
            edit.putString("sleep", "");
        }

        val commit = edit.commit()
        if (commit) {
            Toast.makeText(this, "Save Succeed", Toast.LENGTH_LONG).show()
        }

    }

    @SuppressLint("InflateParams")
    private fun addViewToLayout(s: String, layout: FlowLayout) {
        val view = layoutInflater.inflate(R.layout.item_text, null)
        val text = view.findViewById<TextView>(R.id.text)
        text.text = s
        layout.addView(view)
        text.setOnClickListener {
            setupView?.let {
                if (view.parent != alwayhomeLayout) {
                    layout.removeView(view)
                    addViewToLayout(text.text.toString().trim(), alwayhomeLayout)
                } else {
                    layout.removeView(view)
                    addViewToLayout(text.text.toString().trim(), it)
                }
            } ?: Toast.makeText(this, "选择一个要配置的动作", Toast.LENGTH_LONG).show()

        }
    }

}
