package ir.asiabimeh.bimeh.Adatper;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getLoadTarikhBimehOmrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> Status, Date;
    private OnLoadMoreListener mOnLoadMoreListener;
    //----------------
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context context;
    private SharedPreferences sp;

    private RecyclerView mRecyclerViewlist;
    private DecimalFormat formatter = new DecimalFormat("###,###,###,###");


    public getLoadTarikhBimehOmrAdapter(final Context context, List<String> Date, List<String> Status, RecyclerView recyclerViewlist) {
        this.context = context;

        this.Status = Status;
        this.Date = Date;

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

        return Status.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;

    }

    //--------------------------------------------------------MyViewHolder----------------------------------------------
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view;

            view = LayoutInflater.from(context).inflate(R.layout.view_holder_list_tarikh, parent, false);

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

            sp = context.getSharedPreferences("mablagh", 0);
            sp.edit().putString("tarikh" + position, Date.get(position)).apply();
            sp.edit().putString("pardakhti" + position, Status.get(position)).apply();



            myViewHolder.date.setText(Date.get(position));
            if (Status.get(position).equals("true")) {
                myViewHolder.tick.setChecked(true);
            } else {
                myViewHolder.tick.setChecked(false);
            }


            myViewHolder.tick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (myViewHolder.tick.isChecked()) {
                        sp.edit().putString("pardakhti" + position, "true").apply();
                    } else {
                        sp.edit().putString("pardakhti" + position, "false").apply();
                    }
                   // Log.i("moh3n", "2" + sp.getString("pardakhti" + position, "--"));
                }
            });


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {

        return Status == null ? 0 : Status.size();
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

        TextView date;
        CheckBox tick;


        private MyViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            tick = itemView.findViewById(R.id.checkBox);


        }

    }


}
