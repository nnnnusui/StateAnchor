package com.github.nnnnusui

import net.minecraft.block.{Block, BlockState}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.CompoundNBT
import net.minecraft.state.IProperty

import scala.collection.JavaConverters._

package object stateanchor {
  // scala's enrich
  implicit class RichIterable[A](val seq: Seq[A]){
    def getShiftedValue(current: A, shiftSize: Int): Option[A]
        = if (seq.isEmpty) None
          else  seq.indexOf(current) match {
                  case -1    => seq.headOption
                  case index =>  val shiftedIndex = (index +shiftSize) % seq.size
                                 if (shiftedIndex < 0) Some(seq(seq.size + shiftedIndex))
                                 else                  Some(seq(shiftedIndex))
                }
  }
  
  // stateanchor's enrich
  implicit class RichItemStack(val itemStack: ItemStack){
    def hasAnchor: Boolean
        = ( itemStack.hasTag
         && itemStack.getTag.contains(StateAnchor.NBTKey.root) )
  }
  
  // utility: Enrich minecraft objects
  implicit class RichItem(val item: Item){
    def getBlock: Block = Block.getBlockFromItem(item)
  }
  implicit class RichBlockState(val blockState: BlockState){
    def toCompoundNBT: CompoundNBT = blockState.getValues.asScala.toCompoundNBT
  }
  implicit class RichMap(val map: scala.collection.Map[IProperty[_ <: Comparable[_]], Comparable[_]]){
    def toCompoundNBT: CompoundNBT ={
      val nbt = new CompoundNBT
      map.map{    case (key, value)=> (key.getName, value.toString)}
         .foreach{case (key, value)=> nbt.putString(key, value)    }
      nbt
    }
  }
}
