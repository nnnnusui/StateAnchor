package com.github.nnnnusui.stateanchor.rich

import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.state.IProperty

object RichToNBT{
  object Enrich{
    implicit def map(map: java.util.Map[IProperty[_ <: Comparable[_]], Comparable[_]]): RichMap = new RichMap(map)
    implicit def blockState(blockState: BlockState): RichBlockState = new RichBlockState(blockState)
  }

  class RichBlockState(val blockState: BlockState){
    import Enrich.map
    def toCompoundNBT: CompoundNBT = blockState.getValues.toCompoundNBT
  }
  class RichMap(val map: java.util.Map[IProperty[_ <: Comparable[_]], Comparable[_]]){
    import scala.collection.JavaConverters._
    def toCompoundNBT: CompoundNBT ={
      val nbt = new CompoundNBT
      map.asScala
         .map{    case (key, value)=> (key.getName, value.toString)}
         .foreach{case (key, value)=> nbt.putString(key, value)    }
      nbt
    }
  }
}
