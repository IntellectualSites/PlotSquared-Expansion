# PlotSquared-Expansion
PlotSquared expansion for PlaceholderAPI

This project is intended to be an updated and backwards compatible extension for PlotSquared.

This expansion works for both the old and new API's of PlotSquared.

### Downloads
[Download from the eCloud](https://api.extendedclip.com/expansions/plotsquared/) <br>
Download ingame: `/papi ecloud download PlotSquared` followed by `/papi reload`

### Note to FeatherBoard users
FeatherBoard does not use the regular PlaceholderAPI setup. Therefore, you must edit the placeholders a bit, check the example below:    
`{placeholderapi_plotsquared_currentplot_alias}` <br>
You need to add `placeholderapi` in front of all placeholders.

### Following placeholders are available:
```
- %plotsquared_currentplot_alias%
- %plotsquared_currentplot_owner%
- %plotsquared_currentplot_members%
    - %plotsquared_currentplot_members_added%
    - %plotsquared_currentplot_members_trusted%
- %plotsquared_currentplot_members_denied%
- %plotsquared_currentplot_world%
- %plotsquared_has_plot%
    - %plotsquared_has_plot_<World>%
- %plotsquared_has_build_rights%
- %plotsquared_plot_count%
    - %plotsquared_plot_count_<World>%
- %plotsquared_allowed_plot_count%
- %plotsquared_currentplot_xy%
    - %plotsquared_currentplot_x%
    - %plotsquared_currentplot_y%
- %plotsquared_currentplot_rating%
- %plotsquared_currentplot_biome%
- %plotsquared_currentplot_flag_<flagName>%
```
