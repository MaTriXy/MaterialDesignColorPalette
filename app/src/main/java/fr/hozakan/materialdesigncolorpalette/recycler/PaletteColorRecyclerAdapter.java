package fr.hozakan.materialdesigncolorpalette.recycler;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import fr.hozakan.materialdesigncolorpalette.R;
import fr.hozakan.materialdesigncolorpalette.model.PaletteColor;

/**
 * Created by gimbert on 2014-08-05.
 */
public class PaletteColorRecyclerAdapter extends RecyclerView.Adapter<PaletteColorRecyclerAdapter.PaletteColorViewHolder> {

    private final List<PaletteColor> mDataset;
    private final Context mContext;

    public PaletteColorRecyclerAdapter(Context context, List<PaletteColor> colors) {
        mContext = context;
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
        PaletteColor color = mDataset.get(i);
        if (color != null) {
//            paletteColorViewHolder.mCardView.setBackgroundColor(color.getColorId());
            paletteColorViewHolder.mRlPalette.setBackgroundColor(color.getColorId());
            paletteColorViewHolder.mTvColorName.setText(color.getNameId());
            paletteColorViewHolder.mTvColorId.setText(color.getColorId());
            paletteColorViewHolder.mTvColorHexa.setText(color.getHexaId());
            System.out.println("mDataset = " + mDataset);
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

        public PaletteColorViewHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mTvColorName = (TextView) mCardView.findViewById(R.id.info_color_name);
            mTvColorId = (TextView) mCardView.findViewById(R.id.info_color_id);
            mTvColorHexa = (TextView) mCardView.findViewById(R.id.info_color_hexa);
            mRlPalette = (RelativeLayout) itemView.findViewById(R.id.cardview_rl);
        }
    }
}
