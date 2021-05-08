package ru.mendel.apps.box2dlite.physics

import ru.mendel.apps.box2dlite.World
import ru.mendel.apps.box2dlite.geometry.Point
import ru.mendel.apps.box2dlite.shapes.CircleShape
import kotlin.math.pow

class SectorBox(val world: World) {
    //, val center: Point, val width: Float, val height: Float
    //не факт что нужны вообще эти данные, мир сам знает в каком секторе лежат объекты
    //сектор мира в нем могут быть тела или могут не быть
    private val bodies = ArrayList<Body>()

    fun addBody(body: Body){
        bodies.add(body)
    }

    fun removeBody(body: Body){
        bodies.remove(body)
    }

    fun clearBodies(){
        bodies.clear()
    }

    fun checkCollide(){
        //пробегаемся по всем телам и смотрим имеют ли они пересечения друг с другом
        val n = bodies.size
        for (i1 in 0 until n){
            if (bodies[i1].shape==null) continue
            for (i2 in i1+1 until n){
                //проверяем был ли контакт
                if (bodies[i2].shape==null) continue
                if (bodies[i1].currentCollide.contains(bodies[i2])) continue
                val s1 = bodies[i1].shape
                val s2 = bodies[i2].shape
                when{
                    s1 is CircleShape && s2 is CircleShape -> {
                        //обе фигуры - круги
                        if (isCollide(s1, s2)){
                            bodies[i1].addCollide(bodies[i2])
                            bodies[i2].addCollide(bodies[i1])
                            //выполняем действие при встрече
                            world.collide?.startCollide(bodies[i1], bodies[i2])
                        }
                    }
                }
            }
        }
    }

    private fun isCollide(s1: CircleShape, s2: CircleShape):Boolean{
        return s1.isCollide(s2)
    }
}