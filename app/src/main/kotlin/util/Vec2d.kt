package util

import java.lang.Math.sqrt

data class Vec2d(val x: Double, val y: Double) {

    operator fun plus(v: Vec2d) = Vec2d(x + v.x, y + v.y)

    

    fun magnitude() = sqrt(x * x + y * y)
}