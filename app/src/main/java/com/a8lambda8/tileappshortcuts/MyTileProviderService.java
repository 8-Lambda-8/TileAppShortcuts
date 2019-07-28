package com.a8lambda8.tileappshortcuts;

import android.util.Log;

import com.google.android.clockwork.tiles.TileProviderService;

import static com.a8lambda8.tileappshortcuts.MainActivity.TAG;

public class MyTileProviderService extends TileProviderService {
    public MyTileProviderService() {
    }

    @Override
    public void onTileUpdate(int tileId) {

        //Log.d(TAG,"from tile"+tileId);

    }
}
