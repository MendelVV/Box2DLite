package ru.mendel.apps.box2dlite.physics

import ru.mendel.apps.box2dlite.World
import ru.mendel.apps.box2dlite.geometry.Point
import ru.mendel.apps.box2dlite.geometry.Vector2D
import ru.mendel.apps.box2dlite.shapes.CircleShape
import ru.mendel.apps.box2dlite.shapes.Shape
import kotlin.random.Random

class Body(val world: World) {

    companion object{
        const val TYPE_NONE = -1
        const val TYPE_STATIC = 0
        const val TYPE_DYNAMIC = 1
    }

    var type = TYPE_NONE

    var shape: Shape? = null//форма
    var linearVelocity: Vector2D? = null
    val currentCollide = ArrayList<Body>()

    val metadata = HashMap<String, Any>()//дополнительная информация, вдруг пригодится

    fun addCollide(body: Body){
        currentCollide.add(body)
    }

    fun endCollide(collide: CollideAbstract?){
        val n = currentCollide.size-1
        for (i in n downTo  0){
            if (!shape!!.isCollide(currentCollide[i].shape!!)){
                collide?.endCollide(this,currentCollide[i])//действие при завершении
                currentCollide[i].currentCollide.remove(this)//оттуда удалили этот
                currentCollide.removeAt(i)//отсюда удалили чужой
            }
        }
    }

    fun move(delta:Float, maxX: Float, maxY: Float){
        if (linearVelocity==null) return
        var dx = linearVelocity!!.x*delta
        var dy = linearVelocity!!.y*delta

        when{
            shape is CircleShape -> {
                val cs = shape as CircleShape
                if (cs.center.x+dx<0 || cs.center.x+dx>maxX){
                    dx=0f//пока ноль но нгужно поменять
                    linearVelocity!!.x=-linearVelocity!!.x
                }
                if (cs.center.y+dy<0 || cs.center.y+dy>maxY){
                    dy=0f//пока ноль но нужно поменять
                    linearVelocity!!.y=-linearVelocity!!.y
                }
                cs.center.x+=dx
                cs.center.y+=dy
            }
        }
    }

    fun setRandomVelocity(velocity: Float){
        if (linearVelocity==null){
            linearVelocity = Vector2D(0f,0f)
        }
        val dx = 2* Random.nextFloat()
        val dy = 2* Random.nextFloat()
        linearVelocity?.changeCoordinates(dx, dy)
        linearVelocity?.changeLength(velocity)
    }

    fun print(){
        if (shape is CircleShape){
            println((shape as CircleShape).center)
        }
    }
}