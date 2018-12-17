package pdog18.com.module_evaluator

import android.animation.TypeEvaluator

class IncreaseEvaluator(index: Int) : TypeEvaluator<IncreaseEvaluator.IncreaseBody> {
    val increasingBody: IncreaseBody

    init {
        increasingBody = IncreaseBody(0.0)
        increasingBody.setIndex(index)
    }

    override fun evaluate(fraction: Float, startValue: IncreaseBody?, endValue: IncreaseBody?): IncreaseBody {
        return increasingBody.update(endValue!!.total, fraction)
    }

    class IncreaseBody(total: Double) {
        var left: Int = 0       //小数点前
        var right: Int = 0      //小数点以后
        internal var total: Double = 0.toDouble()
        var factor: Int = 0
        private var index = 2

        init {
            update(total, 1f)
        }

        fun update(endTotal: Double?, fraction: Float): IncreaseBody {
            total = endTotal!! * fraction
            left = total.toInt()
            right = Math.round((total - left) * factor).toInt()


            if (index == 0 && (right >= factor / 2)) {
                left += 1
                right = 0
            }else if (right == factor) { //类似0.99995四舍五入后需要向前进一位
                left += 1
                right = 0
            }

            return this
        }

        /**
         * [index] 保留小数点的位数
         */
        fun setIndex(index: Int) {
            if (index < 0) {
                throw IllegalArgumentException("factor 不能小于0，当前为" + index)
            }

            this.index = index
            this.factor = Math.pow(10.0, index.toDouble()).toInt()
        }
    }
}
