package com.github.nnnnusui.stateanchor.forgeeventhandler

import com.github.nnnnusui.stateanchor.AnchorNBTController.Rich.RichItemStack
import com.github.nnnnusui.stateanchor.rich.RichItem.enrich
import com.github.nnnnusui.stateanchor.{AnchorNBTController, StateAnchor}
import net.minecraft.client.Minecraft
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
object PlaceAccordingToAnchor {
  @SubscribeEvent
  def onEntityPlace(event: BlockEvent.EntityPlaceEvent): Unit ={
    val player = Minecraft.getInstance().player
    if(event.getEntity != player) return
    val itemStack = player.getHeldItemMainhand
    val block = itemStack.getItem.getBlock
    if (block != event.getPlacedBlock.getBlock) return
    if (!itemStack.hasAnchorNBT) return

    val anchorNBT
      = itemStack.getAnchorNBT match {
          case Some(nbt) => nbt
          case None      => return
        }
    val stateNBT = anchorNBT.getCompound(StateAnchor.NBTKey.state)
    val stateList = block.getStateContainer.getValidStates

    AnchorNBTController.getStateFromAnchor(stateNBT, stateList) match {
      case Some(state) => event.getWorld.setBlockState(event.getPos, state, 1)
      case None        => ()
    }
  }
  @SubscribeEvent
  def putBlocks(event: BlockEvent.EntityMultiPlaceEvent): Unit ={
    println("EntityMultiPlaceEvent")
  }
}
