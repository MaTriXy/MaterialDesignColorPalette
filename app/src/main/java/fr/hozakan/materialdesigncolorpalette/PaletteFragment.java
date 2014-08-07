package fr.hozakan.materialdesigncolorpalette;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.card.ColorCard;
import fr.hozakan.materialdesigncolorpalette.card.ColorCardAdapter;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.otto.event.ShareMenuClickedEvent;
import fr.hozakan.materialdesigncolorpalette.recycler.PaletteColorRecyclerAdapter;
import fr.hozakan.materialdesigncolorpalette.services.PaletteColorService;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by gimbert on 2014-07-18.
 */
public class PaletteFragment extends Fragment implements ColorCard.ColorCardCallback {

    private CardListView mClv;

    @Inject
    protected PaletteColorService mService;

    @Inject
    protected Bus mBus;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int mPrimaryColor;

    public static PaletteFragment newInstance(int primaryColor) {
        PaletteFragment f = new PaletteFragment();
        f.mPrimaryColor = primaryColor;
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBus.register(this);
    }

    @Override
    public void onDestroy() {
        mBus.unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_color_palette2, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mClv = (CardListView) view.findViewById(R.id.palette_card_list);
//        mClv.setAdapter(new ColorCardAdapter(getActivity(), toCards(mService.getPaletteColors(mPrimaryColor))));

        mRecyclerView = (RecyclerView) view.findViewById(R.id.palette_card_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);

        PaletteColorRecyclerAdapter adapter = new PaletteColorRecyclerAdapter(mService.getPaletteColors(mPrimaryColor));
        ((BaseApplication)getActivity().getApplication()).inject(adapter);
        mRecyclerView.setAdapter(adapter);

    }

    private List<ColorCard> toCards(List<PaletteColor> paletteColors) {
        List<ColorCard> cards = new ArrayList<ColorCard>();
        for (PaletteColor color : paletteColors) {
            cards.add(new ColorCard(getActivity(), color, this));
        }
        return cards;
    }

    @Override
    public void copyColorToClipboard(String parentColorName, String colorName, String colorHexa) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText(String.format("Color %s %s", parentColorName, colorName), colorHexa);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(getActivity(), "Color copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void shareMenuClicked(ShareMenuClickedEvent event) {
        final PaletteColor color = event.getColor();
        copyColorToClipboard(color.getParentColorName(), color.getNameId(), color.getHexaId());
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((BaseApplication)activity.getApplication()).inject(this);
        List<PaletteColor> colors = mService.getPaletteColors(mPrimaryColor);
        if (!colors.isEmpty()) {
            ((ColorPaletteActivity) activity).onSectionAttached(activity.getResources().getString(mPrimaryColor), colors.get(0).getHexaId());
        }
    }
}
