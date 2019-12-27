package com.suppliestracker.Skills;

import com.suppliestracker.SuppliesTrackerPlugin;
import static net.runelite.api.ItemID.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.game.ItemManager;

import javax.inject.Singleton;

@Singleton
public class Farming
{
	private SuppliesTrackerPlugin plugin;
	private ItemManager itemManager;
	private int plantId = 0;
	private int compostId = 0;
	private final int[] ALLOTMENT_SEEDS = new int[]{POTATO_SEED, ONION_SEED, CABBAGE_SEED, TOMATO_SEED, SWEETCORN_SEED, STRAWBERRY_SEED, WATERMELON_SEED, SNAPE_GRASS_SEED};

	/***	Will be switching all to onMenuOptionClicked similar to how
	 *		hardwoods work next update so its automated just pushing this for now
	 ***/
	public Farming(SuppliesTrackerPlugin plugin, ItemManager itemManager)
	{
		this.plugin = plugin;
		this.itemManager = itemManager;
	}

	public void OnChat(String message)
	{
			if (plantId <= 0)
			{
				return;
			}
			String name = itemManager.getItemComposition(plantId).getName().toLowerCase();
			if ( name.contains(" seed"))
			{
				for (int seedId: ALLOTMENT_SEEDS)
				{
					if (plantId == seedId)
					{
						plugin.buildEntries(plantId, 3);
						return;
					}
				}
				plugin.buildEntries(plantId);
			}
			else if (name.contains(" compost") || name.contains("plant cure") || name.contains(" sapling"))
			{
				if (plantId == BOTTOMLESS_COMPOST_BUCKET)
				{
					plugin.buildEntries(compostId);
				}
				else
				{
					plugin.buildEntries(plantId);
				}
			}
	}

	public void setPlantId(int plantId)
	{
		this.plantId = plantId;
	}

	public void setEndlessBucket(String message)
	{

		if (message.toLowerCase().contains("ultracompost"))
		{
			compostId = ULTRACOMPOST;
		}
		else if (message.toLowerCase().contains("supercompost"))
		{
			compostId = SUPERCOMPOST;
		}
		else
		{
			compostId = COMPOST;
		}
	}
}
