package com.github.nnnnusui.stateanchor

import net.minecraft.item.ItemStack
import net.minecraft.nbt.{CompoundNBT, INBT}

/*
 * Control NBT
 *   Block{stateanchor:{... this point ...}, ...}
 * in ItemStack.
 */
object AnchorNBTController{
  import com.github.nnnnusui.stateanchor.rich.RichItem.enrich
  import com.github.nnnnusui.stateanchor.rich.RichToNBT.Enrich.blockState

  def putAnchorNBT(itemStack: ItemStack): Boolean ={
    val defaultState = itemStack.getItem.getBlock.getDefaultState
    val properties   = defaultState.getProperties
    if (properties.isEmpty) return false

    val tag
      = if(itemStack.hasTag) itemStack.getTag
        else                 new CompoundNBT
    val stateNBT      = defaultState.toCompoundNBT
    val firstProperty = properties.iterator().next()
    putDefaultToTag(tag, stateNBT, firstProperty.getName)
    itemStack.setTag(tag)
    true
  }

  import com.github.nnnnusui.stateanchor.StateAnchor.{NBTKey => Key}

  private def putDefaultToTag(nbt: CompoundNBT, state: INBT, selectedPropertyName: String): CompoundNBT ={
    val defaultContents = generateDefault(state, selectedPropertyName)
    nbt.put(Key.root, defaultContents)
    nbt
  }
  private def generateDefault(state: INBT, selectedPropertyName: String): CompoundNBT ={
    val stateAnchor = new CompoundNBT
    stateAnchor.putString(Key.selectedProperty, selectedPropertyName)
    stateAnchor.put(Key.state,state)
    stateAnchor
  }
}