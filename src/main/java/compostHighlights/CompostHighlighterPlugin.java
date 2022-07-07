package compostHighlights;

import com.google.inject.Provides;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Compost Highlighter"
)
public class CompostHighlighterPlugin extends Plugin
{

	@Getter
	private final Map<TileObject, Tile> patchTiles = new HashMap<>();
	@Inject
	private Client client;

	@Inject
	private CompostHighlighterConfig config;

	@Inject
	private CompostedOverlay compostedOverlay;

	@Inject
	private OverlayManager overlayManager;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Compost Highlighter started!");
		overlayManager.add(compostedOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Compost Highlighter stopped!");
		overlayManager.remove(compostedOverlay);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Compost Highlighter says " + config.greeting(), null);
		}
	}

	@Provides
	CompostHighlighterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(CompostHighlighterConfig.class);
	}


}
