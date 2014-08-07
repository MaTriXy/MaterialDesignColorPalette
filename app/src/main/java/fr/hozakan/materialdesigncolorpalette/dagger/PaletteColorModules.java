package fr.hozakan.materialdesigncolorpalette.dagger;

import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.hozakan.materialdesigncolorpalette.BaseApplication;
import fr.hozakan.materialdesigncolorpalette.NavigationDrawerFragment;
import fr.hozakan.materialdesigncolorpalette.PaletteFragment;
import fr.hozakan.materialdesigncolorpalette.recycler.PaletteColorRecyclerAdapter;
import fr.hozakan.materialdesigncolorpalette.services.PaletteColorService;

/**
 * Created by gimbert on 2014-07-31.
 */
@Module(injects = {
        NavigationDrawerFragment.class,
        PaletteFragment.class,
        PaletteColorRecyclerAdapter.class
},
library = true)
public class PaletteColorModules {
    private BaseApplication mBaseApp;

    public PaletteColorModules(BaseApplication app) {
        this.mBaseApp = app;
    }

    @Provides
    public Context provideAppContext() {
        return mBaseApp.getApplicationContext();
    }

    @Provides
    public PaletteColorService providePaletteColorService(Context context) {
        return new PaletteColorService(context);
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus(ThreadEnforcer.ANY);
    }
}
