package com.it.nhozip.englisha_z.widgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.it.nhozip.englisha_z.Adapter.MoveAdapter;
import com.it.nhozip.englisha_z.Model.Move;
import com.it.nhozip.englisha_z.R;
import com.it.nhozip.englisha_z.View.ActivityDetailMove;

import java.util.ArrayList;



/**
 * Created by huyen on 11/2/2016.
 */
public class AutoScrollPagerFragment extends Fragment {
    private  DatabaseReference mDatabasee;
    public  static   ArrayList<Move> moves;
    private TextView title;
    private AutoScrollViewPager pager;
    private CirclePageIndicator indicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fm_auto, container, false);
        intView(view);
        setDataAuto();
        return view;
    }
    private void setDataAuto() {
                mDatabasee = FirebaseDatabase.getInstance().getReference().child("hot");

                mDatabasee.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        moves= new ArrayList<Move>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Move move;
                            move = snapshot.getValue(Move.class);
                            moves.add(move);
                        }
                        Log.e("dtaa", moves.toString());
                        indicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                            @Override
                            public void onPageSelected(int i) {
                                title.setText(moves.get(i).getName());
                            }
                        });

                        pager.setAdapter(new PagerAdapter() {
                            @Override
                            public int getCount() {
                                return moves.size();
                            }

                            @Override
                            public boolean isViewFromObject(View view, Object o) {
                                return view == o;
                            }

                            @Override
                            public Object instantiateItem(ViewGroup container, int position) {
                                ImageView view = new ImageView(container.getContext());
                                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                Glide.with(getContext()).load("http://img.youtube.com/vi/"+moves.get(position).getThumbi_move().toString()+"/mqdefault.jpg")
                                        .thumbnail(0.5f)
                                        .crossFade()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(view);
                                container.addView(view);
                                return view;
                            }

                            @Override
                            public void destroyItem(ViewGroup container, int position, Object object) {
                                container.removeView((View) object);
                            }
                        });

                        indicator.setViewPager(pager);
                        indicator.setSnap(true);

                        pager.setScrollFactgor(5);
                        pager.setOffscreenPageLimit(4);
                        pager.startAutoScroll(2000);

                        pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
                            @Override
                            public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
                                Intent intent=new Intent(getActivity(), ActivityDetailMove.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra(MoveAdapter.MOVE_OBJ, moves.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void intView(View view) {
        title = (TextView) view.findViewById(R.id.title);
        pager = (AutoScrollViewPager)view.findViewById(R.id.scroll_pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}