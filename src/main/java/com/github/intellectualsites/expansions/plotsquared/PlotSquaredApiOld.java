package com.github.intellectualsites.expansions.plotsquared;

import com.intellectualcrafters.plot.PS;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.intellectualcrafters.plot.util.UUIDHandler;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class PlotSquaredApiOld implements PlotSquaredApiInterface {

    private PS api;

    public PlotSquaredApiOld() {
        this.api = PS.get();
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
                final Set<UUID> o = pl.getCurrentPlot().getOwners();
                if (o == null || o.isEmpty()) {
                    return "";
                }
                final UUID uid = (UUID) o.toArray()[0];
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
                return String.valueOf(pl.getAllowedPlots());
            }
            case "plot_count": {
                return String.valueOf(pl.getPlotCount());
            }
            case "currentplot_members": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                if (pl.getCurrentPlot().getMembers() == null && pl.getCurrentPlot().getTrusted() == null) {
                    return "0";
                }
                return String.valueOf(pl.getCurrentPlot().getMembers().size() + pl.getCurrentPlot().getTrusted().size());
            }
            case "currentplot_members_added": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                if (pl.getCurrentPlot().getMembers() == null) {
                    return "0";
                }
                return String.valueOf(pl.getCurrentPlot().getMembers().size());
            }
            case "currentplot_members_trusted": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                if (pl.getCurrentPlot().getTrusted() == null) {
                    return "0";
                }
                return String.valueOf(pl.getCurrentPlot().getTrusted().size());
            }
            case "currentplot_members_denied": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                if (pl.getCurrentPlot().getDenied() == null) {
                    return "0";
                }
                return String.valueOf(pl.getCurrentPlot().getDenied().size());
            }
            case "has_build_rights": {
                return (pl.getCurrentPlot() != null) ? ((pl.getCurrentPlot().isAdded(pl.getUUID())) ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse()) : "";
            }
            case "currentplot_x": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return String.valueOf(pl.getCurrentPlot().getId().x);
            }
            case "currentplot_y": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return String.valueOf(pl.getCurrentPlot().getId().y);
            }
            case "currentplot_xy": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return pl.getCurrentPlot().getId().x + ";" + pl.getCurrentPlot().getId().y;
            }
            case "currentplot_rating": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return String.valueOf(pl.getCurrentPlot().getAverageRating());
            }
            case "currentplot_biome": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return String.valueOf(pl.getCurrentPlot().getBiome());
            }
            default:
                break;
        }
        return null;
    }

}
