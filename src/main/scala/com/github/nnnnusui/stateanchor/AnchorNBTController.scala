package com.github.nnnnusui.stateanchor

import com.github.nnnnusui.stateanchor.StateAnchor.{NBTKey => Key}
import com.github.nnnnusui.stateanchor.rich.RichItem.enrich
import com.github.nnnnusui.stateanchor.rich.RichToNBT.Enrich.blockState
import net.minecraft.block.BlockState
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompoundNBT, INBT}

import scala.collection.JavaConverters._

/*
 * Control NBT
 *   Block{stateanchor:{... this point ...}, ...}
 * in ItemStack.
 */
object AnchorNBTController{

  def putAnchorNBT(itemStack: ItemStack): Boolean ={
    val stateContainer = itemStack.getItem.getBlock.getStateContainer
    val properties   = stateContainer.getProperties
    if (properties.isEmpty) return false

    val tag
      = if(itemStack.hasTag) itemStack.getTag
        else                 new CompoundNBT
    val stateNBT      = stateContainer.getBaseState.toCompoundNBT
    val firstProperty = properties.iterator().next()
    putDefaultNBTToTag(tag, stateNBT, firstProperty.getName)
    itemStack.setTag(tag)
    true
  }
  private def putDefaultNBTToTag(nbt: CompoundNBT, state: INBT, selectedPropertyName: String): CompoundNBT ={
    val defaultContents = generateDefaultNBT(state, selectedPropertyName)
    nbt.put(Key.root, defaultContents)
    nbt
  }
  private def generateDefaultNBT(state: INBT, selectedPropertyName: String): CompoundNBT ={
    val stateAnchor = new CompoundNBT
    stateAnchor.putString(Key.selectedProperty, selectedPropertyName)
    stateAnchor.put(Key.state, state)
    stateAnchor
  }

  def getStateFromAnchor(anchor: CompoundNBT, stateList: java.util.Collection[BlockState]): Option[BlockState]
      = getStateFromAnchor(anchor, stateList.asScala.toSeq)
  def getStateFromAnchor(state: CompoundNBT, stateList: Seq[BlockState]): Option[BlockState] ={
    val index = stateList.map(it =>it.toCompoundNBT)
                         .indexOf(state)
    if (index < 0) return None
    Some.apply(stateList(index))
  }

  object Rich{
    implicit class RichItemStack(val itemStack: ItemStack){
      def hasAnchorNBT: Boolean
          = (itemStack.hasTag
          && itemStack.getTag.contains(StateAnchor.NBTKey.root))

      def getAnchorNBT: Option[CompoundNBT]
          = hasAnchorNBT match {
              case true  => Some(itemStack.getTag.getCompound(StateAnchor.NBTKey.root))
              case false => None
            }
    }
  }
}