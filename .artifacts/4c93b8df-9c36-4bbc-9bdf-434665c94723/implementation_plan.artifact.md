# Radio App Redesign & Feature Implementation

This plan outlines the changes to redesign the Radio App with region-based navigation, specialized playback controls, and UI cleanup.

## User Review Required

> [!IMPORTANT]
> The `BottomNavigationView` and generic fragments (Transform, Reflow, Slideshow) will be removed to focus the app on Radio playback and Region selection as requested.

## Proposed Changes

### UI Cleanup & Layout Simplification

#### [MODIFY] [app_bar_main.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/layout/app_bar_main.xml)
- Remove the `FloatingActionButton` (the "mail" icon).

#### [MODIFY] [content_main.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/layout/content_main.xml)
- Remove the `BottomNavigationView`.
- Ensure the `NavHostFragment` takes up the full space.

#### [MODIFY] [MainActivity.kt](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/java/com/example/radioapp/MainActivity.kt)
- Remove FAB initialization and snackbar code.
- Remove `bottomNavView` setup logic.

---

### Navigation & Region Support

#### [MODIFY] [navigation_drawer.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/menu/navigation_drawer.xml)
- Replace generic items with "All Regions", "Bosnia", "Serbia", and "Croatia".

#### [NEW] [RegionSelectionFragment.kt](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/java/com/example/radioapp/ui/region/RegionSelectionFragment.kt)
- A new fragment that displays buttons or a list for selecting a region (Bosnia, Serbia, Croatia).

#### [NEW] [fragment_region_selection.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/layout/fragment_region_selection.xml)
- Layout for the region selection screen.

#### [MODIFY] [mobile_navigation.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/navigation/mobile_navigation.xml)
- Set `RegionSelectionFragment` as the start destination.
- Add a `region` argument (String, nullable) to `nav_radio`.
- Remove generic destinations (`nav_transform`, `nav_reflow`, etc.).

---

### Playback Controls & Logic

#### [MODIFY] [fragment_radio.xml](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/res/layout/fragment_radio.xml)
- Redesign the bottom `controlBar` to contain exactly three buttons: **Stop**, **Pause**, and **Play Live**.

#### [MODIFY] [RadioFragment.kt](file:///D:/Android%20Studio%20APPS/Radio%20APP/app/src/main/java/com/example/radioapp/ui/radio/RadioFragment.kt)
- **Region Filtering**: Retrieve the `region` argument and filter the `stations` list.
- **Stop Button**: Implement `player.stop()` and `player.clearMediaItems()` to completely cease playback and data consumption.
- **Pause Button**: Standard `player.pause()`.
- **Play Live Button**: Logic to resume playback and seek to the live edge (if supported) to ensure lowest latency.

## Verification Plan

### Manual Verification
1. Verify that the "mail" icon (FAB) is gone.
2. Verify that the bottom navigation bar is removed.
3. Open the navigation drawer and check if Bosnia, Serbia, and Croatia items are present.
4. Click "Bosnia" in the drawer; verify only Bosnian stations are shown.
5. Press the back button; verify the app returns to the Region Selection screen.
6. Test Playback:
    - **Pause**: Sound stops, resumes from same spot on Play.
    - **Stop**: Sound stops, "Play" starts from beginning/live.
    - **Play Live**: Resumes or jumps to the latest live point.
