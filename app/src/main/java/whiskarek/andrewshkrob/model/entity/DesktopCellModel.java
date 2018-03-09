package whiskarek.andrewshkrob.model.entity;

import android.content.Intent;

public interface DesktopCellModel {

    int getAppId();

    int getPosition();

    int getScreen();

    int getShortcutType();

    Intent getIntent();
}
