# Fix missing layout attributes in app_bar_main.xml

The user reported a render issue: "One or more layouts are missing the layout_width or layout_height attributes".
After investigation, I found that multiple `app_bar_main.xml` files contain an `<include>` tag for `content_main` that is missing these required attributes.

## Proposed Changes

### [Layouts]

#### [MODIFY] [app_bar_main.xml](file:///D:/Android Studio APPS/Radio APP/app/src/main/res/layout/app_bar_main.xml)
Add `android:layout_width="match_parent"` and `android:layout_height="match_parent"` to the `<include android:id="@+id/content_main" ... />` tag.

#### [MODIFY] [app_bar_main.xml](file:///D:/Android Studio APPS/Radio APP/app/src/main/res/layout-w600dp/app_bar_main.xml)
Add `android:layout_width="match_parent"` and `android:layout_height="match_parent"` to the `<include android:id="@+id/content_main" ... />` tag.

#### [MODIFY] [app_bar_main.xml](file:///D:/Android Studio APPS/Radio APP/app/src/main/res/layout-w1240dp/app_bar_main.xml)
Add `android:layout_width="match_parent"` and `android:layout_height="match_parent"` to the `<include android:id="@+id/content_main" ... />` tag.

## Verification Plan

### Manual Verification
- The Layout Preview in Android Studio should no longer show the error "One or more layouts are missing the layout_width or layout_height attributes".
