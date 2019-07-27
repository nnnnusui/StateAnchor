package com.github.nnnnusui.stateanchor.forgeeventhandler

import com.github.nnnnusui.stateanchor._
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
    
    if (!itemStack.hasAnchor) return
    val anchor = Anchor(itemStack)
    anchor.setAnchor(itemStack)
  
    event.getWorld.setBlockState(event.getPos, anchor.currentState, 1)
  }
//  @SubscribeEvent
//  def putBlocks(event: BlockEvent.EntityMultiPlaceEvent): Unit ={
//    println("EntityMultiPlaceEvent")
//  }
}
