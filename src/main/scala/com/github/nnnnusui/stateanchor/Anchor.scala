package com.github.nnnnusui.stateanchor

import com.github.nnnnusui.stateanchor.StateAnchor.NBTKey
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.state.IProperty

import scala.collection.JavaConverters._

object Anchor{
  def apply(itemStack: ItemStack): Anchor = {
    val tag = itemStack.getOrCreateTag()
    if(!tag.contains(NBTKey.root)) return GenerateDefault.anchor(itemStack)
    
    val anchorNBT = tag.getCompound(NBTKey.root)
    val stateNBT
      = if(anchorNBT.contains(NBTKey.state)) anchorNBT.getCompound(NBTKey.state)
        else                                 GenerateDefault.stateNBT(itemStack)
    val selectedPropertyName
      = if(anchorNBT.contains(NBTKey.selectedProperty)) anchorNBT.getString(NBTKey.selectedProperty)
        else                                            GenerateDefault.selectedPropertyName(itemStack)
    new Anchor(itemStack, selectedPropertyName, stateNBT)
  }
  def createNBT(stateNBT: CompoundNBT, selectedPropertyName: String): CompoundNBT ={
    val anchorNBT = new CompoundNBT
    anchorNBT.putString(StateAnchor.NBTKey.selectedProperty, selectedPropertyName)
    anchorNBT.put(StateAnchor.NBTKey.state, stateNBT)
    anchorNBT
  }
  object GenerateDefault{
    def anchor(itemStack: ItemStack): Anchor
        = new Anchor( itemStack
                     ,selectedPropertyName(itemStack)
                     ,stateNBT(itemStack)             )
    def selectedPropertyName(itemStack: ItemStack): String ={
      val properties = itemStack.getItem.getBlock.getStateContainer.getProperties
      if (properties.isEmpty) ""
      else                    properties.iterator().next().getName
    }
    def stateNBT(itemStack: ItemStack): CompoundNBT
        = itemStack.getItem.getBlock.getStateContainer
         .getBaseState.toCompoundNBT
  }
}

class Anchor(     source             : ItemStack
             ,var currentPropertyName: String
             ,val stateNBT           : CompoundNBT
            ){
  private val stateContainer = source.getItem.getBlock.getStateContainer
  private val properties     = stateContainer.getProperties.asScala
  private lazy val stateList    = stateContainer.getValidStates.asScala
  private lazy val stateNBTList = stateList.map(it =>it.toCompoundNBT)
  
  def selectedProperty: Option[IProperty[_ <: Comparable[_]]]
      = properties.find(it=> it.getName == currentPropertyName) match {
                      case Some(property) => Some(property)
                      case None           => properties.headOption
                    }
  def currentValue: String
      = if(stateNBT.contains(currentPropertyName))
          stateNBT.getString(currentPropertyName).stripSuffix("\"")
        else ""
  def values: Seq[String] = selectedProperty.map(_.getAllowedValues.asScala.map(_.toString).toSeq)
                                            .getOrElse(Seq.empty[String])

  def shiftValue(shiftSize: Int): Unit ={
    val shiftedValue = values.getShiftedValue(currentValue, shiftSize) match {
                         case Some(value) => value
                         case None        => return
                       }
    stateNBT.putString(currentPropertyName, shiftedValue)
  }
  def shiftProperty(shiftSize: Int): Unit ={
    currentPropertyName = properties.toSeq.getShiftedValue(selectedProperty.orNull, shiftSize) match {
                            case Some(value) => value.getName
                            case None        => return
                          }
  }
  def setAnchor(itemStack: ItemStack): Unit ={
    val anchorNBT = Anchor.createNBT(stateNBT, currentPropertyName)
    val tag = itemStack.getOrCreateTag()
    tag.put(StateAnchor.NBTKey.root, anchorNBT)
    itemStack.setTag(tag)
  }
  def currentState: BlockState ={
    val index = stateNBTList.indexOf(stateNBT)
    if (index < 0) stateList.head
    else           stateList(index)
  }
}
