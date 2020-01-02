package com.github.intellectualsites.expansions.plotsquared;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.flag.GlobalFlagContainer;
import com.plotsquared.core.plot.flag.PlotFlag;
import com.plotsquared.core.util.uuid.UUIDHandler;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlotSquaredApi implements PlotSquaredApiInterface {

    private PlotSquared api;
    private Pattern flagPattern = Pattern.compile("currentplot_flag_(.+)");

    public PlotSquaredApi() {
        this.api = PlotSquared.get();
    }

    @Override
    public String onPlaceHolderRequest(Player p, String identifier) {
        if (this.api == null || p == null) {
            return "";
        }
        final PlotPlayer pl = PlotPlayer.get(p.getName());
        final Plot plot = pl.getCurrentPlot();
        if (pl == null) {
            return "";
        }

        if (identifier.startsWith("has_plot_")) {
            if (identifier.split("has_plot_").length != 2) return null;

            identifier = identifier.split("has_plot_")[1];
            return pl.getPlotCount(identifier) > 0 ? PlaceholderAPIPlugin.booleanTrue() : PlaceholderAPIPlugin.booleanFalse();
        }

        if (identifier.startsWith("plot_count_")) {
            if (identifier.split("plot_count_").length != 2) return null;

            identifier = identifier.split("plot_count_")[1];
            return String.valueOf(pl.getPlotCount(identifier));
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
                return String.valueOf(plot.getTrusted().size());
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
                return String.valueOf(plot.getId().x);
            }
            case "currentplot_y": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return String.valueOf(plot.getId().y);
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
                return String.valueOf(plot.getAverageRating());
            }
            case "currentplot_biome": {
                if (pl.getCurrentPlot() == null) {
                    return "";
                }
                return plot.getBiomeSynchronous() + "";
            }
            default:
                break;
        }
        final Matcher matcher = flagPattern.matcher(identifier);
        if (matcher.find()) {
            return getFlag(plot, matcher.group(1));
        }
        return null;
    }

    /**
     * Return the flag value from its name on the current plot.
     * If the flag doesn't exist it return an empty string.
     * If the flag exists but it is not set on current plot it return default value.
     *
     * @param plot     Plot
     * @param flagName Name of flag to get from current plot
     * @return The value of flag serialized in string
     */
    private String getFlag(final Plot plot, final String flagName) {
        final PlotFlag<?, ?> flag = GlobalFlagContainer.getInstance().getFlagFromString(flagName);
        if (flag != null && plot != null) {
            return plot.getFlags().stream()
                .filter(pflag -> pflag.getName().equals(flagName)).findFirst()
                .map(PlotFlag::getValue)
                .map(Object::toString)
                .orElseGet(() -> plot.getFlagContainer().getFlagErased(flag.getClass()).toString());
        }
        return "";
    }
}
