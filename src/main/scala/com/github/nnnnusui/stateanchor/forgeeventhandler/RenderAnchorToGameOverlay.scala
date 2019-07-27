package com.github.nnnnusui.stateanchor.forgeeventhandler

import com.github.nnnnusui.stateanchor.{Anchor, StateAnchor}
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.{MainWindow, Minecraft}
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = StateAnchor.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
object RenderAnchorToGameOverlay {
  @SubscribeEvent
  def o(event: RenderGameOverlayEvent.Post): Unit ={
    val minecraft = Minecraft.getInstance()
    val window = event.getWindow
    val renderer = minecraft.fontRenderer
    
    val itemStack = minecraft.player.getHeldItemMainhand
    val anchor = Anchor(itemStack)
    
    val baseHeight = window.getScaledHeight - 60F
    val lineHeight = renderer.getWordWrappedHeight(".", 1)
    
    val text = s"${anchor.stateNBT}"
    val y = baseHeight
    drawXCenter(y, text, window, renderer)
  
    val text2 = s"${anchor.currentPropertyName} _ ${anchor.currentValue}"
    val y2 = baseHeight - lineHeight
    drawXCenter(y2, text2, window, renderer)
  }
  
  val colorWHITE = 16777215
  def drawXCenter(y: Float, text: String, window: MainWindow, renderer: FontRenderer): Unit ={
    val textWidth = renderer.getStringWidth(text)
    
    val x = window.getScaledWidth/2 - textWidth/2
    renderer.drawStringWithShadow(text, x, y, colorWHITE)
  }
}
