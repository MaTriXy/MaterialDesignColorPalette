package fr.hozakan.materialdesigncolorpalette.recycler;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;
import fr.hozakan.materialdesigncolorpalette.otto.event.ShareMenuClickedEvent;

/**
 * Created by gimbert on 2014-08-05.
 */
public class PaletteColorRecyclerAdapter extends RecyclerView.Adapter<PaletteColorRecyclerAdapter.PaletteColorViewHolder> {

    private final List<PaletteColor> mDataset;

    @Inject
    protected Context mContext;

    @Inject
    protected Bus mBus;

    public PaletteColorRecyclerAdapter(List<PaletteColor> colors) {
        mDataset = colors;
    }

    @Override
    public PaletteColorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.palette_color_recycler_item, viewGroup, false);
        PaletteColorViewHolder vh = new PaletteColorViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PaletteColorViewHolder paletteColorViewHolder, int i) {
        final PaletteColor color = mDataset.get(i);
        if (color != null) {
            paletteColorViewHolder.mCardView.setBackgroundResource(color.getColorId());
            paletteColorViewHolder.mTvColorName.setText(color.getNameId());
            paletteColorViewHolder.mTvColorId.setText(color.getColorId());
            paletteColorViewHolder.mTvColorHexa.setText(color.getHexaId());
            paletteColorViewHolder.mButtonPopupMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(mContext, view);
                    MenuInflater inflater = popup.getMenuInflater();
                    inflater.inflate(R.menu.color_card_popup_menu, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.card_menu_copy_color:
                                    mBus.post(new ShareMenuClickedEvent(color));
                                    break;
                                default:
                                    break;
                            }
                            return false;
                        }
                    });
                    popup.show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class PaletteColorViewHolder extends  RecyclerView.ViewHolder {

        private final CardView mCardView;
        private final TextView mTvColorName;
        public final TextView mTvColorId;
        public final TextView mTvColorHexa;
        private final RelativeLayout mRlPalette;
        private final ImageButton mButtonPopupMenu;

        public PaletteColorViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTvColorName = (TextView) mCardView.findViewById(R.id.info_color_name);
            mTvColorId = (TextView) mCardView.findViewById(R.id.info_color_id);
            mTvColorHexa = (TextView) mCardView.findViewById(R.id.info_color_hexa);
            mRlPalette = (RelativeLayout) itemView.findViewById(R.id.cardview_rl);
            mButtonPopupMenu = (ImageButton) itemView.findViewById(R.id.palette_color_popup_menu);
        }
    }
}
