package whiskarek.andrewshkrob.model;

import android.content.Intent;

public interface DesktopCellModel {

    int getAppId();

    int getPosition();

    int getScreen();

    int getCellType();

    Intent getIntent();
}
