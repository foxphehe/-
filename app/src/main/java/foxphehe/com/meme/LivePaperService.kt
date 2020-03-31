package foxphehe.com.meme

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.service.wallpaper.WallpaperService
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.WindowManager

import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by foxphehe on 2018/1/26.
 */

class LivePaperService : WallpaperService() {


    /**
     * Must be implemented to return a new instance of the wallpaper's engine.
     * Note that multiple instances may be active at the same time, such as
     * when the wallpaper is currently set as the active wallpaper and the user
     * is in the wallpaper picker viewing a preview of it as well.
     */
    override fun onCreateEngine(): WallpaperService.Engine {
        return MyEngine()
    }

    internal inner class MyEngine : WallpaperService.Engine() {

        //吃饭
        private val syokuzi = ArrayList<Bitmap>()
        //火焰
        private val rousoku = ArrayList<Bitmap>()
        //写东西
        private val hikii = ArrayList<Bitmap>()
        //看书
        private val inemuri = ArrayList<Bitmap>()
        //背帽子
        private val kaeru_niwa = ArrayList<Bitmap>()
        //做手工
        private val sagyou_ie = ArrayList<Bitmap>()
        //整理背包
        private val sitaku_ie = ArrayList<Bitmap>()
        //蜗牛
        private val katatsumuri_niwa = ArrayList<Bitmap>()

        //音符
        private val musicIcon = ArrayList<HashMap<String, Float>>()


        private val wa_name = "wa%02d.png"
        private val rousoku_name = "rousoku%d.png"
        private val hikii_name = "hikii%d.png"
        private val inemuri_name = "inemuri%d.png"
        private val kaeru_niwa_name = "kaeru_niwa_%d.png"
        private val sagyou_ie_name = "sagyou_ie_%d.png"
        private val sitaku_ie_name = "sitaku_ie_%d.png"
        private val katatsumuri_niwa_name = "katatsumuri_niwa_%02d.png"
        private val paint = Paint()
        private var rousoku_index = 0
        private val handler = Handler()
        private var time: Long = 0
        private var running = true
        lateinit var background: Bitmap
        private var bgtransX = 0
        private var screenWidth: Int = 0
        private var screenHeight: Int = 0
        private var frameArray = ArrayList<Int>()
        private var frameArrayKatasumuri = ArrayList<Int>()
        private lateinit var music: Bitmap

        var ratio = 0f
        //看书的坐标轴
        var inemuri_x = 0f
        var inemuri_y = 0f
        //吃饭坐标轴
        var syokuzi_x = 0f
        var syokuzi_y = 0f
        //记仇坐标轴
        var hikii_x = 0f
        var hikii_y = 0f
        //背帽子
        var kaeru_niwa_x = 0f //0.264
        var kaeru_niwa_y = 0f //0.761
        //整理背包
        var sitaku_ie_x = 0f //0.264
        var sitaku_ie_y = 0f //0.681

        var second = 0f
        var musicW = 0f
        var musicH = 0f
        var enaleMusicIcon = 4

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            screenWidth = getScreenWidth()
            screenHeight = getScreenHeight()
            var open: InputStream
            try {
                open = resources.assets.open("back_mainIn.png")
                background = BitmapFactory.decodeStream(open)
                open.close()
                ratio = screenHeight * 1.0f / background.height
                background = scaleBitmap(background, ratio)
                bgtransX = (background.width - screenWidth) / 2
                for (i in 0..2) {
                    open = resources.assets.open(String.format(rousoku_name, i))
                    rousoku.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                }
                for (i in 0..7) {
                    open = resources.assets.open(String.format(inemuri_name, i))
                    inemuri.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                }

                open = resources.assets.open("music.png")
                music = scaleBitmap(BitmapFactory.decodeStream(open), ratio)
                musicW = (music.width / 2).toFloat()
                musicH = (music.height / 2).toFloat()
                open.close()

                //吃饭坐标
                syokuzi_x = (background.width * 0.60 - bgtransX).toFloat()
                syokuzi_y = (screenHeight * 0.58).toFloat()
                //看书坐标
                inemuri_x = (background.width * 0.52 - bgtransX).toFloat()
                inemuri_y = (screenHeight * 0.154).toFloat()
                //记仇坐标轴
                hikii_x = (background.width * 0.32 - bgtransX).toFloat()
                hikii_y = (screenHeight * 0.17).toFloat()
                //背帽子
                kaeru_niwa_x = (background.width * 0.264 - bgtransX).toFloat() //0.264
                kaeru_niwa_y = (screenHeight * 0.761).toFloat() //0.761
                //背帽子
                sitaku_ie_x = (background.width * 0.264 - bgtransX).toFloat() //0.264
                sitaku_ie_y = (screenHeight * 0.681).toFloat() //0.681
            } catch (e: IOException) {
                e.printStackTrace()
            }
            second = getSecond()
            drawFrame()
        }

