package com.github.intellectualsites.expansions.plotsquared;

import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.intellectualsites.plotsquared.plot.PlotSquared;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import com.github.intellectualsites.plotsquared.plot.util.UUIDHandler;

import me.clip.placeholderapi.PlaceholderAPIPlugin;

public class PlotSquaredApiNew implements PlotSquaredApiInterface {
	
	private PlotSquared api;

	public PlotSquaredApiNew() {
		this.api = PlotSquared.get();
	}
	
	@Override
	public String onPlaceHolderRequest(Player p, String identifier) {
        if (this.api == null || p == null) {
            return "";
        }
        final PlotPlayer pl = PlotPlayer.get(p.getName());
        if (pl == null) {
            return "";
        }
        switch (identifier) {
            case "currentplot_alias": {
                return (pl.getCurrentPlot() != null) ? pl.getCurrentPlot().getAlias() : "";
            }
            case "currentplot_owner": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                final Set<UUID> o = (Set<UUID>)pl.getCurrentPlot().getOwners();
                if (o == null || o.isEmpty()) {
                    return "";
                }
                final UUID uid = (UUID)o.toArray()[0];
                if (uid == null) {
                    return "";
                }
                final String name = UUIDHandler.getName(uid);
                return (name != null) ? name : ((Bukkit.getOfflinePlayer(uid) != null) ? Bukkit.getOfflinePlayer(uid).getName() : "unknown");
            }
            case "currentplot_world": {
                return p.getWorld().getName();
            }
            case "has_plot": {
                return (pl.getPlotCount() > 0) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
            }
            case "allowed_plot_count": {
                return new StringBuilder(String.valueOf(pl.getAllowedPlots())).toString();
            }
            case "plot_count": {
                return new StringBuilder(String.valueOf(pl.getPlotCount())).toString();
            }
            case "currentplot_members_size": {
                if (pl.getCurrentPlot() == null) {
                    return "0";
                }
                return new StringBuilder(String.valueOf(pl.getCurrentPlot().getMembers().size())).toString();
            }
            case "has_build_rights": {
                return (pl.getCurrentPlot() != null) ? ((pl.getCurrentPlot().isAdded(pl.getUUID())) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse()) : "";
            }
            default:
                break;
        }
        return null;
	}


}
