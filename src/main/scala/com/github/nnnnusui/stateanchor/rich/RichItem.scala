package com.github.nnnnusui.stateanchor.rich

import net.minecraft.block.Block
import net.minecraft.item.Item

class RichItem(val item: Item){
  def getBlock: Block = Block.getBlockFromItem(item)
}
object RichItem{
  implicit def enrich(item: Item): RichItem = new RichItem(item)
}
