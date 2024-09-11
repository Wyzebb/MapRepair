# **Map Repair**
### **The plugin is in its beta phase - use with caution!**
_It is recommended that you save a backup of your world before using this plugin in the event that something goes wrong! Features are still being implemented - if there is one you would like, please make an issue on GitHub!_

<p>
<a href="https://discord.gg/akbd8EPSgr"><img alt="discord-plural" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/social/discord-plural_vector.svg"></a>
  <a href="https://ko-fi.com/wyzebb"><img alt="kofi-singular" height="56" src="https://cdn.jsdelivr.net/npm/@intergrav/devins-badges@3/assets/cozy/donate/kofi-singular_vector.svg"></a>
</p>
This is a simple plugin that repairs block changes to your worlds - great for minigame maps!
<br/>Unlike other plugins, this plugin only repairs the blocks that have changed, reducing lag drastically!<br/>

## **Features**
- Repair subcommand which reverts most changes made to blocks (full list below), in a performance friendly way
- Setmap command which can be executed after editing a world to make it return to that state after the next repair
- World-independent tracking: the plugin repairs the world the player is in
- Multi-language system
- Configurable in plugin.yml
- Repair data saves across server restarts

## **What it repairs**
- Broken blocks
- Placed blocks
- Blockstate information, such as rotation
- Lavacasts
- Explosions from TNT, end crystals, creepers and beds
- Water and lava (source blocks and flowing blocks)
- Blocks being ignited/ primed
- Blocks burned by fire
- Leaves that may decay
- Blocks melting
- Fire burning out
- Blocks fertilised by bone meal
- Fire spreading
- Crops grown by bone meal

_If you need anything else to be tracked, open an issue on the GitHub page!_

<br/>

### Command permissions: `maprepair.command`
<br/>