        override fun onTouchEvent(event: MotionEvent) {
            super.onTouchEvent(event)
            if (enaleMusicIcon > 0)
                return
            enaleMusicIcon = 4
            val x = event.x
            val y = event.y
            val map = HashMap<String, Float>()
            map.put("x", x)
            map.put("y", y)
            map.put("alpha", 255f)
            musicIcon.add(map)
        }

        private fun scaleBitmap(origin: Bitmap, ratio: Float): Bitmap {
            val width = origin.width
            val height = origin.height
            val matrix = Matrix()
            matrix.preScale(ratio, ratio)
            val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
            origin.recycle()
            return newBM
        }

        private fun drawFrame() {
            handler.postDelayed({
                val start = System.currentTimeMillis()
                val canvas = surfaceHolder.lockCanvas()
                //绘制背景图
                paint.alpha = 255
                canvas.drawBitmap(background, -bgtransX.toFloat(), 0f, paint)
                canvas.drawBitmap(rousoku[rousoku_index], (background.width * 0.608 - bgtransX).toFloat(), (screenHeight * 0.846).toFloat(), paint)
                canvas.drawBitmap(rousoku[rousoku_index], (background.width * 0.5325 - bgtransX).toFloat(), (screenHeight * 0.589).toFloat(), paint)
                val hour = getTime()
                initKatatsumuriNiwa()
                val x = (screenWidth + katatsumuri_niwa[0].width) * (1f - second / 60000) - katatsumuri_niwa[0].width
                val y = (screenHeight - katatsumuri_niwa[0].height).toFloat()
                canvas.drawBitmap(katatsumuri_niwa[frameArrayKatasumuri[0]], x, y, paint)
                when (hour) {
                    //睡觉时间
                    in App.getSleep() -> {
                        initSleep()
                        canvas.drawBitmap(inemuri[frameArray[0]], inemuri_x, inemuri_y, paint)
                    }
                    //打瞌睡
                    in App.getSleepy() -> {
                        initReadySleep()
                        val index = frameArray[0]
                        canvas.drawBitmap(inemuri[index], inemuri_x, inemuri_y, paint)
                    }
                    //吃饭时间
                    in App.getSyokuzi() -> {
                        initWa()
                        canvas.drawBitmap(syokuzi[frameArray[0]], syokuzi_x, syokuzi_y, paint)
                    }
                    //记仇
                    in App.getHikii() -> {
                        initHikii()
                        canvas.drawBitmap(hikii[frameArray[0]], hikii_x, hikii_y, paint)
                    }
                    //整理背包
                    in App.getSitaku_ie() -> {
                        initSitakuIe()
                        canvas.drawBitmap(sitaku_ie[frameArray[0]], sitaku_ie_x, sitaku_ie_y, paint)
                    }
                    //背帽子
                    in App.getKaeru_niwa() -> {
                        initKaeruNiwa()
                        canvas.drawBitmap(kaeru_niwa[frameArray[0]], kaeru_niwa_x, kaeru_niwa_y, paint)
                    }
                    //做手工
                    in App.getSagyou_ie() -> {
                        initSagyouIe()
                        canvas.drawBitmap(sagyou_ie[frameArray[0]], syokuzi_x, syokuzi_y, paint)
                    }
                }

                val iter = musicIcon.listIterator()
                while (iter.hasNext()) {
                    val map = iter.next()
                    val alpha = map["alpha"]
                    val x = map["x"]!!
                    val y = map["y"]!!
                    if (alpha!! > 0) {
                        paint.alpha = alpha.toInt()
                        canvas.drawBitmap(music, x - musicW, y - musicH, paint)
                        map["alpha"] = alpha - 5
                        map["y"] = y + 1.5f
                    } else {
                        iter.remove()
                    }
                }


                //看书
//                canvas.drawBitmap(inemuri[inemuri_index], (background.width * 0.52 - bgtransX).toFloat(), (screenHeight * 0.154).toFloat(), paint)
                if (frameArray.size > 0) {
                    frameArray.add(frameArray.get(0))
                    frameArray.removeAt(0)
                }
                if (frameArrayKatasumuri.size > 0) {
                    frameArrayKatasumuri.add(frameArrayKatasumuri.get(0))
                    frameArrayKatasumuri.removeAt(0)
                }
                rousoku_index++
                if (rousoku_index >= 3) {
                    rousoku_index = 0
                }
                surfaceHolder.unlockCanvasAndPost(canvas)
                val end = System.currentTimeMillis()
                val timedis = (end - start)
                if (timedis < 160) {
                    time = 160 - timedis
                } else {
                    time = 0
                }
                second += time
                if (second >= 60 * 1000) {
                    second = 0f
                }
                if (running) {
                    drawFrame()
                }
                if (enaleMusicIcon > 0)
                    enaleMusicIcon--
            }, time)
        }

