package ru.mendel.apps.box2dlite

import ru.mendel.apps.box2dlite.physics.Body
import ru.mendel.apps.box2dlite.physics.CollideAbstract
import ru.mendel.apps.box2dlite.physics.SectorBox
import ru.mendel.apps.box2dlite.shapes.CircleShape
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class World {
    //класс физического мира, в нем происходят все расчеты
    var width = 0f
    var height = 0f
    var size = 0
    val bodies = ArrayList<Body>()
    val speed = 1f
    val sectors = ArrayList<ArrayList<SectorBox>>()
    var sectorW = 0f
    var sectorH = 0f
    var collide: CollideAbstract? = null

    constructor(){

    }

    constructor(w: Float, h: Float, n: Int){
        //n*n секторов
        width = w
        height = h
        size = n
        createSectors(size)
    }

    private fun createSectors(n: Int){
        //создаем необходимое количество секторов
        sectorW = width/n
        sectorH = height/n
        for (i in 0 until n){
            val arr = ArrayList<SectorBox>()
            for (j in 0 until n){
                val sectorBox = SectorBox(this)
                arr.add(sectorBox)
            }
            sectors.add(arr)
        }
    }

    fun createBody():Body{
        val body = Body(this)
        bodies.add(body)
        return body
    }

    fun act(delta: Float){
        move(delta)
        checkCollide()
        endCollide()
    }

    fun setRandomVelocity(velocity: Float){
        //генерируем случайные скорости для всех объектов
        for (body in bodies){
            body.setRandomVelocity(velocity)
        }
    }

    private fun move(delta: Float){
        //двигаем все тела в соответствии с их скоростями
        clearSectors()
        for (body in bodies){
            body.move(delta, width, height)
            toSector(body)
        }
    }

    private fun checkCollide(){
        //проверяем соударения
        for (row in sectors){
            for (sector in row){
                sector.checkCollide()
            }
        }
    }

    private fun endCollide(){
        //весь endCollide должен быть синхронным!
        for (body in bodies){
            body.endCollide(collide)
        }
    }

    private fun toSector(body: Body){
        val circle = body.shape as CircleShape
        //считаем где лежит центр, в какой строке и в каком столбце
        val row = (circle.center.y/sectorH).toInt()
        val column = (circle.center.x/sectorW).toInt()
        //знаем в каком секторе находится центр
        val rowPlus = ((circle.center.y+circle.radius)/sectorH).toInt()
        val rowMinus = ((circle.center.y-circle.radius)/sectorH).toInt()

        val columnPlus = ((circle.center.x+circle.radius)/sectorW).toInt()
        val columnMinus = ((circle.center.x-circle.radius)/sectorW).toInt()

        val rowMin = min(row, rowMinus)
        val rowMax = max(row, rowPlus)

        val colMin = min(column, columnMinus)
        val colMax = max(column, columnPlus)

        for (r in rowMin..rowMax){
            if (r>=size) continue
            for (c in colMin..colMax){
                if (c>=size) continue
                sectors[r][c].addBody(body)
            }
        }
//        println("row =[$rowMin,$rowMax] col=[$colMin,$colMax]")
    }

    private fun clearSectors(){
        for (a in sectors){
            for (sector in a){
                sector.clearBodies()
            }
        }
    }

}