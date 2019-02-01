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


public class getListPadashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> Mablagh, Description, PDate, Babat;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;


    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getListPadashAdapter(final Context context, List<String> PDate, List<String> Mablagh, List<String> Babat, List<String> Description, RecyclerView recyclerViewlist) {
        this.context = context;

        this.Mablagh = Mablagh;
        this.Babat = Babat;
        this.Description = Description;
        this.PDate = PDate;

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

        return Description.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.view_holder_list_padash, parent, false);

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


            myViewHolder.txt[1].setText(PDate.get(position));
            myViewHolder.txt[2].setText(formatter.format(Long.valueOf(Mablagh.get(position))) + " تومان ");
            myViewHolder.txt[3].setText(Babat.get(position));
            myViewHolder.txt[4].setText(Description.get(position));


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return Description == null ? 0 : Description.size();
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
