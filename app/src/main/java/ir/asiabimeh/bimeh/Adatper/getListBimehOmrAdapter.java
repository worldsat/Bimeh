package ir.asiabimeh.bimeh.Adatper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.DecimalFormat;
import java.util.List;

import ir.asiabimeh.bimeh.Helper.EditBimeh;
import ir.asiabimeh.bimeh.Helper.editOmrVaPasandaz;
import ir.asiabimeh.bimeh.Helper.updateOmrVaPasandaz;
import ir.asiabimeh.bimeh.OtherClass.OnLoadMoreListener;
import ir.asiabimeh.bimeh.R;


public class getListBimehOmrAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<String> NameBimeGozarItems, IdItems, EnumStatus, RaveshPardakhtAllBime, NameBimeShode, ShenaseBimeNaameItems, MablaghGharardad, MablaghVosool, DateItems;
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


    public getListBimehOmrAdapter(final Context context, final String Activity, List<String> RaveshPardakhtAllBime, List<String> NameBimeGozarItems, List<String> DateItems, List<String> IdItems, List<String> ShenaseBimeNaameItems, List<String> MablaghGharardad, List<String> MablaghVosool, List<String> NameBimeShode, List<String> EnumStatus, RecyclerView recyclerViewlist) {
        this.context = context;

        this.NameBimeGozarItems = NameBimeGozarItems;
        this.DateItems = DateItems;
        this.IdItems = IdItems;
        this.ShenaseBimeNaameItems = ShenaseBimeNaameItems;
        this.MablaghGharardad = MablaghGharardad;
        this.MablaghVosool = MablaghVosool;
        this.mRecyclerViewlist = recyclerViewlist;
        this.RaveshPardakhtAllBime = RaveshPardakhtAllBime;
        this.NameBimeShode = NameBimeShode;
        this.EnumStatus = EnumStatus;
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

            view = LayoutInflater.from(context).inflate(R.layout.view_holder_list_bimeh_omr, parent, false);

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


            myViewHolder.MablaghHagheBime.setText(formatter.format(Long.valueOf(MablaghGharardad.get(position))) + " تومان ");
            myViewHolder.NameBimeShode.setText(NameBimeShode.get(position));

            myViewHolder.ShenaseBimeNaame.setText(ShenaseBimeNaameItems.get(position));
            myViewHolder.NameBimeGozar.setText(NameBimeGozarItems.get(position));
            myViewHolder.Date.setText(DateItems.get(position));
            myViewHolder.ID.setText(IdItems.get(position));

            if (RaveshPardakhtAllBime.get(position).equals("1")) {
                myViewHolder.RaveshPardakhtAllBime.setText("نقدی");
            } else if (RaveshPardakhtAllBime.get(position).equals("2")) {
                myViewHolder.RaveshPardakhtAllBime.setText("اقساطی");
            }
            String string = null;
            string = MablaghVosool.get(position);
            String[] parts = null;
            parts = string.split(",");
            Log.i("moh3n", "onBindViewHolder: " + string);
            int i = parts.length;
            i = (i) + 1;
            final String[] parts2 = parts;
            final int x = i;
            Log.i("moh3n", "onBindViewHolder: " + i);

            for (int n = 1; n < i; n++) {

                if (n == 1 || n == 3 || n == 5 || n == 7 || n == 9 || n == 11) {
                    if (parts[n].equals("T")) {

                        myViewHolder.tick[n].setChecked(true);
                        myViewHolder.tick[n].setClickable(false);


                    } else {

                        myViewHolder.tick[n].setChecked(false);
                        myViewHolder.tick[n].setClickable(true);
                        Log.i("moh3n", "onBindViewHolderLenth: " + parts2.length);
                        final int k = n;
                        myViewHolder.tick[n].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int a = 1; a < x; a++) {
                                    Log.i("moh3n", "onClick2: " + parts2[a - 1]);
                                }

                                parts2[k] = "T";
                                String str = "";

                                for (int a = 1; a < x; a++) {
                                    int z = x - 1;
                                    if (z == a) {
                                        str += parts2[a - 1];
                                    } else {
                                        str += parts2[a - 1] + ",";
                                    }
//
                                }
//
                                Log.i("moh3n", "onClick: " + str);
                                updateOmrVaPasandaz updateOmrVaPasandaz = new updateOmrVaPasandaz();
                                updateOmrVaPasandaz.connect(context, IdItems.get(position),str
                                        , NameBimeGozarItems.get(position), NameBimeShode.get(position), ShenaseBimeNaameItems.get(position), MablaghGharardad.get(position), RaveshPardakhtAllBime.get(position));
                            }


                        });
                    }
                } else {

                    myViewHolder.date[n].setText(parts[n - 2]);
                    myViewHolder.layer[n].setVisibility(View.VISIBLE);
                }

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

        } else if (holder instanceof LoadingViewHolder)

        {
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

        TextView RaveshPardakhtAllBime, MablaghHagheBime, NameBimeShode, ShenaseBimeNaame, NameBimeGozar, Date, ID, EnumStatus, edit;
        TextView tick1, tick2, tick3, tick4, tick5, tick6, date1, date2, date3, date4, date5, date6;
        LinearLayout layer1, layer2, layer3, layer4, layer5, layer6;
        CheckBox[] tick = new CheckBox[14];
        TextView[] date = new TextView[14];
        LinearLayout[] layer = new LinearLayout[14];

        private MyViewHolder(View itemView) {
            super(itemView);

            RaveshPardakhtAllBime = itemView.findViewById(R.id.RaveshPardakhtAllBimeTxt);
            NameBimeShode = itemView.findViewById(R.id.NameBimeShode);
            MablaghHagheBime = itemView.findViewById(R.id.MablaghHagheBimeTxt);
            ShenaseBimeNaame = itemView.findViewById(R.id.ShenaseBimeNaameTxt);
            NameBimeGozar = itemView.findViewById(R.id.NameBimeGozarTxt);
            EnumStatus = itemView.findViewById(R.id.EnumStatus);
            Date = itemView.findViewById(R.id.DateTxt);
            ID = itemView.findViewById(R.id.IdTxt);
            tick[1] = itemView.findViewById(R.id.box1);
            tick[3] = itemView.findViewById(R.id.box2);
            tick[5] = itemView.findViewById(R.id.box3);
            tick[7] = itemView.findViewById(R.id.box4);
            tick[9] = itemView.findViewById(R.id.box5);
            tick[11] = itemView.findViewById(R.id.box6);
            date[2] = itemView.findViewById(R.id.date1);
            date[4] = itemView.findViewById(R.id.date2);
            date[6] = itemView.findViewById(R.id.date3);
            date[8] = itemView.findViewById(R.id.date4);
            date[10] = itemView.findViewById(R.id.date5);
            date[12] = itemView.findViewById(R.id.date6);
            layer[2] = itemView.findViewById(R.id.layer1);
            layer[4] = itemView.findViewById(R.id.layer2);
            layer[6] = itemView.findViewById(R.id.layer3);
            layer[8] = itemView.findViewById(R.id.layer4);
            layer[10] = itemView.findViewById(R.id.layer5);
            layer[12] = itemView.findViewById(R.id.layer6);
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
                editOmrVaPasandaz editBimeh = new editOmrVaPasandaz();
                editBimeh.connect(context, page, Id, str, alert_dialog, wait);
            }
        });
    }

}