        //蜗牛
        private fun initKatatsumuriNiwa() {
            if (katatsumuri_niwa.size != 9) {
                for (i in 2..10) {
                    val open = resources.assets.open(String.format(katatsumuri_niwa_name, i))
                    katatsumuri_niwa.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                    frameArrayKatasumuri.add(i - 2)
                }
                for (i in 7 downTo 1) {
                    frameArrayKatasumuri.add(i)
                }
            }
        }

        //记仇
        private fun initHikii() {
            if (hikii.size != 7) {
                clearCache()
                for (i in 0..6) {
                    val open = resources.assets.open(String.format(hikii_name, i))
                    hikii.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                    frameArray.add(i)
                }
            }
        }


        //睡着了
        private fun initSleep() {
            if (inemuri.size != 3) {
                clearCache()
                for (i in 3..5) {
                    val open = resources.assets.open(String.format(inemuri_name, i))
                    inemuri.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                    frameArray.add(i - 3)
                }
                for (i in 0..30) {
                    frameArray.add(2)
                }
                frameArray.add(1)
                frameArray.add(0)
                frameArray.add(1)
                frameArray.add(2)
                frameArray.add(1)
                frameArray.add(0)
            }
        }

        //打瞌睡
        private fun initReadySleep() {
            if (inemuri.size != 8) {
                clearCache()
                for (i in 0..7) {
                    val open = resources.assets.open(String.format(inemuri_name, i))
                    inemuri.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                }
            }
            for (i in 0..7)
                frameArray.add(i)
        }

        //吃饭时间
        private fun initWa() {
            if (syokuzi.size != 12) {
                clearCache()
                for (i in 1..12) {
                    val open = resources.assets.open(String.format(wa_name, i))
                    syokuzi.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                }
                for (i in 0..10) {
                    frameArray.add(i)
                }
                frameArray.add(9)
                frameArray.add(10)
                frameArray.add(9)
                frameArray.add(10)
                frameArray.add(11)
                for (i in 1..9) {
                    frameArray.add(0)
                }
            }
        }

        //背帽子
        private fun initKaeruNiwa() {
            if (kaeru_niwa.size != 3) {
                clearCache()
                for (i in 1..3) {
                    val open = resources.assets.open(String.format(kaeru_niwa_name, i))
                    kaeru_niwa.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                }
                frameArray.add(0)
                frameArray.add(1)
                frameArray.add(2)
                frameArray.add(1)
                frameArray.add(0)
                for (i in 0..9)
                    frameArray.add(0)
            }
        }

        //做手工
        private fun initSagyouIe() {
            if (sagyou_ie.size != 6) {
                clearCache()
                for (i in 1..6) {
                    val open = resources.assets.open(String.format(sagyou_ie_name, i))
                    sagyou_ie.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                    frameArray.add(i - 1)
                }
                for (i in 0..5) {
                    frameArray.add(i)
                }
            }
        }

        //整理背包
        private fun initSitakuIe() {
            if (sitaku_ie.size != 5) {
                clearCache()
                for (i in 1..5) {
                    val open = resources.assets.open(String.format(sitaku_ie_name, i))
                    sitaku_ie.add(scaleBitmap(BitmapFactory.decodeStream(open), ratio))
                    open.close()
                    frameArray.add(i - 1)
                }
                for (i in 4 downTo 0)
                    frameArray.add(i)
                for (i in 0..9)
                    frameArray.add(0)
            }
        }

        private fun clearCache() {
            frameArray.clear()
            if (!hikii.isEmpty()) {
                hikii.forEach { it.recycle() }
                hikii.clear()
            }
            if (!inemuri.isEmpty()) {
                inemuri.forEach { it.recycle() }
                inemuri.clear()
            }
            if (!syokuzi.isEmpty()) {
                syokuzi.forEach { it.recycle() }
                syokuzi.clear()
            }
            if (!kaeru_niwa.isEmpty()) {
                kaeru_niwa.forEach { it.recycle() }
                kaeru_niwa.clear()
            }
            if (!sagyou_ie.isEmpty()) {
                sagyou_ie.forEach { it.recycle() }
                sagyou_ie.clear()
            }
            if (!sitaku_ie.isEmpty()) {
                sitaku_ie.forEach { it.recycle() }
                sitaku_ie.clear()
            }
        }

        /**
         * Called to inform you of the wallpaper becoming visible or
         * hidden.  *It is very important that a wallpaper only use
         * CPU while it is visible.*.
         *
         * @param visible
         */
        override fun onVisibilityChanged(visible: Boolean) {
            running = visible
            if (running) {
                drawFrame()
            } else {
                time = 0
            }
            super.onVisibilityChanged(visible)
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder?) {
            super.onSurfaceDestroyed(holder)
            clearCache()
            music.recycle()
        }
    }

    fun getTime(): Int {
        return Date().toHourInt()
    }

    fun getSecond(): Float {
        return Date().dayTimeInMillis().toFloat()
    }


    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽
     */
    fun getScreenWidth(): Int {
        val wm = App.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.x
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高
     */
    fun getScreenHeight(): Int {
        val wm = App.getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.y
    }


}



