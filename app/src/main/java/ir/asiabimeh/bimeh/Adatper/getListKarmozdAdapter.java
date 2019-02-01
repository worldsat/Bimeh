package ir.asiabimeh.bimeh.Adatper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getListKarmozdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> NameBimeGozar, MablaghKarmozd, PayPDate, MablaghHagheBime, IsPay, BimeKind, Percent;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;


    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getListKarmozdAdapter(final Context context, List<String> BimeKind, List<String> NameBimeGozar, List<String> Percent, List<String> MablaghKarmozd, List<String> PayPDate, List<String> MablaghHagheBime, List<String> IsPay, RecyclerView recyclerViewlist) {
        this.context = context;

        this.NameBimeGozar = NameBimeGozar;
        this.Percent = Percent;
        this.MablaghKarmozd = MablaghKarmozd;
        this.PayPDate = PayPDate;
        this.MablaghHagheBime = MablaghHagheBime;
        this.BimeKind = BimeKind;
        this.IsPay = IsPay;
        this.mRecyclerViewlist = recyclerViewlist;

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

        return MablaghKarmozd.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.view_holder_list_karmozd, parent, false);

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


            myViewHolder.txt[1].setText(NameBimeGozar.get(position));

            myViewHolder.txt[3].setText(" % " + Percent.get(position));
            myViewHolder.txt[4].setText(formatter.format(Long.valueOf(MablaghHagheBime.get(position))) + " تومان ");
            myViewHolder.txt[5].setText(formatter.format(Long.valueOf(MablaghKarmozd.get(position))) + " تومان ");

            if (!PayPDate.get(position).equals("null")) {
                myViewHolder.txt[7].setText(PayPDate.get(position));
            } else {
                myViewHolder.txt[7].setText("");
            }

            String Str = "";
            switch (BimeKind.get(position)) {
                case "3":
                    Str = "بیمه عمر";
                    break;
                case "4":
                    Str = "بیمه ثالت";
                    break;
                case "2":
                    Str = "بیمه بدنه";
                    break;
                case "5":
                    Str = "بیمه مسئولیت";
                    break;
                case "1":
                    Str = "بیمه آتش سوزی";
                    break;

            }
            myViewHolder.txt[2].setText(Str);

            if (IsPay.get(position).equals("false")) {
                myViewHolder.txt[6].setText("پرداخت نشده");
            } else if (IsPay.get(position).equals("2")) {
                myViewHolder.txt[6].setText("پرداخت شده");
            }


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return MablaghKarmozd == null ? 0 : MablaghKarmozd.size();
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

        TextView[] txt = new TextView[8];


        private MyViewHolder(View itemView) {
            super(itemView);

            txt[1] = itemView.findViewById(R.id.txt1);
            txt[2] = itemView.findViewById(R.id.txt2);
            txt[3] = itemView.findViewById(R.id.txt3);
            txt[4] = itemView.findViewById(R.id.txt4);
            txt[5] = itemView.findViewById(R.id.txt5);
            txt[6] = itemView.findViewById(R.id.txt6);
            txt[7] = itemView.findViewById(R.id.txt7);


        }

    }


}
