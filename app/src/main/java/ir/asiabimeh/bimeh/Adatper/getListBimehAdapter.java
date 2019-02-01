package ir.asiabimeh.bimeh.Adatper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import ir.asiabimeh.bimeh.Helper.EditBimeh;
import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getListBimehAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> NameBimeGozarItems, IdItems, ShenaseBimeNaameItems, EnumStatus, MablaghHagheBimeItems, RaveshPardakhtAllBimeItems, DateItems;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;
    private String str;
    private String activity;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getListBimehAdapter(final Context context, final String Activity, List<String> NameBimeGozarItems, List<String> DateItems, List<String> IdItems, List<String> ShenaseBimeNaameItems, List<String> MablaghHagheBimeItems, List<String> RaveshPardakhtAllBimeItems, List<String> EnumStatus, RecyclerView recyclerViewlist) {
        this.context = context;

        this.NameBimeGozarItems = NameBimeGozarItems;
        this.DateItems = DateItems;
        this.IdItems = IdItems;
        this.ShenaseBimeNaameItems = ShenaseBimeNaameItems;
        this.MablaghHagheBimeItems = MablaghHagheBimeItems;
        this.RaveshPardakhtAllBimeItems = RaveshPardakhtAllBimeItems;
        this.EnumStatus = EnumStatus;
        this.mRecyclerViewlist = recyclerViewlist;
        this.activity = Activity;
//---------------------------
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerViewlist.getLayoutManager();
        mRecyclerViewlist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;

                }
            }
        });


    }

    @Override
    public int getItemViewType(int position) {

        return IdItems.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.view_holder_list_bimeh, parent, false);

            MyViewHolder vh = new MyViewHolder(view);
            return vh;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.loading_layout, parent, false);
            LoadingViewHolder vh2 = new LoadingViewHolder(view);
            return vh2;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyViewHolder) {
            final MyViewHolder myViewHolder = (MyViewHolder) holder;


            myViewHolder.MablaghHagheBime.setText(formatter.format(Long.valueOf(MablaghHagheBimeItems.get(position))) + " تومان ");

            myViewHolder.ShenaseBimeNaame.setText(ShenaseBimeNaameItems.get(position));
            myViewHolder.NameBimeGozar.setText(NameBimeGozarItems.get(position));
            myViewHolder.Date.setText(DateItems.get(position));
            myViewHolder.ID.setText(IdItems.get(position));

            if (RaveshPardakhtAllBimeItems.get(position).equals("1")) {
                myViewHolder.RaveshPardakhtAllBime.setText("نقدی");
            } else if (RaveshPardakhtAllBimeItems.get(position).equals("2")) {
                myViewHolder.RaveshPardakhtAllBime.setText("اقساطی");
            }

            switch (EnumStatus.get(position)) {
                case "1": {
                    myViewHolder.EnumStatus.setText("در انتظار تائید");
                    myViewHolder.edit.setVisibility(View.VISIBLE);
                    break;
                }
                case "2": {
                    myViewHolder.EnumStatus.setText(" تائید شده");
                    myViewHolder.edit.setVisibility(View.INVISIBLE);
                    break;
                }
                case "3": {
                    myViewHolder.EnumStatus.setText("تائید نشده");
                    myViewHolder.edit.setVisibility(View.INVISIBLE);
                    break;
                }
            }

            myViewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertEdit(myViewHolder.itemView.getContext(), activity, IdItems.get(position), ShenaseBimeNaameItems.get(position));
                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return IdItems == null ? 0 : IdItems.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    public void setLoaded() {
        isLoading = false;
    }


    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        private LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar1);
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        TextView RaveshPardakhtAllBime, MablaghHagheBime, ShenaseBimeNaame, NameBimeGozar, Date, ID, EnumStatus, edit;


        private MyViewHolder(View itemView) {
            super(itemView);

            RaveshPardakhtAllBime = itemView.findViewById(R.id.RaveshPardakhtAllBimeTxt);
            MablaghHagheBime = itemView.findViewById(R.id.MablaghHagheBimeTxt);
            ShenaseBimeNaame = itemView.findViewById(R.id.ShenaseBimeNaameTxt);
            NameBimeGozar = itemView.findViewById(R.id.NameBimeGozarTxt);
            Date = itemView.findViewById(R.id.DateTxt);
            ID = itemView.findViewById(R.id.IdTxt);
            EnumStatus = itemView.findViewById(R.id.EnumStatus);
            edit = itemView.findViewById(R.id.edit);


        }

    }

    public void alertEdit(final Context context, final String page, final String Id, final String shenase) {

        final MaterialDialog alert_dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.alert_edit, false)
                .autoDismiss(false)
                .backgroundColor(Color.parseColor("#01000000"))
                .show();

        EditText edt = (EditText) alert_dialog.findViewById(R.id.editText);
        Button save = (Button) alert_dialog.findViewById(R.id.save);
       final ProgressBar wait = (ProgressBar) alert_dialog.findViewById(R.id.progressBar);
        edt.setText(shenase);
        str = edt.getText().toString();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBimeh editBimeh = new EditBimeh();
                editBimeh.connect(context, page, Id, str, alert_dialog,wait);
            }
        });
    }

}
